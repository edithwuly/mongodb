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
        MongoDatabase mongoDatabase = mongoClient.getDatabase("smalltest");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        /*LocalDateTime start = LocalDateTime.now();
        mongodb.insert("src/main/resources/data/5-1--总　 　论.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-2--肾小球疾病概述.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-3-1-急性肾小球肾炎.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-3-2-急进性肾小球肾炎.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-3-3-慢性肾小球肾炎.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-3-4-无症状性血尿或（和）蛋白尿.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-4--肾病综合征.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-5--IgA　肾　病.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-6-1-狼疮性肾炎.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-6-2-糖尿病肾病.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-7-1-急性间质性肾炎.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-7-2-慢性间质性肾炎.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-8--尿路感染.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-9-1-肾小管性酸中毒.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-9-2-Fanconi综合征.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-10-1-肾动脉狭窄.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-10-2-肾动脉栓塞和血栓形成.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-10-3-小动脉性肾硬化症.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-10-4-肾静脉血栓形成.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-10-5-介入肾脏病学.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-11-1-常染色体显性多囊肾病.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-11-2-Alport综合征.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-12--急性肾损伤.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-13--慢性肾衰竭.csv", entity, relation);
        mongodb.insert("src/main/resources/data/5-14--肾脏替代治疗.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便秘1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便秘2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便秘3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便秘4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便秘5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便血1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便血2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便血3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/便血4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/抽搐与惊厥1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/抽搐与惊厥2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/抽搐与惊厥3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/抽搐与惊厥4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/抽搐与惊厥5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/恶心与呕吐1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/恶心与呕吐2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/恶心与呕吐3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/恶心与呕吐4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/恶心与呕吐5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发绀1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发绀2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发绀3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发绀4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热6.csv", entity, relation);
        mongodb.insert("src/main/resources/data/发热7.csv", entity, relation);
        mongodb.insert("src/main/resources/data/肥胖1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/肥胖2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/肥胖3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/肥胖4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/肥胖5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹痛1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹痛2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹痛3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹痛4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹痛5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹泻1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹泻2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹泻3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹泻4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腹泻5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咯血1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咯血2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咯血3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咯血4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/关节痛1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/关节痛2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/关节痛3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/关节痛4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呼吸困难1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呼吸困难2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呼吸困难3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呼吸困难4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/黄疸1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/黄疸2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/黄疸3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/黄疸4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/黄疸5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/黄疸6.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咳嗽与咳痰1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咳嗽与咳痰2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咳嗽与咳痰3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咳嗽与咳痰4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/咳嗽与咳痰5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿频、尿急与尿痛1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿频、尿急与尿痛2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿频、尿急与尿痛3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿失禁1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿失禁2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿失禁3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿失禁4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/尿失禁5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呕血1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呕血2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呕血3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/呕血4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/排尿困难1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/排尿困难2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/排尿困难3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/排尿困难4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/皮肤黏膜出血1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/皮肤黏膜出血2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/皮肤黏膜出血3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/皮肤黏膜出血4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/情感症状1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/情感症状2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/情感症状3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/少尿、无尿与多尿1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/少尿、无尿与多尿2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/少尿、无尿与多尿3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/水肿1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/水肿2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/水肿3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/水肿4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/头痛1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/头痛2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/头痛3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/头痛4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/头痛5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/吞咽困难1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/吞咽困难2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/吞咽困难3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/吞咽困难4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/吞咽困难5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/消瘦1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/消瘦2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/消瘦3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/消瘦4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/心悸1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/心悸2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/心悸3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/心悸4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/胸痛1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/胸痛2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/胸痛3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/胸痛4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/眩晕1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/眩晕2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/眩晕3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/眩晕4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/血尿1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/血尿2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/血尿3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/血尿4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腰背痛1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腰背痛2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腰背痛3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/腰背痛4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/意识障碍1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/意识障碍2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/意识障碍3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/意识障碍4.csv", entity, relation);
        mongodb.insert("src/main/resources/data/意识障碍5.csv", entity, relation);
        mongodb.insert("src/main/resources/data/晕厥1.csv", entity, relation);
        mongodb.insert("src/main/resources/data/晕厥2.csv", entity, relation);
        mongodb.insert("src/main/resources/data/晕厥3.csv", entity, relation);
        mongodb.insert("src/main/resources/data/晕厥4.csv", entity, relation);*/


        //JSONObject test = mongodb.relationsByType("导致", relation);
        //System.out.print(test);
        //System.out.print("subjects:" + test.get("subjects") + "\nobjects:" + test.get("objects") + "\n");


        //JSONObject test = mongodb.entitiesByType("疾病", entity);
        //System.out.print(test);


        //System.out.print(mongodb.downwardRecursion("月经前妊娠期体温高于正常", entity, relation));


        //System.out.print(mongodb.upwardRecursion("5e917551fa85f33fd86f851b", entity));


        //System.out.print(mongodb.neighbours("体温", entity, relation));

        //LocalDateTime end = LocalDateTime.now();

        //System.out.print("\nduration:" + Duration.between(start,end));

        System.out.print(mongodb.entity2entity("系统性红斑狼疮","肾损害", relation));

        mongoClient.close();
    }
}
