package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.Sentence;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SentenceDao {
    //根据ID查询一条句子的信息
    Sentence querySentenceById(Integer sentenceId);
    //增加一条句子
    int addSentence(Sentence sentence);
    //删除一条句子
    int deleteSentence(Integer sentenceId);
    //修改一条句子
    int updateSentence(Sentence sentence);
    //查看所有句子所有信息
    List<Sentence> queryAllSentence();
    //随机取一条句子
    Sentence queryRandomSentence();
    //根据日期找一条句子
    Sentence queryTodaySentence(String sentenceDate);
}
