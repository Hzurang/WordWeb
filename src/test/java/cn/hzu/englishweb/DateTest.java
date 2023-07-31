package cn.hzu.englishweb;

import cn.hzu.englishweb.model.Word;
import cn.hzu.englishweb.model.WordKnow;
import cn.hzu.englishweb.service.impl.WordServiceImpl;
import cn.hzu.englishweb.utils.RedisTemplateUtil;
import cn.hzu.englishweb.utils.RedisUtil;
import cn.hzu.englishweb.utils.SerializeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateTest {

    @Autowired
    private WordServiceImpl wordService;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Test
    public void wordTest() throws Exception{
        List<Word> wordList = wordService.queryAllSysWord();
        for (Word word: wordList
        ) {
            redisTemplateUtil.set(word.getWordId().toString(), word, -1);
        }
    }

    @Test
    public void getWord() {
        Random random = new Random();
        Integer randNumber = random.nextInt(5833 - 1 + 1) + 1;
        System.out.println(randNumber);
        System.out.println(redisTemplateUtil.get(randNumber.toString()));
    }

    @Test
    public void TestMax() {
        WordKnow wordKnow = wordService.queryNewKnowById(3);
        Random random = new Random();
        int rn = wordKnow.getId();
        for (int i = 0; i < 100; i++) {
            int randNumber = random.nextInt(rn - 1 + 1) + 1;
            System.out.println(randNumber);
        }
    }

    @Test
    public void getNotNumber2(){
        //1. 声明整型数组
        int[] numbers=new int[4];
        //2. 思路： 通过一个外层for循环来不断生成随机数，通过内层for循环来剔除重复的随机数
        for (int i = 0; i < 4; ) {
            int random = (int)(Math.random() * 4 + 1);//1~32
            int j = 0;//后面需要对 j 进行判断，需要提升变量作用域
            for (; j < numbers.length; j++) {
                if (random == numbers[j]) break;//遍历numbers数组，如果出现重复就跳出for循环
            }
            if(j == numbers.length ) numbers[i++] = random; //能到达这一步，说明没有重复，就可以存放随机数字到指定的位置了
        }
        System.out.println(Arrays.toString(numbers));
    }
}
