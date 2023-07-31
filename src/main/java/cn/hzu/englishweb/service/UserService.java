package cn.hzu.englishweb.service;

import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.model.User;
import io.swagger.models.auth.In;

/**
 * @interfaceName: UserService
 * @description: 用户验证处理接口
 * @author: Hzu_rang
 * @createDate: 2021/10/28
 */
public interface UserService {
    /**
     * 用户手机注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    String registerByTel(User user, String smsCode);

    /**
     * 用户邮箱注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    String registerByMail(User user, String smsCode);

    /**
     * 用户手机登录
     *
     * @param user 用户对象
     * @return boolean 是否成功
     */
    Result<User> loginByTel(User user);

    /**
     * 用户邮箱登录
     *
     * @param user 用户对象
     * @return boolean 是否成功
     */
    Result<User> loginByMail(User user);

    /**
     * 查找用户信息
     *
     * @param userId 用户id
     * @return 查找结果
     */
    Result<User> getUserById(Integer userId);

    /**
     * 根据手机号码找回密码
     * @param user 用户对象
     * @return 重置密码结果
     */
    String findBackByTel(User user, String smsCode);

    /**
     * 根据邮箱号找回密码
     * @param user 用户对象
     * @return 重置密码结果
     */
    String findBackByMail(User user, String smsCode);

    /**
     * 根据用户Id更新用户名
     * @param user 用户对象
     * @return 更新结果
     */
    int updateUserNameById(User user);
}

