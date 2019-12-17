import Entity.Triple;
import Mongodb.MongodbAPI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection Entity = mongoDatabase.getCollection("entity");
        MongoCollection Relation = mongoDatabase.getCollection("relation");

        LocalDateTime start = LocalDateTime.now();
        //mongodb.insert("src/main/resources/data/意识障碍5.csv");


        JSONObject test = mongodb.relationsByType("或", Relation);
        //System.out.print("subjects:" + test.get("subjects") + "\nobjects:" + test.get("objects") + "\n");


        /*List<String> test = mongodb.entitiesByType("症状");
        for (String e:test)
        {
            System.out.print(e + "\n");
        }*/


        //mongodb.downwardRecursion("月经前妊娠期体温高于正常");


        //System.out.print(mongodb.upwardRecursion("5de8681bd4db75090b82b31d"));

        /*List<String> test = mongodb.neighbours("体温");
        for (String e:test)
        {
            System.out.print(e + " ");
        }*/

        LocalDateTime end = LocalDateTime.now();
        System.out.print("duration:" + Duration.between(start,end));

        mongoClient.close();
    }
}
