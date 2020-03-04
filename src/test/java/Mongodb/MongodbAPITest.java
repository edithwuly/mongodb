package Mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MongodbAPITest {

    @Test
    public void relationsByType() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] type = {"导致", "或", "是一种", "条件为", "与", "并发症", "调查病史", "否定修饰", "然后", "同义词",
                "修饰限定", "强导致", "表现为", "弱导致", "作用于", "等价", "定义为", "执行检查", "检查发现", "转变为", "比例为"};

        for (int i = 0; i<type.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            JSONObject test = mongodb.relationsByType(type[i], relation);

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(type[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }

    @Test
    public void entitiesByType() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] type = {"疾病", "嵌套", "症状", "病理概念", "阶段", "否定词", "程度修饰语", "化学物质", "事件", "时间",
                "状态修饰语", "部位", "人群分类", "生物", "病史", "频率修饰语", "年龄", "修饰语", "性别", "生理概念",
                "性质修饰语", "数据", "病症", "颜色", "食品", "检查", "数量修饰语", "方位修饰语", "其他"};

        for (int i = 0; i<type.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            JSONObject test = mongodb.entitiesByType(type[i], entity);

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(type[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }

    @Test
    public void downwardRecursion() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] entityName = {"恶心呕吐头痛喷射性呕吐", "水肿液潴留", "化脓性关节炎", "结肠肿瘤肠结核", "尿路结石肿瘤炎症", "假性血友病", "氯丙嗪胍乙啶亚硝酸盐体位性低血压", "皮肤黏膜色素沉着乏力", "肠道肿瘤", "肾静脉血栓",
                "脊柱畸形冷脓肿脊髓压迫", "甲状腺功能亢进", "支气管扩张慢性肺脓肿支气管肺癌", "恶心无呕吐", "上肢内侧", "胃部疾病肠道感染肠道感染性非疾病肠道肿瘤胰腺疾病肝胆疾病", "库欣综合征", "心排血量突然减少心脏停搏", "全身性疾病", "多囊肾病",
                "肺泡小支气管浆液漏出咳痰", "凝血酶原缺乏症", "绝经", "担忧紧张不安恐惧不愉快综合性情绪体验", "中等量", "晕车", "旋转感", "20%", "吸烟", "脊柱骨折肿瘤压迫结核脊髓炎脊髓损伤",
                "腰痛脊肋角叩痛", "肌收缩性头痛紧张性头痛", "肾动脉狭窄缺血性肾病", "肢体疼痛感觉异常", "脑膜炎蛛网膜下腔出血", "三叉神经痛偏头痛脑膜刺激", "急性肾损伤感染", "遭遇负性生活事件身患重病个性悲观", "骨盆底筋膜肌肉膀胱", "快感缺失",
                "高血压高尿酸血症多毛牙龈增生", "Alport综合征血小板减少性紫癜弥漫性平滑肌瘤甲状腺病变", "颅内动脉瘤破裂蛛网膜下腔出血颅内出血剧烈头痛癫痫发作神志改变", "失眠多梦", "胸腔积液", "全身血管扩张", "重症胃炎溃疡", "大叶性肺炎病毒性肝炎钩端螺旋体病败血症急性胆管炎肝脓肿黄疸发热", "脑出血脑外伤脑肿瘤", "恶性高血压发病数周至数月后",
                "运动神经节病变", "慢性感染重症结核病肿瘤", "纵隔炎", "发绀呼吸困难", "水痘-带状疱疹病毒", "肾病性水肿下肢", "加重高氯性酸中毒", "焦虑情绪体验", "泌尿系统症状", "慢性损伤关节",
                "中上腹部", "多尿", "严重心律失常心脏排血受阻心肌缺血", "肾脏出血", "高度房室传导阻滞窦性心动过缓病态窦房结综合征", "急性细菌性痢疾阿米巴痢疾血吸虫病溃疡性结肠炎结肠憩室炎", "进食某些红色蔬菜", "肾小球间质损伤", "前列腺急性炎症出血积脓纤维化", "多发性骨髓瘤肾小球",
                "骨质肌肉", "急性肾炎综合征急进性肾炎综合征", "延髓第四脑室", "焦虑紧张情绪激动", "胃酸缺乏", "减少血液携氧量", "消失", "革兰阴性杆菌败血症", "血行播散型", "前庭障碍性呕吐呕吐听力障碍眩晕",
                "严重休克血栓闭塞性脉管炎雷诺（Raynaud）病肢端发绀症", "意识大部分丧失", "嗜酸性粒细胞", "缩窄", "颠茄类氰化物酒精中毒癫痫", "甲状腺功能亢进结缔组织病", "呼吸系统症状", "肥胖型生殖无能症Frohlich综合征", "听神经瘤小脑肿瘤第四脑室肿瘤其他部位肿瘤颅内占位性病变", "血小板生成减少",
                "小动脉性肾硬化症肾血管疾病", "皮褶厚度", "腰背部弥漫性疼痛", "无脓痰", "心律失常心肌病变", "呕吐反酸", "膜性肾病血栓栓塞并发症", "红细胞携氧量减少血氧含量降低血源性呼吸困难", "癌肿浸润管壁", "发热"};

        for (int i = 0; i<entityName.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            mongodb.downwardRecursion(entityName[i], entity, relation);

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(entityName[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }

    @Test
    public void neighbours() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] entityName = {"红细胞白细胞巨噬细胞纤维蛋白渗出物黏液吸入的尘埃", "过量激素单胺类递质受体抑郁", "急剧恶化", "溶血性黄疸浅柠檬色", "绞痛叩痛剧烈", "Alport综合征血尿", "局灶节段性肾小球硬化", "膀胱内血块", "膀胱壁改变神经肌肉膀胱过度活动", "软组织腰部感染性炎症",
                "遗传性或获得性近端肾小管复合性功能缺陷疾病Fanconi综合征", "壬苯聚醇", "糖尿病肾病糖尿病微血管并发症", "间质性肾炎", "肾性尿崩症", "血清C3下降", "内脏病变传入神经脊髓", "间质性肾炎泌尿系统疾病", "稀薄", "胰腺炎胰腺癌脓肿破溃主动脉瘤纵隔肿瘤上消化道器官组织疾病",
                "阵发性睡眠性血红蛋白尿", "少年Frohlich综合征肘外翻膝内翻畸形生殖器官不发育脂肪多积聚于躯干", "慢性少量出血", "终末段血尿", "Pickwickian综合征肥胖矮小通气功能减低嗜睡发绀杵状指", "黏液", "腹腔脏器", "败血症外伤前列腺癌甲状腺癌", "活动", "镜检无红细胞",
                "较少出血量", "升高胆红素", "心脏跳动", "心悸心动过速胸闷头晕", "纤维化", "严重感染持续增高血压肾毒性药物急剧恶化肾功能", "骨软化症成人脊柱突出骨盆突出", "血氧含量减少", "迷路炎梅尼埃（Ménière）病晕动病位置性眩晕前庭神经元炎药物中毒周围性眩晕", "入睡困难",
                "膀胱炎尿道炎", "遗传易感性", "运动神经节病变口咽性吞咽困难", "月经前妊娠期", "直肠肛门病变", "左侧腰背部放射痛", "感觉障碍", "直肠肛管损伤非特异性直肠炎放射性直肠炎直肠息肉直肠癌痔肛裂肛瘘直肠肛管疾病", "肩背部", "不平衡",
                "生成迟缓", "50g／L还原血红蛋白", "胆汁外溢胆汁性腹膜炎", "肋软骨炎胸痛肋软骨单个多个隆起压痛无红肿", "听力下降", "胆汁外溢", "大量蛋白尿高血压高血脂", "出血血管关节脱位骨折渗出组织液肿胀疼痛关节骨质肌肉韧带微生物产物药物异种血清", "头痛型", "樟脑",
                "高转化性骨病低转化性骨病混合性骨病肾性骨营养不良", "泌尿系统疾病", "心脏瓣膜病", "逼尿肌收缩", "颅底凹入症颅骨肿瘤颅骨疾病", "脊柱骨折肿瘤压迫结核脊髓炎", "肌肉疼痛", "常染色体显性多囊肾病肾功能进行性下降", "脊髓空洞症多发性神经根炎脑动脉粥样硬化恢复期急性传染病", "呕血",
                "IgA肾病泌尿系统疾病", "溶血", "缺血性肠病腹主动脉瘤", "异常血红蛋白衍生物", "急性心肌梗死心肌炎心包炎心力衰竭", "慢性便秘", "低钠饮食进食差", "耳", "焦虑症抑郁症睡眠障碍精神分裂症应激相关障碍", "小肠大部分切除术吸收不良综合征小儿乳糜泻热带口炎性腹泻成人乳糜泻消化酶分泌减少",
                "急性胃肠炎霍乱副霍乱细菌性食物中毒急性食物中毒", "眼花等", "cardiac asthma", "肾周脓肿", "重症急性肾炎急性肾衰竭", "皮下血点皮下瘀斑", "瘢痕排尿困难", "毛细血管静水压增加血浆胶体渗透压降低", "肾炎家族史持续性血尿进行性双侧2000～8000Hz的感音神经性耳聋", "癔症发作",
                "急性呼吸道炎症较少痰量", "癔症抑郁症焦虑症精神心理疾病", "系统性红斑狼疮肾炎过敏性紫癜肾炎急性肾炎综合征", "白痰", "代谢障碍性", "尿路感染炎症性疾病", "逼尿肌不能完全主动尿液排出", "消瘦性功能减退闭经厌食", "迷走神经兴奋性增高", "血栓栓塞疾病吸收热"};

        for (int i = 0; i<entityName.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            mongodb.neighbours(entityName[i], entity, relation);

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(entityName[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }

    @Test
    public void upwardRecursion() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] id = {"5def5cb10258310011102276", "5de8729e92492357db04116f", "5de86a6d1dc230659ccc64d7", "5de8747f95b2586ad55528f2", "5def5d1106d0910090b5c8f3", "5de874f0c21eb9354b798ca3", "5de875bd6caf7928637008c4", "5de874fa1468df53c2003dd2", "5def5c57813df177b5c7ef0d", "5def5b9543f14a57484fb766",
                "5def5c13d49090289c4854bf", "5de875d273fc20589ebe5d29", "5def5bb8a72d792da3a6deed", "5de86a7a457c76666b9bbba2", "5def5bdae8211b4c755b6437", "5def5b71a003b547e856c86b", "5def5bdbe8211b4c755b6475", "5def5b7bbf45e36c287ec928", "5de869f2fe5b684f2c1a4e5e", "5def5c02ae0a7306bae55fd9",
                "5de86ab92337cb53f33ba6ef", "5de8696928e2a02a903c9475", "5de87458b1e5884c89771adb", "5de86a5ff256742dad617c3d", "5def5c24c8e04e16693a3c05", "5def5d4b2bc96d64992e2ecb", "5de86a8765f9271ccf15a588", "5de8698a295ec8321d93a00f", "5def5d6aef937670bff6fff1", "5de872a96b4be91ec3b8a716",
                "5de86999d2639e76b1ae7d51", "5de869f1fe5b684f2c1a4de1", "5de8722122d2ac23eba86e27", "5de86ac279712c26f369d7bf", "5de873f713060f0f23468da6", "5de86a6d1dc230659ccc6534", "5de8722122d2ac23eba86e02", "5de86a60f256742dad617ccc", "5de8735d42906745a27037c2", "5def5ce2b8b8ed45fe4f2ea4",
                "5def5ce2b8b8ed45fe4f2ed8", "5de874b1699b8671bc2a8843", "5def5baf05b96e5213ae5e3f", "5def5c86b87e67490f4e1de1", "5de875d173fc20589ebe5d1c", "5def5d7fc0823e154f1a2841", "5def5d512bc96d64992e30c6", "5de8698b295ec8321d93a027", "5def5b72a003b547e856c89a", "5def5d6bef937670bff7005e",
                "5de8681cd4db75090b82b44b", "5de873f713060f0f23468d2e", "5de8693c22821011aff2afdc", "5de87382d777be299431861b", "5de86999d2639e76b1ae7c57", "5def5c15d49090289c485572", "5def5c37b360486b684514ae", "5def5d1206d0910090b5c986", "5def5c2cac83070b072ae27a", "5de87458b1e5884c89771a9d",
                "5def5b71a003b547e856c87d", "5de875d273fc20589ebe5d32", "5de86a8965f9271ccf15a742", "5def5d6bef937670bff70041", "5def5d2ffe19a22cc624e0b5", "5de872181523d646f6daf07e", "5def5d1106d0910090b5c94b", "5def5d81c0823e154f1a28db", "5de86a8965f9271ccf15a846", "5de874629d056376c23c078c",
                "5de8749e25cef15b44b2ec3c", "5de875bc6caf79286370086f", "5de87365f202392cf5e083a2", "5de873a51d03d6136652be19", "5def5d1106d0910090b5c91f", "5def5d69ef937670bff6ffa1", "5def5ca10ecce26a9c9fe50f", "5def5d9bf3839739990a0080", "5def5d4e2bc96d64992e2fe0", "5def5b7abf45e36c287ec8cb",
                "5de872d71fbdd22882ecfa6b", "5de8745bb1e5884c89771bd1", "5def5d30fe19a22cc624e0ff", "5de869f1fe5b684f2c1a4dbd", "5def5c0ad4a37127428d76bc", "5de87420756fcd5e5aa84e04", "5de874299efa531adefd3c98", "5de872191523d646f6daf0c8", "5de86a8965f9271ccf15a869", "5de8742a9efa531adefd3d03",
                "5de8742a9efa531adefd3cde", "5def5d2efe19a22cc624e03a", "5de8745bb1e5884c89771bd3", "5de87364f202392cf5e082c6", "5def5d9cf3839739990a00bb", "5def5d1306d0910090b5c9d4", "5de869f1fe5b684f2c1a4d0f", "5de8681bd4db75090b82b40a", "5de87421756fcd5e5aa84e88", "5def5d9ef3839739990a019e"};

        for (int i = 0; i<id.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            mongodb.upwardRecursion(id[i], entity);

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(id[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }
}