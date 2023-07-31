package cn.hzu.englishweb.service;

import cn.hzu.englishweb.model.*;

import java.util.List;

public interface WordService {

    Word getWordById(Integer wordId);

    List<Word> queryAllCet4Word();

    List<Word> queryAllSysWord();

    Progress queryNewProgress(Integer tableId, Integer userId);

    int addProgress(Integer wordId, Integer userId);

    int addCollection(Integer wordId, Integer userId);

    WordCollection queryCollectionByWordIdAndUserId(Integer wordId, Integer userId);

    List<WordCollection> queryCollectionByUserId(Integer userId);

    int addUnKnow(Integer wordId, Integer userId);

    WordUnKnow queryUnKnowByWordIdAndUserId(Integer wordId, Integer userId);

    int addKnow(Integer wordId, Integer userId);

    WordKnow queryKnowByWordIdAndUserId(Integer wordId, Integer userId);

    int deleteCET4Progress(Integer tableId, Integer userId);

    WordKnow queryNewKnowById(Integer userId);

    WordKnow queryKnowByIdAndUserId(Integer id, Integer userId);

    int deleteKnow(Integer wordId, Integer userId);

    int addWordError(Integer wordId, Integer userId);

    WordError queryWordErrorByWordIdAndUserId(Integer wordId, Integer userId);

    int updateWordError(Integer wordId, Integer userId, Integer error);

    int addWordRight(Integer wordId, Integer userId);

    WordRight queryWordRightByWordIdAndUserId(Integer wordId, Integer userId);

    int updateWordRight(Integer wordId, Integer userId, Integer rightNum);

    int deleteCollection(Integer wordId, Integer userId);

    List<WordError> queryMaxListErrorByUserId(Integer userId);
}
