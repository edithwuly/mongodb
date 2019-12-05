package Mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Entity.Triple;

public class MongodbAPI {
    MongoCollection Entity;
    MongoCollection Relation;

    public void insert(String file)
    {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        // 创建集合
        //mongoDatabase.createCollection("entity");
        //mongoDatabase.createCollection("relation");

        // 获取集合
        Entity = mongoDatabase.getCollection("entity");
        Relation = mongoDatabase.getCollection("relation");

        readFileByLine(file);

        mongoClient.close();
    }

    private void readFileByLine(String strFile){
        try {
            File file = new File(strFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String strLine = null;
            while(null != (strLine = bufferedReader.readLine()))
            {
                resolveTriple(strLine);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Triple resolveTriple(String data)
    {
        //System.out.println("数据:" + data);
        Triple res = new Triple();
        String temp = "";
        int i = 1;
        if (data.charAt(i) == '(')
        {
            temp += data.charAt(i);
            i++;

            for (int count = 1; count != 0 && i < data.length(); i++)
            {
                if (data.charAt(i) == '(')
                {
                    count ++;
                }
                else if (data.charAt(i) == ')')
                {
                    count --;
                }

                temp += data.charAt(i);
            }
            res.subject = resolveTriple(temp);
        }
        else
        {
            for (; data.charAt(i) != ':'; i++)
            {
                temp += data.charAt(i);
            }
            res.subject = new Triple();
            res.subject.name = temp;
            i++;
            temp = "";
            for (; data.charAt(i) != ','; i++)
            {
                temp += data.charAt(i);
            }
            res.subject.type = temp;
            Document example;
            BasicDBObject bson = new BasicDBObject("name", res.subject.name);
            if ((example = (Document)Entity.find(bson).first()) == null)
            {
                example = new Document("name", res.subject.name).append("type", res.subject.type).append("nest", 0);
                Entity.insertOne(example);
            }
        }
        i++;

        temp = "";
        for (; data.charAt(i) != ','; i++)
        {
            temp += data.charAt(i);
        }
        res.predicate = temp;
        i++;

        temp = "";
        if (data.charAt(i) == '(')
        {
            temp += data.charAt(i);
            i++;

            for (int count = 1; count != 0 && i < data.length(); i++)
            {
                if (data.charAt(i) == '(')
                {
                    count ++;
                }
                else if (data.charAt(i) == ')')
                {
                    count --;
                }
                temp += data.charAt(i);
            }

            res.object = resolveTriple(temp);
            i++;
        }
        else
        {
            for (; data.charAt(i) != ':'; i++)
            {
                temp += data.charAt(i);
            }
            res.object = new Triple();
            res.object.name = temp;
            i++;
            temp = "";
            for (; data.charAt(i) != ')' && i< data.length(); i++)
            {
                temp += data.charAt(i);
            }
            res.object.type = temp;
            Document example;
            BasicDBObject bson = new BasicDBObject("name", res.object.name);
            if ((example = (Document)Entity.find(bson).first()) == null)
            {
                example = new Document("name", res.object.name).append("type", res.object.type).append("nest", 0);
                Entity.insertOne(example);
            }
        }

        res.name = res.subject.name + res.object.name;
        res.type = "嵌套";

        //System.out.println("三元组:" + res.subject.name + ' ' + res.predicate + ' ' + res.object.name);

        Document predicate;
        ObjectId id;
        BasicDBObject bson = new BasicDBObject("name", res.predicate).append("subject",res.subject.name).append("object",res.object.name);
        if ((predicate = (Document) Relation.find(bson).first()) != null)
        {
            id = predicate.getObjectId("_id");
        }
        else
        {
            predicate = new Document("name", res.predicate).append("subject", res.subject.name).append("object", res.object.name);
            Relation.insertOne(predicate);
            id = ((Document)Relation.find(bson).first()).getObjectId("_id");
        }

        Document example;
        bson = new BasicDBObject("name", res.name);
        if ((example = (Document)Entity.find(bson).first()) == null)
        {
            example = new Document("name", res.name).append("type", res.type).append("nest", id);
            Entity.insertOne(example);
        }
        return res;
    }

    public List<Triple> relationsByType(String predicate)
    {
        List<Triple> res = new ArrayList<>();

        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Relation = mongoDatabase.getCollection("relation");
        BasicDBObject bson = new BasicDBObject("name", predicate);
        FindIterable<Document> triples = Relation.find(bson);

        for (Document triple : triples)
        {
            Triple temp = new Triple();
            temp.predicate = predicate;
            temp.subject = new Triple();
            temp.subject.name = triple.getString("subject");
            temp.object = new Triple();
            temp.object.name = triple.getString("object");
            res.add(temp);
        }

        mongoClient.close();
        return res;
    }

    public List<String> entitiesByType(String type)
    {
        List<String> res = new ArrayList<String>();

        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Entity = mongoDatabase.getCollection("entity");
        BasicDBObject bson = new BasicDBObject("type", type);
        FindIterable<Document> entities = Entity.find(bson);

        for (Document e:entities)
        {
            res.add(e.getString("name"));
        }

        mongoClient.close();
        return res;
    }

    public Triple downwardRecursion(String entityName)
    {
        Triple res = new Triple();
        res.name = entityName;

        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Entity = mongoDatabase.getCollection("entity");
        Relation = mongoDatabase.getCollection("relation");
        BasicDBObject bson = new BasicDBObject("name", entityName);
        Document entity = (Document) Entity.find(bson).first();

        res.type = entity.getString("type");
        if (res.type.compareTo("嵌套") == 0)
        {
            Triple temp = findRecursion((ObjectId) entity.get("nest"));
            res.subject = temp.subject;
            res.predicate = temp.predicate;
            res.object = temp.object;
        }

        mongoClient.close();;
        return res;
    }

    private Triple findRecursion(ObjectId id)
    {
        Triple res = new Triple();
        Document relation =(Document) Relation.find(new BasicDBObject("_id", id)).first();

        res.subject = new Triple();
        res.object = new Triple();
        res.subject.name  = relation.getString("subject");
        res.object.name = relation.getString("object");
        res.predicate = relation.getString("name");
        Document entity = (Document) Entity.find(new BasicDBObject("name", res.subject.name)).first();

        if ((res.subject.type = entity.getString("type")).compareTo("嵌套") == 0)
        {
            Triple temp = findRecursion((ObjectId) entity.get("nest"));
            res.subject.subject = temp.subject;
            res.subject.predicate = temp.predicate;
            res.subject.object = temp.object;
        }

        entity = (Document) Entity.find(new BasicDBObject("name", res.object.name)).first();

        if ((res.object.type = entity.getString("type")).compareTo("嵌套") == 0)
        {
            Triple temp = findRecursion((ObjectId) entity.get("nest"));
            res.object.subject = temp.subject;
            res.object.predicate = temp.predicate;
            res.object.object = temp.object;
        }

        System.out.print("subject:" + res.subject.name + " predicate:" + res.predicate + " object:" + res.object.name + "\n");
        return res;
    }

    public List<String> neighbours(String entityName)
    {
        List<String> res = new ArrayList<String>();

        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Relation = mongoDatabase.getCollection("relation");

        FindIterable<Document> relations = Relation.find(new BasicDBObject("subject",entityName));
        for (Document relation : relations)
        {
            String temp = relation.getString("object");
            if (!res.contains(temp))
            {
                res.add(temp);
            }
        }

        relations = Relation.find(new BasicDBObject("object",entityName));
        for (Document relation : relations)
        {
            String temp = relation.getString("subject");
            if (!res.contains(temp))
            {
                res.add(temp);
            }
        }

        mongoClient.close();
        return res;
    }

    public String upwardRecursion(String id)
    {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Entity = mongoDatabase.getCollection("entity");
        Document entity = (Document) Entity.find(new BasicDBObject("nest", new ObjectId(id))).first();

        mongoClient.close();
        return entity.getString("name");
    }
}
