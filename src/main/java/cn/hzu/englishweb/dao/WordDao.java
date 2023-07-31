package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.Word;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WordDao {
    int addWord(Word word);

    List<Word> queryAllWord();
}
