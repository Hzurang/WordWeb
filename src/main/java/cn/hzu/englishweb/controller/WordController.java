package cn.hzu.englishweb.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.*;
import cn.hzu.englishweb.service.impl.WordServiceImpl;
import cn.hzu.englishweb.utils.RedisTemplateUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;


@Controller
public class WordController {
    @Autowired
    private WordServiceImpl wordService;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    // 每日计划
    private int numberCET = 0;

    private int today = 0;

    // 词数剩余
    private int total = 0;

    // 不认识
    private int forget = 0;

    // 剩余
    private int low = 0;

    private Integer wId = 0;

    private int right = 0;

    private int error = 0;


    @RequestMapping("/testByChinese")
    public String testByChinese(Model model) {
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        Integer userId = StpUtil.getSession().getInt("user_id");
        WordKnow wordKnow = wordService.queryNewKnowById(userId);
        Random random = new Random();
        // 选取正确单词
        int rn = wordKnow.getId();
        Integer randNumber = (int) (Math.random() * rn + 1);
        WordKnow wordKnow1 = wordService.queryKnowByIdAndUserId(randNumber, userId);
        Integer wordID = wordKnow1.getWordId();
        Word word = (Word) redisTemplateUtil.get(wordID.toString());

        Word[] words = new Word[3];
        Integer[] rNum = new Integer[3];
        for(int i = 0; i < 3; i++) {
            Integer nn = (int) (Math.random() * 23252 + 1);
            while (nn == randNumber && nn >= 23252) {
                nn = (int) (Math.random() * 23252 + 1);
                if (nn != randNumber) break;
            }
            rNum[i] = nn;
        }
        for(int j = 0; j < 3; j++) {
            System.out.printf("%d ",rNum[j]);
        }
        System.out.println();
        Word word1 = (Word) redisTemplateUtil.get(rNum[0].toString());
        Word word2 = (Word) redisTemplateUtil.get(rNum[1].toString());
        Word word3 = (Word) redisTemplateUtil.get(rNum[2].toString());
        System.out.println(word1);
        System.out.println(word2);
        System.out.println(word3+"\n");
        int[] numbers=new int[4];
        for (int i = 0; i < 4; ) {
            int rm = (int)(Math.random() * 4 + 1);//1~4
            int j = 0;
            for (; j < numbers.length; j++) {
                if (rm == numbers[j]) break;
            }
            if(j == numbers.length ) numbers[i++] = rm;
        }
        model.addAttribute("word", word);
        model.addAttribute("model"+numbers[0], word.getWordChi());
        model.addAttribute("model"+numbers[1], word1.getWordChi());
        model.addAttribute("model"+numbers[2], word2.getWordChi());
        model.addAttribute("model"+numbers[3], word3.getWordChi());
        model.addAttribute("right", "正确：" + right + "个");
        model.addAttribute("error", "答错：" + error + "个");
        return "test-word-english";
    }

    @RequestMapping("/testByEnglish")
    public String testByEnglish(Model model) {
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        Integer userId = StpUtil.getSession().getInt("user_id");
        WordKnow wordKnow = wordService.queryNewKnowById(userId);
        Random random = new Random();
        int rn = wordKnow.getId();
        int randNumber = random.nextInt(rn - 1 + 1) + 1;
        WordKnow wordKnow1 = wordService.queryKnowByIdAndUserId(randNumber, userId);
        Integer wordID = wordKnow1.getWordId();
        Word word = (Word) redisTemplateUtil.get(wordID.toString());
        System.out.println(word);
        model.addAttribute("word", word);
        model.addAttribute("right", "正确：" + right + "个");
        model.addAttribute("error", "答错：" + error + "个");
        return "test-word-chinese";
    }

    @RequestMapping("/selectWord")
    public String toViewWord(Model model) {
        if (!StpUtil.isLogin()) {
            return "login";
        }
        numberCET = 0;
        today = 0;
        total = 0;
        forget = 0;
        right = 0;
        error = 0;
        return "select-word";
    }

    @RequestMapping("/studyWord/{wordId}")
    public String toStudyWord(@PathVariable("wordId")Integer wordId, Model model) {
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        Integer userId = StpUtil.getSession().getInt("user_id");
        if (numberCET == 0) {
            numberCET = 0;
            Integer ww = wordId - 1;
            wordService.addProgress(ww, userId);
            model.addAttribute("over", "0");
        }
        numberCET--;
        total = 5833 - wId;
//        Integer bid = wId + 1;
        Word word = (Word) redisTemplateUtil.get(wordId.toString());
        model.addAttribute("word", word);
        WordCollection wordCollection = wordService.queryCollectionByWordIdAndUserId(wordId, userId);
        if (wordCollection == null) {
            model.addAttribute("src", "/svg/gift_black.svg");
        } else {
            model.addAttribute("src", "/svg/gift_red.svg");
        }
        model.addAttribute("forget", "不认识：" + forget + "个");
        model.addAttribute("today", "每日计划：" + today + "个");
        model.addAttribute("number", "今日剩余：" + numberCET + "个");
        model.addAttribute("total", "词书剩余：" + total + "个");
        return "study-word";
    }

    @ResponseBody
    @RequestMapping("/KnowWord/{wordId}")
    public String KnowWord(@PathVariable("wordId")Integer wordId) {
        JSONObject result = new JSONObject();
        Integer userId = StpUtil.getSession().getInt("user_id");
        WordKnow wordKnow = wordService.queryKnowByWordIdAndUserId(wordId, userId);
        if (wordKnow == null) {
            wordService.addKnow(wordId, userId);
        }
        Integer n = wordId + 1;
        result.put("num", n);
        result.put("msg", "success");
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/collectionWord/{wordId}")
    public String toCollectWord(@PathVariable("wordId")Integer wordId) {
        JSONObject result = new JSONObject();
        Integer userId = StpUtil.getSession().getInt("user_id");
        WordCollection wordCollection = wordService.queryCollectionByWordIdAndUserId(wordId, userId);
        if (wordCollection == null) {
            wordService.addCollection(wordId, userId);
            result.put("src", "/svg/gift_red.svg");
            result.put("msg", "200");
        } else {
            result.put("msg", "该单词已收藏");
        }
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/unKnowWord/{wordId}")
    public String unKnowWord(@PathVariable("wordId")Integer wordId) {
        JSONObject result = new JSONObject();
        forget++;
        Integer userId = StpUtil.getSession().getInt("user_id");
        Integer ww = wordId + 1;
        WordUnKnow wordUnKnow = wordService.queryUnKnowByWordIdAndUserId(wordId, userId);
        if (wordUnKnow == null) {
            wordService.addUnKnow(wordId, userId);
        }
        result.put("wordId", ww);
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/collectionWordByTest/{wordId}")
    public String toCollectWordByTest(@PathVariable("wordId")Integer wordId) {
        JSONObject result = new JSONObject();
        Integer userId = StpUtil.getSession().getInt("user_id");
        WordCollection wordCollection = wordService.queryCollectionByWordIdAndUserId(wordId, userId);
        if (wordCollection == null) {
            wordService.addCollection(wordId, userId);
            result.put("msg", "success");
        } else {
            result.put("msg", "该单词已收藏");
        }
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/WordTestByChi/{wordId}")
    public String WordTestByChi(@PathVariable("wordId")Integer wordId, String inWord) {
        JSONObject result = new JSONObject();
        Integer userId = StpUtil.getSession().getInt("user_id");
        Word word = (Word) redisTemplateUtil.get(wordId.toString());
        String cpWord = word.getWordEng();
        if (inWord.equals(cpWord)) {
            WordRight wordRight = wordService.queryWordRightByWordIdAndUserId(wordId, userId);
            if (wordRight == null) {
                wordService.addWordRight(wordId, userId);
            } else {
                Integer ri = wordRight.getRightNum();
                ri += 1;
                wordService.updateWordRight(wordId, userId, ri);
            }
            right++;
            result.put("msg", "right");
        } else {
            WordError wordError = wordService.queryWordErrorByWordIdAndUserId(wordId, userId);
            if (wordError == null) {
                wordService.addWordError(wordId, userId);
            } else {
                Integer en = wordError.getError();
                en += 1;
                wordService.updateWordError(wordId, userId, en);
            }
            error++;
            result.put("msg", "error");
        }
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/WordTestByEng/{wordId}")
    public String WordTestByEng(@PathVariable("wordId")Integer wordId, String inWordChi) {
        JSONObject result = new JSONObject();
        Integer userId = StpUtil.getSession().getInt("user_id");
        Word word = (Word) redisTemplateUtil.get(wordId.toString());
        String cpWord = word.getWordChi();
        if (inWordChi.equals(cpWord)) {
            WordRight wordRight = wordService.queryWordRightByWordIdAndUserId(wordId, userId);
            if (wordRight == null) {
                wordService.addWordRight(wordId, userId);
            } else {
                Integer ri = wordRight.getRightNum();
                ri += 1;
                wordService.updateWordRight(wordId, userId, ri);
            }
            right++;
            result.put("msg", "right");
        } else {
            WordError wordError = wordService.queryWordErrorByWordIdAndUserId(wordId, userId);
            if (wordError == null) {
                wordService.addWordError(wordId, userId);
            } else {
                Integer en = wordError.getError();
                en += 1;
                wordService.updateWordError(wordId, userId, en);
            }
            error++;
            result.put("msg", "error");
        }
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/KnowToUnKnow/{wordId}")
    public String KnowToUnKnow(@PathVariable("wordId")Integer wordId) {
        JSONObject result = new JSONObject();
        Integer userId = StpUtil.getSession().getInt("user_id");
        wordService.deleteKnow(wordId, userId);
        wordService.addUnKnow(wordId, userId);
        error++;
        result.put("msg", "success");
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/studyWord/CET4")
    public String ChoiceWord(Integer num) {
        Integer userId = StpUtil.getSession().getInt("user_id");
        Progress progress = wordService.queryNewProgress(0, userId);
        if (progress == null) {
            wId = 1;
            numberCET = num;
            today = num;
            return wId.toString();
        }
        if (progress.getWordId() == 5833) {
            return "请重置学习进度";
        }
        if (progress.getWordId() + num > 5833) {
            numberCET = 5833 - progress.getWordId();
            today = numberCET;
            wId = progress.getWordId() + 1;
            return wId.toString();
        }
//        if (progress == null) {
//            Word word = (Word) redisTemplateUtil.get("1");
//            model.addAttribute("word", word);
//            number = num;
//            return "成功";
//        }
        numberCET = num;
        today = num;
        wId = progress.getWordId() + 1;
        return wId.toString();
    }

    @ResponseBody
    @RequestMapping("/resetWord/CET4")
    public String resetWordCET4() {
        Integer userId = StpUtil.getSession().getInt("user_id");
        wordService.deleteCET4Progress(0, userId);
        return "重置成功";
    }
}
