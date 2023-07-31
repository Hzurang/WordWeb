package cn.hzu.englishweb.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.Book;
import cn.hzu.englishweb.model.Listen;
import cn.hzu.englishweb.model.Word;
import cn.hzu.englishweb.model.WordCollection;
import cn.hzu.englishweb.model.vo.WordInfo;
import cn.hzu.englishweb.service.impl.WordServiceImpl;
import cn.hzu.englishweb.utils.RedisTemplateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CollectionController {
    @Autowired
    private WordServiceImpl wordService;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @RequestMapping("/toCollection")
    public String ViewBook(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Integer userId = StpUtil.getSession().getInt("user_id");

        PageHelper.startPage(pageNum, 10);

        List<WordCollection> wordCollections = wordService.queryCollectionByUserId(userId);

        List<WordInfo> wordInfos = new ArrayList<>();

        for (int i = 0; i < wordCollections.size(); i++) {
            Word word = (Word) redisTemplateUtil.get(wordCollections.get(i).getWordId().toString());
            WordInfo wordInfo = new WordInfo();
            wordInfo.setWordId(word.getWordId());
            wordInfo.setWordEN(word.getWordEN());
            wordInfo.setWordEng(word.getWordEng());
            wordInfo.setWordChi(word.getWordChi());
            if (word.getWordStyle() == 0) {
                wordInfo.setWordStyle("四级");
            } else if (word.getWordStyle() == 1) {
                wordInfo.setWordStyle("六级");
            } else if (word.getWordStyle() == 2) {
                wordInfo.setWordStyle("雅思");
            } else if (word.getWordStyle() == 3) {
                wordInfo.setWordStyle("托福");
            }
            wordInfos.add(wordInfo);
        }

        PageInfo<WordInfo> pageInfo = new PageInfo<>(wordInfos);

        model.addAttribute("pageInfo", pageInfo);
        return "collection";
    }

    @RequestMapping("/watchWord/{wordId}")
    public String watchWord(@PathVariable("wordId") Integer wordId, Model model) {
        Word word = (Word) redisTemplateUtil.get(wordId.toString());
        model.addAttribute("word", word);
        return "watch-word";
    }

    @ResponseBody
    @RequestMapping("/DeleteCollection/{wordId}")
    public String DeleteCollection(@PathVariable("wordId") Integer wordId, Model model) {
        System.out.println("22222222222");
        Integer userId = StpUtil.getSession().getInt("user_id");
        wordService.deleteCollection(wordId, userId);
        return "取消成功";
    }
}
