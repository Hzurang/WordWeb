package cn.hzu.englishweb.service.impl;

import cn.hzu.englishweb.model.Code;
import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.service.SmsService;
import cn.hzu.englishweb.utils.RandomUtils;
import cn.hzu.englishweb.utils.RedisTemplateUtil;
import cn.hzu.englishweb.utils.SmsUtil;
import cn.hzu.englishweb.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Hzurang
 * @className SmsServiceImpl
 * @description 短信业务逻辑
 * @createDate 2021/11/30
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送短信验证码
     *
     * @param tel 手机号码
     * @return
     */
    @Override
    public void sendByPhone(String tel) {
        String num = RandomUtils.number(6);

        //向缓存中放一份
        redisTemplateUtil.set("code"+ tel, num, 60);

        // 将验证码存入RabbitMQ
        Map<String,String> RbMap = new HashMap<>();
        RbMap.put("mobile", tel);
        RbMap.put("code", num);

        rabbitTemplate.convertAndSend("sms", RbMap);
        System.out.println("sendByPhone验证码:" + num);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱号码
     * @return
     */
    @Override
    public void sendByEmail(String email) {
        String num = RandomUtils.number(6);

        //向缓存中放一份
        redisTemplateUtil.set("code" + email, num, 60);

        // 将验证码存入RabbitMQ
        Map<String,String> RbMap = new HashMap<>();
        RbMap.put("email", email);
        RbMap.put("code", num);

        rabbitTemplate.convertAndSend("email", RbMap);
        System.out.println("sendByEmail验证码:" + num);
    }
}