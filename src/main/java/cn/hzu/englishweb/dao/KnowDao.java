package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.WordKnow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface KnowDao {
    int addKnow(WordKnow wordKnow);

    WordKnow queryKnowByWordIdAndUserId(Integer wordId, Integer userId);

    WordKnow queryNewKnowById(Integer userId);

    WordKnow queryKnowByIdAndUserId(Integer id, Integer userId);

    int deleteKnow(Integer wordId, Integer userId);
}
