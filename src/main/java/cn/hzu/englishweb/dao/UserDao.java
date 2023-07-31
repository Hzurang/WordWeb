package cn.hzu.englishweb.dao;


import cn.hzu.englishweb.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserDao
 *
 * @description: 用户数据库操作接口
 * @author: Hzu_rang
 * @createDate: 2021/10/25
 */
@Mapper
public interface UserDao {
    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 新增成功记录条数
     */
    int addByPhone(User user);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 新增成功记录条数
     */
    int addByMail(User user);

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    User getById(Integer id);

    /**
     * 根据手机号码获取用户
     *
     * @param user_phone 手机号码
     * @return 用户对象
     */
    User getByPhone(@Param("user_phone") String user_phone);

    /**
     * 根据邮箱获取用户
     *
     * @param user_email 邮箱
     * @return 用户对象
     */
    User getByEmail(@Param("user_email") String user_email);

    /**
     * 根据手机号更新密码
     * @param user_phone 手机号
     * @param user_pwd 密码
     * @return 更新成功记录条数
     */
    int updateByPhone(@Param("user_phone") String user_phone, @Param("user_pwd") String user_pwd);

    /**
     * 根据邮箱更新密码
     * @param user_email 邮箱
     * @param user_pwd 密码
     * @return 更新成功记录条数
     */
    int updateByEmail(@Param("user_email") String user_email, @Param("user_pwd") String user_pwd);

    /**
     * 更新用户名
     * @param user 用户对象
     * @return 新增成功记录条数
     */
    int updateUserName(User user);
}
