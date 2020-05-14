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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import Entity.Triple;
import org.json.JSONArray;
import org.json.JSONObject;

public class MongodbAPI {

    public void insert(String file, MongoCollection entity, MongoCollection relation)
    {
        // 创建集合
        //mongoDatabase.createCollection("entity");
        //mongoDatabase.createCollection("relation");

        readFileByLine(file, entity, relation);
    }

    private void readFileByLine(String strFile, MongoCollection entity, MongoCollection relation){
        try {
            File file = new File(strFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String strLine = null;
            while(null != (strLine = bufferedReader.readLine()))
            {
                resolveTriple(strLine, entity, relation);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Triple resolveTriple(String data, MongoCollection entity, MongoCollection relation)
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
            res.subject = resolveTriple(temp, entity, relation);
        }
        else
        {
            for (; data.charAt(i) != ':'; i++)
            {
                temp += data.charAt(i);
            }
            res.subject = new Triple();
            //temp += "6";
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
            if ((example = (Document)entity.find(bson).first()) == null)
            {
                example = new Document("name", res.subject.name).append("type", res.subject.type).append("nest", 0);
                entity.insertOne(example);
            }
        }
        i++;

        temp = "";
        for (; data.charAt(i) != ','; i++)
        {
            temp += data.charAt(i);
        }
        //temp += "6";
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

            res.object = resolveTriple(temp, entity, relation);
            i++;
        }
        else
        {
            for (; data.charAt(i) != ':'; i++)
            {
                temp += data.charAt(i);
            }
            res.object = new Triple();
            //temp += "6";
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
            if ((example = (Document)entity.find(bson).first()) == null)
            {
                example = new Document("name", res.object.name).append("type", res.object.type).append("nest", 0);
                entity.insertOne(example);
            }
        }

        res.name = res.subject.name + res.object.name;
        res.type = "嵌套";

        System.out.println("三元组:" + res.subject.name + ' ' + res.predicate + ' ' + res.object.name);

        Document predicate;
        ObjectId id;
        BasicDBObject bson = new BasicDBObject("name", res.predicate).append("subject",res.subject.name).append("object",res.object.name);
        if ((predicate = (Document) relation.find(bson).first()) != null)
        {
            id = predicate.getObjectId("_id");
        }
        else
        {
            predicate = new Document("name", res.predicate).append("subject", res.subject.name).append("object", res.object.name);
            relation.insertOne(predicate);
            id = ((Document)relation.find(bson).first()).getObjectId("_id");
        }

        Document example;
        bson = new BasicDBObject("name", res.name);
        if ((example = (Document)entity.find(bson).first()) == null)
        {
            example = new Document("name", res.name).append("type", res.type).append("nest", id);
            entity.insertOne(example);
        }
        return res;
    }

    public JSONObject relationsByType(String predicate, MongoCollection relation)
    {
        //List<Triple> res = new ArrayList<>();

        /*MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Relation = mongoDatabase.getCollection("relation");*/
        LocalDateTime start1 = LocalDateTime.now();

        BasicDBObject bson = new BasicDBObject("name", predicate);

        FindIterable<Document> triples = relation.find(bson);

        LocalDateTime start2 = LocalDateTime.now();

        List<String> subjects = new ArrayList<>();
        List<String> objects = new ArrayList<>();

        for (Document triple : triples)
        {
            /*Triple temp = new Triple();
            temp.predicate = predicate;
            temp.subject = new Triple();
            temp.subject.name = triple.getString("subject");
            temp.object = new Triple();
            temp.object.name = triple.getString("object");
            res.add(temp);*/
            subjects.add(triple.getString("subject"));
            objects.add(triple.getString("object"));
        }

        JSONObject res = new JSONObject();
        res.put("subjects", subjects);
        res.put("objects", objects);

        LocalDateTime end = LocalDateTime.now();

        //System.out.print("\nformat time:" + Duration.between(start2,end));
        //System.out.print("\ntotal time:" + Duration.between(start1,end));

        //mongoClient.close();
        return res;
    }

    public JSONObject entitiesByType(String type, MongoCollection entity)
    {
        //List<String> res = new ArrayList<String>();

        /*MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        entity = mongoDatabase.getCollection("entity");*/
        //LocalDateTime start1 = LocalDateTime.now();

        BasicDBObject bson = new BasicDBObject("type", type);

        FindIterable<Document> data = entity.find(bson);

        //LocalDateTime start2 = LocalDateTime.now();

        JSONObject res = new JSONObject();
        List<String> entities = new ArrayList<>();

        for (Document e:data)
        {
            entities.add(e.getString("name"));
        }

        res.put("entities", entities);

        /*LocalDateTime end = LocalDateTime.now();

        System.out.print("\nformat time:" + Duration.between(start2,end));
        System.out.print("\ntotal time:" + Duration.between(start1,end));*/

        //mongoClient.close();
        return res;
    }

    public JSONObject downwardRecursion(String entityName, MongoCollection entity, MongoCollection relation)
    {
        /*MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        entity = mongoDatabase.getCollection("entity");
        relation = mongoDatabase.getCollection("relation");*/

        JSONObject res = new JSONObject();
        res.put("name", entityName);

        BasicDBObject bson = new BasicDBObject("name", entityName);

        Document data = (Document) entity.find(bson).first();

        String type = data.getString("type");
        res.put("type", type);
        if (type.compareTo("嵌套") == 0)
        {
            JSONObject temp = findRecursion((ObjectId) data.get("nest"), entity, relation);
            res.put("subject", temp.getJSONObject("subject"));
            res.put("predicate", temp.getString("predicate"));
            res.put("object", temp.getJSONObject("object"));
        }

        //mongoClient.close();;
        return res;
    }

    private JSONObject findRecursion(ObjectId id, MongoCollection entity, MongoCollection relation)
    {
        JSONObject res = new JSONObject();
        Document data =(Document) relation.find(new BasicDBObject("_id", id)).first();

        JSONObject subject = new JSONObject();
        String subjectName = data.getString("subject");
        String objectName = data.getString("object");
        String predicate = data.getString("name");
        //System.out.print("subject:" + subjectName);
        subject.put("name", subjectName);


        data = (Document) entity.find(new BasicDBObject("name", subjectName)).first();
        String type = data.getString("type");
        subject.put("type", type);

        if (type.compareTo("嵌套") == 0)
        {
            JSONObject temp = findRecursion((ObjectId) data.get("nest"), entity, relation);
            //System.out.print(temp);
            subject.put("subject", temp.getJSONObject("subject"));
            subject.put("predicate", temp.getString("predicate"));
            subject.put("object", temp.getJSONObject("object"));
        }

        res.put("subject", subject);
        res.put("predicate", predicate);


        //System.out.print("object:" + objectName);
        JSONObject object = new JSONObject();
        object.put("name", objectName);
        data = (Document) entity.find(new BasicDBObject("name", objectName)).first();

        type =  data.getString("type");
        object.put("type",type);

        if (type.compareTo("嵌套") == 0)
        {
            JSONObject temp = findRecursion((ObjectId) data.get("nest"), entity, relation);
            object.put("subject", temp.getJSONObject("subject"));
            object.put("predicate", temp.getString("predicate"));
            object.put("object", temp.getJSONObject("object"));
        }

        res.put("object", object);

        //System.out.print("subject:" + res.subject.name + " predicate:" + res.predicate + " object:" + res.object.name + "\n");
        return res;
    }

    public JSONObject neighbours(String entityName,MongoCollection entity, MongoCollection relation)
    {
        //LocalDateTime start1 = LocalDateTime.now();

        JSONArray neighbours = new JSONArray();

        /*MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        Relation = mongoDatabase.getCollection("relation");*/

        FindIterable<Document> relations = relation.find(new BasicDBObject("subject",entityName));

        //LocalDateTime start2 = LocalDateTime.now();
        for (Document e : relations)
        {
            JSONObject neighbour = new JSONObject();
            neighbour.put("predicate", e.getString("name"));
            neighbour.put("predicateID", e.getObjectId("_id"));
            //System.out.print(e.getString("object"));
            Document temp = (Document) entity.find(new BasicDBObject("name",e.getString("object"))).first();
            neighbour.put("type", temp.getString("type"));
            neighbour.put("name", temp.getString("name"));
            neighbours.put(neighbour);
        }

        //LocalDateTime end2 = LocalDateTime.now();

        relations = relation.find(new BasicDBObject("object",entityName));

        //LocalDateTime start3 = LocalDateTime.now();

        for (Document e : relations)
        {
            JSONObject neighbour = new JSONObject();
            neighbour.put("predicate", e.getString("name"));
            neighbour.put("predicateID", e.getObjectId("_id"));
            Document temp = (Document) entity.find(new BasicDBObject("name",e.getString("subject"))).first();
            neighbour.put("type", temp.getString("type"));
            neighbour.put("name", temp.getString("name"));
            neighbours.put(neighbour);
        }

        JSONObject res = new JSONObject();
        res.put("neighbours", neighbours);

        /*LocalDateTime end1 = LocalDateTime.now();

        System.out.print("\nformat time:" + Duration.between(start2, end2) + ", " + Duration.between(start3, end1));
        System.out.print("\ntotal time:" + Duration.between(start1, end1));*/

        //mongoClient.close();
        return res;
    }

    public JSONObject upwardRecursion(String id, MongoCollection entity)
    {
        /*MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        entity = mongoDatabase.getCollection("entity");*/
        LocalDateTime start1 = LocalDateTime.now();

        Document data = (Document) entity.find(new BasicDBObject("nest", new ObjectId(id))).first();

        LocalDateTime start2 = LocalDateTime.now();

        JSONObject res = new JSONObject();

        if (data == null)
            res.put("entity", "");
        else
            res.put("entity", data.getString("name"));

        LocalDateTime end = LocalDateTime.now();

        System.out.print("\nformat time:" + Duration.between(start2,end));
        System.out.print("\ntotal time:" + Duration.between(start1,end));

        //mongoClient.close();
        return res;
    }

    public JSONObject entity2entity(String entity1, String entity2, MongoCollection relation)
    {
        JSONObject res = new JSONObject();

        Queue<Stack<String>> way = new LinkedList<>();

        Stack<String> token = new Stack<>();
        token.push(entity1);
        way.offer(token);

        while (!way.isEmpty()) {
            token = way.poll();
            String first = token.pop();
            FindIterable<Document> relations = relation.find(new BasicDBObject("subject", first));
            for (Document e : relations) {
                String predicate = e.getString("name");
                String next = e.getString("object");
                if (next.equals(entity2)){
                    token.push(first);
                    token.push(predicate);
                    token.push(next);
                    res.put("way", token);
                    break;
                }
                if (token.search(next) == 0) {
                    Stack<String> temp = (Stack<String>) token.clone();
                    temp.push(first);
                    temp.push(predicate);
                    temp.push(next);
                    way.offer(temp);
                }
            }
        }
        if (res.isEmpty()){
            res.put("way", "");
        }
        return res;
    }

}
