package tmp;

import com.boen.keywordfilter.KeyWordMatch;
import com.boen.keywordfilter.KeyWordResult;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Boen on 2015/10/20.
 */
public class BenchMarks {
    static AtomicInteger exeNumNow = new AtomicInteger(0);
    public static void main(String a[]){
        final String doc = "草把学生整你妹的我日陈水扁我日被中共KO了找妹妹操逼我日的汗啊 陈水扁啊啊啊啊的办理本科方式发到双方的首发第三方的手佛挡杀佛第三方第三方的手【释义】在各国及其公民之间或中间；关于各国的交往；由两个或更多国家参加；两个或更多。另一个妹的我日陈水扁我日被中共KO了找妹妹操逼我日的汗啊陈水扁啊啊啊啊的容易与国际混淆的概念是跨国。跨国是指一个同时在亚国家、国家和国际范畴内存在的事物，比如跨国公司或者跨国的民间组织等恐怖组织。你他妈的是不把土地爷当神仙看是吧？跟我搞你不是跟电搞吗？你是火葬场开张专烧熟人是吧？小心你头顶上长胞脚底下流胧，吃饭被耶死，喝水被呛死，走路被车撞死，游泳被水淹死，骑车就被摔死，骑马就被踢死，夏天就被晒死，冬天就被冻死，白天被狗咬死，夜晚被鬼吓死，反正你不得好死你个溅B，破鞋，你溅得嘴里长痔疮，你爹我已经把你B草烂了，你怎么还在大街上卖B，滚回家把你女马叫出来给大家草草。看你玉树临风，英俊潇洒，风流倜傥，人见人爱，花见花开，想必一定是人渣中的极品，禽兽中的禽兽。看看啊，你这小脸瘦得，都没个猪样啦！你老说你男朋友长得帅，有钱，长得是有钱，长得跟前列线似的，尿尿都分叉了，赶快去治治吧！半夜三更，厕所无灯。你去解手，掉进茅坑。与虫（是错所里便便上的虫子）搏斗，与屎竞争。无人救你，壮烈牺牲。生得伟大，死的无声。为了纪念你，错所安灯。我晚上做了一个梦：上帝告诉我，我这一生注定孤独，他还说破咒的方法只有一个：给10个傻子发条短信，我当时就哭了，我只认识你一个，我完了……遇事要先从自己身上找原因，别一拉不出屎就怪地球没有吸引力？你喷粪之前先想想你自己都干过什么，有没有资格说别人！我是不够完美，但是我坦白自然，你呢？2014年7月14日,孙某行走时,突然被男子于某拦下。于某称,他因驾驶私家车出事故而急需用钱,但当时手头上并没有人民币,只有“美元”,希望兑换成人民币去处理交通事故。随后通过王某、于某以演双簧的形式,骗取孙某信任。于是,孙某从银行取了现金两万元,在将钱交给于某后,于某等3人便借机溜走,也没有把用秘鲁币冒充的“美元”交给孙某。发现上当的孙某立即报警,于某等人被警方抓获。后经查明,于某、王某、刘某都曾因犯诈骗罪被判刑。今年1月,3人被提起公诉,嫌疑人到案后,已退回全部赃款。山东省威海市文登区人民法院审理后,以诈骗罪分别判处于某、王某、刘某有期徒刑一年零四个月,罚金4万元；有期徒刑一年零三个月,罚金4万元；有期徒刑一年零两个月,罚金4万元。另外,对作案工具,即王某购买的帕萨特轿车一辆,依法予以没收。法官庭后表示,诈骗是指以非法占有为目的,用虚构事实或者隐瞒真相的方法,骗取款额较大的公私财物的行为。由于这种行为完全不使用暴力,而是在一派“平静”的气氛下进行的,加之受害人防范意识较差,较易上当受骗。法官还建议,市民在日常工作生活中应树立反诈骗意识,最好不要感情用事。并且不要轻易相信陌生人,对过于主动自夸有“本事”或“能耐”的人,或者过于热情地希望“帮助”你解决困难的人,要特别注意。另外,还有最重要的一点,就是切忌贪小便宜。";
        long start = System.nanoTime();
        KeyWordMatch.getInstance();
        System.out.println("初始化花费[毫秒]:"+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS));
        KeyWordResult result =  KeyWordMatch.matchKeyWord(doc);
        List<String> klist = result.getKeywords();
        for (String keyword : klist){
            System.out.println(keyword);
        }
      /*  final int runNumPerThread = 100000;
        final int threadNum = 10;
        final int runNum = runNumPerThread * threadNum;
        final long  startRunTime = System.nanoTime();
        for (int j = 0; j < threadNum; j++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < runNumPerThread; i++) {
                         KeyWordMatch.matchKeyWord(doc);
                        if (exeNumNow.incrementAndGet() == runNum) {
                            System.out.println("执行文档数"+exeNumNow.get() +",总耗时毫秒数:" + TimeUnit.MILLISECONDS.convert(System.nanoTime() - startRunTime, TimeUnit.NANOSECONDS));
                        }
                    }
                }
            }).start();
        }*/
    }



    public static void mains(String []a) {
       /* final DoubleArrayTrieStatic trie = new DoubleArrayTrieStatic();
        try {
            long start = System.nanoTime();
            trie.loadKeyWord();
            System.out.println("MILLI_TIME:"+ TimeUnit.MILLISECONDS.convert(System.nanoTime() - start,TimeUnit.NANOSECONDS));
            final long  startRunTime = System.nanoTime();

            final String doc = "草把学生整你妹的我日陈水扁我日被中共KO了找妹妹操逼我日的汗啊 陈水扁啊啊啊啊的办理本科方式发到双方的首发第三方的手佛挡杀佛第三方第三方的手【释义】在各国及其公民之间或中间；关于各国的交往；由两个或更多国家参加；两个或更多。另一个妹的我日陈水扁我日被中共KO了找妹妹操逼我日的汗啊陈水扁啊啊啊啊的容易与国际混淆的概念是跨国。跨国是指一个同时在亚国家、国家和国际范畴内存在的事物，比如跨国公司或者跨国的民间组织等恐怖组织。你他妈的是不把土地爷当神仙看是吧？跟我搞你不是跟电搞吗？你是火葬场开张专烧熟人是吧？小心你头顶上长胞脚底下流胧，吃饭被耶死，喝水被呛死，走路被车撞死，游泳被水淹死，骑车就被摔死，骑马就被踢死，夏天就被晒死，冬天就被冻死，白天被狗咬死，夜晚被鬼吓死，反正你不得好死你个溅B，破鞋，你溅得嘴里长痔疮，你爹我已经把你B草烂了，你怎么还在大街上卖B，滚回家把你女马叫出来给大家草草。看你玉树临风，英俊潇洒，风流倜傥，人见人爱，花见花开，想必一定是人渣中的极品，禽兽中的禽兽。看看啊，你这小脸瘦得，都没个猪样啦！你老说你男朋友长得帅，有钱，长得是有钱，长得跟前列线似的，尿尿都分叉了，赶快去治治吧！半夜三更，厕所无灯。你去解手，掉进茅坑。与虫（是错所里便便上的虫子）搏斗，与屎竞争。无人救你，壮烈牺牲。生得伟大，死的无声。为了纪念你，错所安灯。我晚上做了一个梦：上帝告诉我，我这一生注定孤独，他还说破咒的方法只有一个：给10个傻子发条短信，我当时就哭了，我只认识你一个，我完了……遇事要先从自己身上找原因，别一拉不出屎就怪地球没有吸引力？你喷粪之前先想想你自己都干过什么，有没有资格说别人！我是不够完美，但是我坦白自然，你呢？2014年7月14日,孙某行走时,突然被男子于某拦下。于某称,他因驾驶私家车出事故而急需用钱,但当时手头上并没有人民币,只有“美元”,希望兑换成人民币去处理交通事故。随后通过王某、于某以演双簧的形式,骗取孙某信任。于是,孙某从银行取了现金两万元,在将钱交给于某后,于某等3人便借机溜走,也没有把用秘鲁币冒充的“美元”交给孙某。发现上当的孙某立即报警,于某等人被警方抓获。后经查明,于某、王某、刘某都曾因犯诈骗罪被判刑。今年1月,3人被提起公诉,嫌疑人到案后,已退回全部赃款。山东省威海市文登区人民法院审理后,以诈骗罪分别判处于某、王某、刘某有期徒刑一年零四个月,罚金4万元；有期徒刑一年零三个月,罚金4万元；有期徒刑一年零两个月,罚金4万元。另外,对作案工具,即王某购买的帕萨特轿车一辆,依法予以没收。法官庭后表示,诈骗是指以非法占有为目的,用虚构事实或者隐瞒真相的方法,骗取款额较大的公私财物的行为。由于这种行为完全不使用暴力,而是在一派“平静”的气氛下进行的,加之受害人防范意识较差,较易上当受骗。法官还建议,市民在日常工作生活中应树立反诈骗意识,最好不要感情用事。并且不要轻易相信陌生人,对过于主动自夸有“本事”或“能耐”的人,或者过于热情地希望“帮助”你解决困难的人,要特别注意。另外,还有最重要的一点,就是切忌贪小便宜。";
            List<String> klist = trie.getAllKeyWord(doc);
            for (String keyword : klist){
                System.out.println(keyword);
            }
            final int runNumPerThread = 100000;
            final int threadNum = 10;
            final int runNum = runNumPerThread * threadNum;
            for (int j = 0; j < threadNum; j++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < runNumPerThread; i++) {
                            List<String> list = trie.getAllKeyWord(doc);
                            if (exeNumNow.incrementAndGet() == runNum){
                                System.out.println("MILLI_TIME:" + TimeUnit.MILLISECONDS.convert(System.nanoTime() - startRunTime, TimeUnit.NANOSECONDS));
                            }
                        }
                    }
                }).start();
            }*/
		/*	BufferedReader in = new BufferedReader(new InputStreamReader(DoubleArrayTrieStatic.class.getResourceAsStream("/keyword.txt")));
			String line = null;
			int kecount = 0;
			while ((line = in.readLine()) != null) {
				String[] lineCols = line.split("\\|");
				String keyWord = lineCols[0];
				if (!trie.isKeyWord(keyWord.trim())) {
					System.out.println(keyWord);
					++ kecount;
				};

			}
			System.out.println(kecount);
			in.close();*/
       /* } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
