import Entity.Triple;
import Mongodb.MongodbAPI;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        MongodbAPI mongodb = new MongodbAPI();
        LocalDateTime start = LocalDateTime.now();
        //mongodb.insert("src/main/resources/data/5-14--肾脏替代治疗.csv");

        /*List<Triple> test = mongodb.relationsByType("修饰限定");
        for (Triple triple:test)
        {
            System.out.print("subject:" + triple.subject.name + " object:" + triple.object.name + "\n");
        }*/


        /*List<String> test = mongodb.entitiesByType("症状");
        for (String e:test)
        {
            System.out.print(e + "\n");
        }*/


        //mongodb.downwardRecursion("月经前妊娠期体温高于正常");


        //System.out.print(mongodb.upwardRecursion("5ddce28b4c8dee7f2b4c54df"));

        /*List<String> test = mongodb.neighbours("体温");
        for (String e:test)
        {
            System.out.print(e + " ");
        }*/

        LocalDateTime end = LocalDateTime.now();
        System.out.print("duration:" + Duration.between(start,end));
    }
}
