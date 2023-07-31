package cn.hzu.englishweb.service.impl;


import cn.hzu.englishweb.dao.ListenDao;
import cn.hzu.englishweb.model.Listen;
import cn.hzu.englishweb.service.ListenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListenServiceImpl implements ListenService {

    @Autowired
    private ListenDao listenDao;

    @Override
    public Listen queryListenById(Integer listenId) {
        return listenDao.queryListenById(listenId);
    }

    @Override
    public int addListen(Listen listen) {
        return listenDao.addListen(listen);
    }

    @Override
    public int deleteListen(Integer listenId) {
        return listenDao.deleteListen(listenId);
    }

    @Override
    public int updateListen(Listen listen) {
        return listenDao.updateListen(listen);
    }

    @Override
    public List<Listen> queryAllListen() {
        return listenDao.queryAllListen();
    }

    @Override
    public List<Listen> queryNewListen() {
        return listenDao.queryNewListen();
    }
}
