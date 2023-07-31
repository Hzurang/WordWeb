package cn.hzu.englishweb.dao;


import cn.hzu.englishweb.model.WordUnKnow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UnKnowDao {
    int addUnKnow(WordUnKnow wordUnKnow);

    WordUnKnow queryUnKnowByWordIdAndUserId(Integer wordId, Integer userId);
}
