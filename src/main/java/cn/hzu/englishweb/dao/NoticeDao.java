package cn.hzu.englishweb.dao;


import cn.hzu.englishweb.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeDao {
    /**
     * 根据ID查询一条公告
     * @param noticeId 公告ID
     * @return 公告对象
     */
    Notice queryNoticeById(Integer noticeId);
    /**
     * 增加公告
     * @param notice 公告对象
     * @return 增加成功记录数
     */
    int addNotice(Notice notice);
    /**
     * 删除公告
     * @param noticeId 公告ID
     * @return 删除成功记录数
     */
    int deleteNotice(Integer noticeId);
    /**
     * 修改公告
     * @param notice 公告对象
     * @return 更新成功记录数
     */
    int updateNotice(Notice notice);
    /**
     * 查看公告所有信息
     * @return 所有的公告对象
     */
    List<Notice> queryAllNotice();
    /**
     * 查看最新公告的内容
     * @return 公告对象
     */
    Notice queryNewNoticeById();
}
