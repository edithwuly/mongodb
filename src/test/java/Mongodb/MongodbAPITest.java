package Mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONObject;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Vector;


public class MongodbAPITest {

    @Test
    public void relationsByType() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("bigtest");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] type = {"导致", "或", "是一种", "条件为", "与", "并发症", "调查病史", "否定修饰", "然后", "同义词",
                "修饰限定", "强导致", "表现为", "弱导致", "作用于", "等价", "定义为", "执行检查", "检查发现", "转变为", "比例为", "主语", "宾语"};

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
        MongoDatabase mongoDatabase = mongoClient.getDatabase("bigtest");

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
        MongoDatabase mongoDatabase = mongoClient.getDatabase("bigtest");

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
        MongoDatabase mongoDatabase = mongoClient.getDatabase("bigtest");

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
        MongoDatabase mongoDatabase = mongoClient.getDatabase("bigtest");

        MongoCollection entity = mongoDatabase.getCollection("entity");
        MongoCollection relation = mongoDatabase.getCollection("relation");

        //small
        /*String[] id = {"5ebe4d41b0782168776d9894", "5ebe4df0b0782168776dd7e4", "5ebe4d15b0782168776d8531", "5ebe4d15b0782168776d856d", "5ebe4cc8b0782168776d59e4", "5ebe4ca7b0782168776d32b2", "5ebe4cc1b0782168776d53cf", "5ebe4dcbb0782168776dcbd4", "5ebe4d5cb0782168776da3e2", "5ebe4d2fb0782168776d90f5",
                "5ebe4de0b0782168776dd2e7", "5ebe4ca4b0782168776d2e9d", "5ebe4cd6b0782168776d62dd", "5ebe4dc7b0782168776dca79", "5ebe4ca9b0782168776d3727", "5ebe4d25b0782168776d8c56", "5ebe4dc8b0782168776dcae4", "5ebe4d03b0782168776d7c3a", "5ebe4dcab0782168776dcb8d", "5ebe4ce8b0782168776d6dd1",
                "5ebe4cc4b0782168776d56ba", "5ebe4dc8b0782168776dcaae", "5ebe4cf8b0782168776d76b5", "5ebe4cd7b0782168776d6379", "5ebe4ca4b0782168776d2cd1", "5ebe4ca1b0782168776d26c8", "5ebe4cb2b0782168776d452a", "5ebe4d7bb0782168776dafee", "5ebe4d9ab0782168776dbb27", "5ebe4d5cb0782168776da3e0",
                "5ebe4d79b0782168776daf41", "5ebe4d4bb0782168776d9c9e", "5ebe4db1b0782168776dc33e", "5ebe4dd6b0782168776dcf71", "5ebe4cb7b0782168776d4a61", "5ebe4cccb0782168776d5b84", "5ebe4ca1b0782168776d278f", "5ebe4d16b0782168776d85a1", "5ebe4cb9b0782168776d4bbb", "5ebe4ce3b0782168776d6b2d",
                "5ebe4db0b0782168776dc2ba", "5ebe4d30b0782168776d9137", "5ebe4d5ab0782168776da334", "5ebe4ddeb0782168776dd244", "5ebe4cb7b0782168776d4a7d", "5ebe4c9db0782168776d1b8a", "5ebe4ca4b0782168776d2d9e", "5ebe4d42b0782168776d9901", "5ebe4cd5b0782168776d622a", "5ebe4cfdb0782168776d7962",
                "5ebe4ca3b0782168776d2c68", "5ebe4cc5b0782168776d5723", "5ebe4d87b0782168776db46d", "5ebe4d4eb0782168776d9dc0", "5ebe4ca0b0782168776d2336", "5ebe4ca0b0782168776d23ea", "5ebe4d54b0782168776da07d", "5ebe4ca3b0782168776d2c5f", "5ebe4cb8b0782168776d4aac", "5ebe4ceeb0782168776d7199",
                "5ebe4ccfb0782168776d5e5b", "5ebe4cdeb0782168776d67c5", "5ebe4cb6b0782168776d4859", "5ebe4d5cb0782168776da401", "5ebe4cfcb0782168776d78e6", "5ebe4d48b0782168776d9b3c", "5ebe4cadb0782168776d3e48", "5ebe4ce9b0782168776d6e99", "5ebe4ce1b0782168776d69e7", "5ebe4cfeb0782168776d79a4",
                "5ebe4ce4b0782168776d6b3a", "5ebe4ca7b0782168776d32ff", "5ebe4d25b0782168776d8c6c", "5ebe4d22b0782168776d8b4d", "5ebe4d49b0782168776d9be3", "5ebe4ce8b0782168776d6dc0", "5ebe4cb8b0782168776d4ad5", "5ebe4d54b0782168776da08d", "5ebe4ca3b0782168776d2b3a", "5ebe4d3db0782168776d96fb",
                "5ebe4ca3b0782168776d2acc", "5ebe4c9eb0782168776d1d26", "5ebe4cc2b0782168776d552f", "5ebe4d87b0782168776db48f", "5ebe4dc4b0782168776dc985", "5ebe4d97b0782168776dba3b", "5ebe4cadb0782168776d3d85", "5ebe4d10b0782168776d825e", "5ebe4cd4b0782168776d6188", "5ebe4cb0b0782168776d418c",
                "5ebe4d87b0782168776db487", "5ebe4d39b0782168776d9568", "5ebe4caab0782168776d398c", "5ebe4d68b0782168776da8cc", "5ebe4caeb0782168776d3fbb", "5ebe4caeb0782168776d3f0f", "5ebe4d2cb0782168776d8fdf", "5ebe4de9b0782168776dd5ac", "5ebe4cb1b0782168776d42c1", "5ebe4d47b0782168776d9af2"};
         */

        //big
        String[] id = {"5ebe84a51ba93e08efa50a30", "5ebe82921ba93e08efa4a881", "5ebe8586f937a9096dcd1c1b", "5ebe82491ba93e08efa48b9f", "5ebe851df937a9096dccffe2", "5ebe8585f937a9096dcd1bf7", "5ebe82781ba93e08efa49baf", "5ebe84a71ba93e08efa50b40", "5ebe82721ba93e08efa499a4", "5ebe84231ba93e08efa4f3f7",
                "5ebe82431ba93e08efa48853", "5ebe839b1ba93e08efa4e05a", "5ebe82df1ba93e08efa4bf79", "5ebe82441ba93e08efa488a0", "5ebe82581ba93e08efa491b5", "5ebe850ef937a9096dccfb95", "5ebe851ff937a9096dcd0083", "5ebe83a21ba93e08efa4e161", "5ebe857ff937a9096dcd1a7f", "5ebe822c1ba93e08efa47d65",
                "5ebe82141ba93e08efa463bc", "5ebe82951ba93e08efa4aa3e", "5ebe843d1ba93e08efa4f7bd", "5ebe862cf937a9096dcd4118", "5ebe891ce8d7790b3e91cf34", "5ebe86f9f937a9096dcd6a49", "5ebe8903e8d7790b3e91cb56", "5ebe820f1ba93e08efa45994", "5ebe8b04e8d7790b3e92124d", "5ebe82781ba93e08efa49bbf",
                "5ebe821f1ba93e08efa472b4", "5ebe8602f937a9096dcd3931", "5ebe86b8f937a9096dcd5dfd", "5ebe86e5f937a9096dcd6678", "5ebe82541ba93e08efa4907f", "5ebe82111ba93e08efa45d82", "5ebe8b4be8d7790b3e921b13", "5ebe8874f937a9096dcdab04", "5ebe82311ba93e08efa47ffb", "5ebe82601ba93e08efa49483",
                "5ebe85e3f937a9096dcd3239", "5ebe853ff937a9096dcd0983", "5ebe82881ba93e08efa4a397", "5ebe8b69e8d7790b3e921ee0", "5ebe82f81ba93e08efa4c484", "5ebe8695f937a9096dcd56c6", "5ebe858af937a9096dcd1d60", "5ebe84eef937a9096dccf286", "5ebe878cf937a9096dcd847c", "5ebe82e31ba93e08efa4c02c",
                "5ebe82281ba93e08efa47a44", "5ebe8544f937a9096dcd0af9", "5ebe82891ba93e08efa4a3f7", "5ebe8b47e8d7790b3e921a7e", "5ec1f1c0e013c411903b14f4", "5ebe8901e8d7790b3e91cb03", "5ebe8b2de8d7790b3e92176e", "5ebe880df937a9096dcd9a1d", "5ebe8506f937a9096dccf968", "5ebe84971ba93e08efa50575",
                "5ebe82321ba93e08efa48098", "5ebe82f81ba93e08efa4c463", "5ebe886ef937a9096dcdaa16", "5ebe8768f937a9096dcd7e9e", "5ebe83c01ba93e08efa4e603", "5ebe855ff937a9096dcd126b", "5ebe8b4de8d7790b3e921b4f", "5ebe8b2ae8d7790b3e9216f9", "5ebe8b47e8d7790b3e921a78", "5ebe84b21ba93e08efa50efd",
                "5ebe82421ba93e08efa487ed", "5ebe8817f937a9096dcd9bbf", "5ebe86e5f937a9096dcd6666", "5ebe85d2f937a9096dcd2e4d", "5ebe8920e8d7790b3e91cfd5", "5ebe875ef937a9096dcd7cb9", "5ebe8aa5e8d7790b3e92071e", "5ebe88d4e8d7790b3e91c428", "5ebe86dbf937a9096dcd6499", "5ebe8522f937a9096dcd017e",
                "5ebe86c9f937a9096dcd6128", "5ebe88f4e8d7790b3e91c921", "5ebe83141ba93e08efa4c9a4", "5ebe821b1ba93e08efa46e4c", "5ebe82f31ba93e08efa4c3ad", "5ebe82231ba93e08efa47656", "5ebe877af937a9096dcd81ca", "5ebe8acae8d7790b3e920b94", "5ebe882cf937a9096dcd9ee7", "5ebe893ce8d7790b3e91d458",
                "5ebe82e51ba93e08efa4c0b9", "5ebe84681ba93e08efa4fd50", "5ebe8634f937a9096dcd42f5", "5ebe83ee1ba93e08efa4ec40", "5ebe83381ba93e08efa4cf92", "5ebe82161ba93e08efa4665a", "5ebe83741ba93e08efa4d9c1", "5ebe8507f937a9096dccf9bc", "5ebe8905e8d7790b3e91cbc1", "5ebe8a73e8d7790b3e920076"};


        for (int i = 0; i<id.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            mongodb.upwardRecursion(id[i], entity);

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(id[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }

    @Test
    public void entity2entity() {
        MongodbAPI mongodb = new MongodbAPI();
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("smalltest");

        MongoCollection relation = mongoDatabase.getCollection("relation");

        String[] entity1={"嗜睡意识障碍","消瘦体重低于正常低限","链球菌感染急性肾炎","快速度","血压下降脉搏微弱数秒数分钟自然苏醒无后遗症","多糖体成分多核苷酸","负氮平衡消瘦","持续性溢尿","重度休克心律失常Adams‐Stokes综合征", "5‐羟色胺去甲肾上腺素再摄取抑制剂抗抑郁剂",
                "胆道发育不全","1型糖尿病Ⅳ期","运动不受限","躯体症状","少尿排尿困难","肺栓塞心血管疾病","膀胱癌前列腺癌","急性间质性肾炎间质性肾炎","腰背部","手术出血不止",
                "胸背部下腹腰部两侧腹股沟下肢","肾病综合征感染高凝微量元素缺乏内分泌紊乱免疫功能低下","短暂意识丧失","肌收缩性头痛头痛型癫痫","慢性支气管炎慢性阻塞性肺气肿支气管哮喘弥漫性泛细支气管炎","皮炎","终末段","过度通气呼吸性碱中毒","面色苍白发绀","先天性视力减退屈光不正眼肌麻痹青光眼",
                "快慢","不育","支气管胸膜瘘支气管扩张肺脓肿增多痰量","恶性","中毒史","细胞免疫功能低下","失用性骨质疏松高脂血症性关节病","房室传导阻滞","缺乏食欲","高血压血肌酐升高",
                "血性","脑卒中痴呆骨盆外伤","实质脏器包膜牵张胀痛","IgA肾病新月体性肾炎急性肾衰竭","膜性肾病突发性腰痛肋腹痛血尿肾功能受损肾静脉血栓","单瘫偏瘫","食管纵隔病变胸骨后胸痛","无症状细菌尿无急性尿路感染病史","浅在性","急性肾小管坏死肾小管损害无急性肾炎综合征"};
        String[] entity2={"吸气期呼气期","增多还原血红蛋白皮肤黏膜青紫色","脑出血脑栓塞脑血栓高血压脑病偏头痛脑血管疾病","继发性肾实质性恶性高血压","放射线","排汗","自主神经功能紊乱心悸胸闷气短皮肤潮红苍白口干便秘腹泻出汗尿意频繁","下肢静脉血栓下腔静脉血栓","多囊肾病","小肠大部分切除术吸收不良综合征小儿乳糜泻热带口炎性腹泻成人乳糜泻消化酶分泌减少肠黏膜吸收面积减少吸收障碍",
                "神经源性","肿瘤病史","肺循环","心肌顺应性降低心脏舒张受限静脉回流受阻静脉淤血静脉压增高腹水胸水肢体水肿","尿急难以控制尿意","压力性","颅外","航空乘船乘车","血红蛋白","移动感",
                "近端肾小管损伤肾间质水肿肾实质损伤","黏液性水肿","囊肿出血囊肿感染结石","淋巴分泌过多","忧郁症","无肝囊肿","大出血肾病综合征肝肾综合征","咳嗽胸痛","慢性肠炎慢性痢疾肠结核肿瘤","心动",
                "抑郁自杀行为自杀观念自伤","旋转感摇晃感","脓痰","空腔脏器","即刻呕吐","晚期慢性肾衰竭重出血胃肠道出血脑出血","结肠癌肠结核肠道恶性淋巴瘤肠套叠","发育性","组织液渗出输液血红蛋白血细胞比容逐渐降低血液稀释","单纯性肾囊肿",
                "原胆烷醇酮","感觉丧失肢体麻木肢体烧灼感肢体疼痛感肢体深反射迟钝肢体深反射消失神经肌肉兴奋性增加","肌肉紧张运动不安","深棕色","痔疮肛裂","弛张热","超过","脊膜脊神经后根","血清补体降低","肠套叠"};

        for (int i = 0; i<entity1.length;i++) {
            LocalDateTime start = LocalDateTime.now();

            System.out.print(mongodb.entity2entity(entity1[i], entity2[i], 5, relation));

            LocalDateTime end = LocalDateTime.now();

            String duration = Duration.between(start, end).toString();

            System.out.print(entity1[i] + "->" + entity2[i] + "\t" + duration.substring(2, duration.length()-1) + "\n");
        }

        mongoClient.close();
    }

    @Test
    public void testThread() throws InterruptedException {
        Vector<Thread> threadVector = new Vector<>();
        for (int i = 0; i < 10; i++) {
            threadTest temp = new threadTest();
            threadVector.add(temp);
            temp.id = i;
            temp.start();
        }

        for (Thread e : threadVector){
            e.join();
        }
    }
}