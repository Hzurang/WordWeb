package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.WordRight;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WordRightDao {
    int addWordRight(WordRight wordRight);

    WordRight queryWordRightByWordIdAndUserId(Integer wordId, Integer userId);

    int updateWordRight(Integer wordId, Integer userId, Integer rightNum);
}
