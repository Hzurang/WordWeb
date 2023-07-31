package cn.hzu.englishweb.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @className User
 * @description 用户对象实体类
 * @author Hzurang
 * @createDate 2021/11/30
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户的手机号
     */
    private String tel;
    /**
     * 用户的邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 是否认证
     */
    private Integer is_check;
    /**
     * 用户角色权限
     */
    private Integer role;
    /**
     * 创建时间
     */
    private Timestamp createTime;
}