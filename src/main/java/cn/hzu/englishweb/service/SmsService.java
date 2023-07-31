package cn.hzu.englishweb.service;

/**
 * @interfaceName SmsService
 * @description 短信服务
 * @author Hzurang
 * @createDate 2021/11/30
 */
public interface SmsService {
    /**
     * 发送短信
     * @param tel 手机号码
     * @return Result<String>
     */
    void sendByPhone(String tel);

    /**
     *
     * @param email 邮箱号码
     * @return Result<String>
     */
    void sendByEmail(String email);
}