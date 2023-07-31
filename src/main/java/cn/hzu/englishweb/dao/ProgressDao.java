package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.Progress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProgressDao {
    Progress queryNewProgress(Integer tableId, Integer userId);

    int addProgress(Progress progress);

    int deleteCET4Progress(Integer tableId, Integer userId);
}
