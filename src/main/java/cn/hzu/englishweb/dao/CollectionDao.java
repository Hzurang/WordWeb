package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.WordCollection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionDao {
    int addCollection(WordCollection collection);

    int deleteCollection(Integer wordId, Integer userId);

    WordCollection queryCollectionByWordIdAndUserId(Integer wordId, Integer userId);

    List<WordCollection> queryCollectionByUserId(Integer userId);
}