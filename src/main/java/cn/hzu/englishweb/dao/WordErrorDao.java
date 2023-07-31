package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.WordError;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WordErrorDao {
    int addWordError(WordError wordError);

    WordError queryWordErrorByWordIdAndUserId(Integer wordId, Integer userId);

    int updateWordError(Integer wordId, Integer userId, Integer error);

    List<WordError> queryMaxListErrorByUserId(Integer userId);
}
