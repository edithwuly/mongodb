import Entity.Triple;
import Mongodb.MongodbAPI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String[] args ) throws IOException {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        LocalDateTime start = LocalDateTime.now();
        //mongodb.insert("src/main/resources/data/意识障碍5.csv");


        JSONObject test = mongodb.relationsByType("导致", relation);
        //System.out.print("subjects:" + test.get("subjects") + "\nobjects:" + test.get("objects") + "\n");


        //JSONObject test = mongodb.entitiesByType("症状", entity);
        //System.out.print(test);


        //System.out.print(mongodb.downwardRecursion("月经前妊娠期体温高于正常", entity, relation));


        //System.out.print(mongodb.upwardRecursion("5de8681bd4db75090b82b31d", entity));


        //System.out.print(mongodb.neighbours("体温", entity, relation));

        LocalDateTime end = LocalDateTime.now();

        System.out.print("\nduration:" + Duration.between(start,end));

        mongoClient.close();
    }
}
