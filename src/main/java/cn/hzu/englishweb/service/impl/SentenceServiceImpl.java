package cn.hzu.englishweb.service.impl;

import cn.hzu.englishweb.dao.SentenceDao;
import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.model.Sentence;
import cn.hzu.englishweb.service.SentenceService;
import cn.hzu.englishweb.utils.SentenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SentenceServiceImpl implements SentenceService {

    @Autowired
    SentenceDao sentenceDao;

    @Override
    public Sentence querySentenceById(Integer sentenceId) {
        return sentenceDao.querySentenceById(sentenceId);
    }

    @Override
    public int addSentence(Sentence sentence) {
        return sentenceDao.addSentence(sentence);
    }

    @Override
    public int deleteSentence(Integer sentenceId) {
        return sentenceDao.deleteSentence(sentenceId);
    }

    @Override
    public int updateSentence(Sentence sentence) {
        return sentenceDao.updateSentence(sentence);
    }

    @Override
    public List<Sentence> queryAllSentence() {
        return sentenceDao.queryAllSentence();
    }

    @Override
    public Sentence queryRandomSentence() {
        return sentenceDao.queryRandomSentence();
    }

    @Override
    public Result<Sentence> todaySentence() {
        Result<Sentence> result = new Result<Sentence>();
        Sentence sentence = new Sentence();
        Sentence getsentence = null;
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String dd = ft.format(date);
        getsentence = sentenceDao.queryTodaySentence(dd);
        if (getsentence != null) {
            result.setResultSuccess("每日一句查询成功", getsentence);
            return result;
        }
        String httpUrl = "http://open.iciba.com/dsapi/";
        String jsonResult = SentenceUtil.request(httpUrl);
        System.out.println(jsonResult);
        JSONObject jsonObject = JSON.parseObject(jsonResult);
        String content = jsonObject.getString("content");
        String note = jsonObject.getString("note");
        String tts = jsonObject.getString("tts");
        System.out.println(tts);
        String today = jsonObject.getString("dateline");
        sentence.setSentenceName(content);
        sentence.setSentencePath(tts);
        sentence.setSentenceNote(note);
        sentence.setSentenceDate(today);
        sentenceDao.addSentence(sentence);
        result.setResultSuccess("每日一句增加成功", sentence);
        return result;
    }
}
