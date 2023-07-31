package cn.hzu.englishweb.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.*;
import cn.hzu.englishweb.model.vo.WordInfo;
import cn.hzu.englishweb.service.UserService;
import cn.hzu.englishweb.service.impl.NoticeServiceImpl;
import cn.hzu.englishweb.service.impl.SentenceServiceImpl;
import cn.hzu.englishweb.service.impl.UserServiceImpl;
import cn.hzu.englishweb.service.impl.WordServiceImpl;
import cn.hzu.englishweb.utils.RedisTemplateUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private NoticeServiceImpl noticeService;

    @Autowired
    private SentenceServiceImpl sentenceService;

    @Autowired
    private WordServiceImpl wordService;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    /**
     * 页面渲染
     * @author Hzurang
     * @return 返回页面
     */
    @RequestMapping("/")
    public String login(Model model, HttpSession session) {
        if (StpUtil.isLogin() == true) {
            return main(model, session);
        }
        return "login";
    }

    @RequestMapping("/main")
    public String main(Model model, HttpSession session) {
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        Integer userId = StpUtil.getSession().getInt("user_id");
        //查询最新公告
        Notice notice = noticeService.queryNewNoticeById();
        model.addAttribute("notice", notice);
        //随机抽取一个单词
        Random random = new Random();
        int randNumber = random.nextInt(23252 - 1 + 1) + 1;
        Word word = wordService.getWordById(randNumber);
        model.addAttribute("word", word);
        List<WordError> wordErrors = wordService.queryMaxListErrorByUserId(userId);
        List<Word> words = new ArrayList<>();
        for (int i = 0; i < wordErrors.size(); i++) {
            Word word1 = (Word) redisTemplateUtil.get(wordErrors.get(i).getWordId().toString());
            words.add(word1);
        }
        model.addAttribute("list", words);
        //查询每日一句
        Result<Sentence> result1 = sentenceService.todaySentence();
        model.addAttribute("sentence", result1.getData());
        System.out.println(model.getAttribute("sentence"));
        return "main";
    }

    @RequestMapping("/toWatchWord/{wordId}")
    public String watchWord(@PathVariable("wordId") Integer wordId, Model model) {
        Word word = (Word) redisTemplateUtil.get(wordId.toString());
        model.addAttribute("word", word);
        return "watch-word-main";
    }

    /**
     * ajax异步刷新单词随机背页面
     * @param num
     * @return
     */
    @ResponseBody
    @RequestMapping( "/main/data")
    public String mainData(String num) {
        System.out.println(num);
        JSONObject result = new JSONObject();
        Word word = wordService.getWordById(Integer.parseInt(num));
        result.put("eng", word.getWordEng());
        result.put("chi", word.getWordChi());
        result.put("photo", word.getWordPhotoPath());
        System.out.println(result);
        return result.toJSONString();
    }
}
