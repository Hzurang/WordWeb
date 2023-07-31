package cn.hzu.englishweb.service.impl;

import cn.hzu.englishweb.dao.*;
import cn.hzu.englishweb.model.*;
import cn.hzu.englishweb.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordSysDao wordSysDao;

    @Autowired
    private ProgressDao progressDao;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private UnKnowDao unKnowDao;

    @Autowired
    private KnowDao knowDao;

    @Autowired
    private WordErrorDao wordErrorDao;

    @Autowired
    private WordRightDao wordRightDao;

    @Override
    public Word getWordById(Integer wordId) {
        return wordSysDao.getWordById(wordId);
    }

    @Override
    public List<Word> queryAllCet4Word() {
        return wordSysDao.queryAllCet4Word();
    }

    @Override
    public List<Word> queryAllSysWord() {
        return wordSysDao.queryAllSysWord();
    }

    @Override
    public Progress queryNewProgress(Integer tableId, Integer userId) {
        return progressDao.queryNewProgress(tableId, userId);
    }

    @Override
    public int addCollection(Integer wordId, Integer userId) {
        WordCollection wordCollection = new WordCollection();
        wordCollection.setWordId(wordId);
        // 后期要更改
        wordCollection.setTableId(0);
        wordCollection.setUserId(userId);
        wordCollection.setWordTime(new Date());
        return collectionDao.addCollection(wordCollection);
    }

    @Override
    public int addProgress(Integer wordId, Integer userId) {
        Progress progress = new Progress();
        progress.setWordId(wordId);
        // 后期要更改
        progress.setTableId(0);
        progress.setUserId(userId);
        progress.setWordTime(new Date());
        return progressDao.addProgress(progress);
    }

    @Override
    public WordCollection queryCollectionByWordIdAndUserId(Integer wordId, Integer userId) {
        return collectionDao.queryCollectionByWordIdAndUserId(wordId, userId);
    }

    @Override
    public List<WordCollection> queryCollectionByUserId(Integer userId) {
        return collectionDao.queryCollectionByUserId(userId);
    }

    @Override
    public int addUnKnow(Integer wordId, Integer userId) {
        WordUnKnow wordUnKnow = new WordUnKnow();
        wordUnKnow.setWordId(wordId);
        wordUnKnow.setUserId(userId);
        wordUnKnow.setWordTime(new Date());
        wordUnKnow.setTableId(0);
        return unKnowDao.addUnKnow(wordUnKnow);
    }

    @Override
    public WordUnKnow queryUnKnowByWordIdAndUserId(Integer wordId, Integer userId) {
        return unKnowDao.queryUnKnowByWordIdAndUserId(wordId, userId);
    }

    @Override
    public int addKnow(Integer wordId, Integer userId) {
        WordKnow wordKnow = new WordKnow();
        wordKnow.setWordId(wordId);
        wordKnow.setUserId(userId);
        wordKnow.setWordTime(new Date());
        wordKnow.setTableId(0);
        return knowDao.addKnow(wordKnow);
    }

    @Override
    public WordKnow queryKnowByWordIdAndUserId(Integer wordId, Integer userId) {
        return knowDao.queryKnowByWordIdAndUserId(wordId, userId);
    }

    @Override
    public int deleteCET4Progress(Integer wordId, Integer userId) {
        return progressDao.deleteCET4Progress(wordId, userId);
    }

    @Override
    public WordKnow queryNewKnowById(Integer userId) {
        return knowDao.queryNewKnowById(userId);
    }

    @Override
    public WordKnow queryKnowByIdAndUserId(Integer id, Integer userId) {
        return knowDao.queryKnowByIdAndUserId(id, userId);
    }

    @Override
    public int deleteKnow(Integer wordId, Integer userId) {
        return knowDao.deleteKnow(wordId, userId);
    }

    @Override
    public int addWordError(Integer wordId, Integer userId) {
        WordError wordError = new WordError();
        wordError.setError(1);
        wordError.setWordId(wordId);
        wordError.setWordTime(new Date());
        wordError.setUserId(userId);
        wordError.setTableId(0);
        return wordErrorDao.addWordError(wordError);
    }

    @Override
    public WordError queryWordErrorByWordIdAndUserId(Integer wordId, Integer userId) {
        return wordErrorDao.queryWordErrorByWordIdAndUserId(wordId, userId);
    }

    @Override
    public int updateWordError(Integer wordId, Integer userId, Integer error) {
        return wordErrorDao.updateWordError(wordId, userId, error);
    }

    @Override
    public int addWordRight(Integer wordId, Integer userId) {
        WordRight wordRight = new WordRight();
        wordRight.setRightNum(1);
        wordRight.setWordId(wordId);
        wordRight.setWordTime(new Date());
        wordRight.setUserId(userId);
        wordRight.setTableId(0);
        return wordRightDao.addWordRight(wordRight);
    }

    @Override
    public WordRight queryWordRightByWordIdAndUserId(Integer wordId, Integer userId) {
        return wordRightDao.queryWordRightByWordIdAndUserId(wordId, userId);
    }

    @Override
    public int updateWordRight(Integer wordId, Integer userId, Integer rightNum) {
        return wordRightDao.updateWordRight(wordId, userId, rightNum);
    }

    @Override
    public int deleteCollection(Integer wordId, Integer userId) {
        return collectionDao.deleteCollection(wordId, userId);
    }

    @Override
    public List<WordError> queryMaxListErrorByUserId(Integer userId) {
        return wordErrorDao.queryMaxListErrorByUserId(userId);
    }
}
