package Mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;

public class threadTest extends Thread {

    public int id;

    @Override
    public void run() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("smalltest");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        LocalDateTime start = LocalDateTime.now();
        JSONObject test = mongodb.relationsByType("导致", relation);
        LocalDateTime end = LocalDateTime.now();
        String duration = Duration.between(start, end).toString();
        System.out.println("thread " + id + " relationByType\t" + duration.substring(2, duration.length()-1));

        start = LocalDateTime.now();
        test = mongodb.entitiesByType("疾病", entity);
        end = LocalDateTime.now();
        duration = Duration.between(start, end).toString();
        System.out.println("thread " + id + " entitiesByType\t" + duration.substring(2, duration.length()-1));

        start = LocalDateTime.now();
        mongodb.downwardRecursion("胃部疾病肠道感染肠道感染性非疾病肠道肿瘤胰腺疾病肝胆疾病", entity, relation);
        end = LocalDateTime.now();
        duration = Duration.between(start, end).toString();
        System.out.println("thread " + id + " downwardRecursion\t" + duration.substring(2, duration.length()-1));

        start = LocalDateTime.now();
        mongodb.neighbours("发热", entity, relation);
        end = LocalDateTime.now();
        duration = Duration.between(start, end).toString();
        System.out.println("thread " + id + " neighbours\t" + duration.substring(2, duration.length()-1));

        start = LocalDateTime.now();
        mongodb.upwardRecursion("5ebe4c9db0782168776d1991", entity);
        end = LocalDateTime.now();
        Duration.between(start, end).toString();
        System.out.println("thread " + id + " upwardRecursion\t" + duration.substring(2, duration.length()-1));

        start = LocalDateTime.now();
        mongodb.entity2entity("消瘦体重低于正常低限", "增多还原血红蛋白皮肤黏膜青紫色", 5, relation);
        end = LocalDateTime.now();
        Duration.between(start, end).toString();
        System.out.println("thread " + id + " entity2entity\t" + duration.substring(2, duration.length()-1));

        mongoClient.close();
    }
}
