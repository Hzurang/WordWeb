package cn.hzu.englishweb;

import cn.hzu.englishweb.dao.ListenDao;
import cn.hzu.englishweb.dao.WordDao;
import cn.hzu.englishweb.dao.WordSysDao;
import cn.hzu.englishweb.model.Listen;
import cn.hzu.englishweb.model.Word;
import cn.hzu.englishweb.service.SpiderService;
import cn.hzu.englishweb.utils.StringUtils;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderTest {
    @Resource
    private SpiderService spiderService;

    @Resource
    private WordSysDao wordSysDao;

    @Resource
    private WordDao wordDao;

    @Resource
    private ListenDao listenDao;

//    @Test
//    public void TestWord() throws Exception {
//        List<Word> wordList = new ArrayList<>();
//        wordList = spiderService.getDataCET4();
//        wordList.forEach(d->wordSysDao.addWord(d));
//    }

//    @Test
//    public void testSpider() throws Exception {
//        List<Word> res = spiderService.getWord();
//        res.forEach(d->wordDao.addWord(d));
//    }

//    @Test
//    public void TestWordCET6() throws Exception {
//        List<Word> wordList = new ArrayList<>();
//        wordList = spiderService.getDataCET6();
//        wordList.forEach(d->wordSysDao.addWord(d));
//    }

//    @Test
//    public void TestWordTF() throws Exception {
//        List<Word> wordList = new ArrayList<>();
//        wordList = spiderService.getDataTF();
//        wordList.forEach(d->wordSysDao.addWord(d));
//    }

//    @Test
//    public void TestWordYS() throws Exception {
//        List<Word> wordList = new ArrayList<>();
//        wordList = spiderService.getDataYS();
//        wordList.forEach(d->wordSysDao.addWord(d));
//    }

//    @Test
//    public void TestWordListen() throws Exception {
//        List<Listen> listenList = new ArrayList<>();
//        listenList = spiderService.spider("https://www.hjenglish.com/new/ting/");
//        for (int i = 0; i < listenList.size(); i++) {
//            /**
//             * 当路径为空或者内容为空时不加入数据库
//             */
//            if (listenList.get(i).getListenPath() == null || listenList.get(i).getContent() == null) continue;
//            else
//                listenDao.addListen(listenList.get(i));
//        }
//    }

//    @Test
//    public void TestWord() {
//        String str = "try{HJPlayer.init(\"hjptype=song&player=5&file=https://n1audio.hjfile.cn/mh/2018/10/16/6b3e2a326c81a419617eacdcd370b23b.mp3&autoStart=false&showDownload=true&width=200&height=40\");}catch(err){}";
//        Integer dd = StringUtils.getIndexStr1("https", str);
//        System.out.println(dd);
//        Integer gg = StringUtils.getIndexStr1(".mp3", str);
//        String kk = str.substring(dd, gg+4);
//        System.out.println(kk);
//        System.out.println(dd+ "  "+gg+4);
//    }
}
