package cn.hzu.englishweb.service.impl;

import cn.hzu.englishweb.dao.NoticeDao;
import cn.hzu.englishweb.model.Notice;
import cn.hzu.englishweb.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Notice queryNoticeById(Integer noticeId) { return noticeDao.queryNoticeById(noticeId); }

    @Override
    public int addNotice(Notice notice) {
        return noticeDao.addNotice(notice);
    }

    @Override
    public int deleteNotice(Integer noticeId) {
        return noticeDao.deleteNotice(noticeId);
    }

    @Override
    public int updateNotice(Notice notice) {
        return noticeDao.updateNotice(notice);
    }

    @Override
    public List<Notice> queryAllNotice() {
        return noticeDao.queryAllNotice();
    }

    @Override
    public Notice queryNewNoticeById() {
        return noticeDao.queryNewNoticeById();
    }
}
