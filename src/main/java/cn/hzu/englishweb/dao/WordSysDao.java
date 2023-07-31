package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.Word;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WordSysDao {
    int addWord(Word word);

    List<Word> queryAllSysWord();

    List<Word> queryAllCet4Word();

    List<Word> queryAllCet6Word();

    List<Word> queryAllTfWord();

    List<Word> queryAllYsWord();

    Word getWordById(Integer wordId);
}
