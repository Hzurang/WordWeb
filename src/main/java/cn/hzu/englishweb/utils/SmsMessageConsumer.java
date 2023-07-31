package cn.hzu.englishweb.utils;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsMessageConsumer {

    @Autowired
    private SmsUtil smsUtil;

    private static String TEMPLATE_ID;

    @Value("${spring.project.sms.template-id}")
    public void setTemplateId(String templateId) {
        TEMPLATE_ID = templateId;
    }

    @RabbitHandler
    public void onMessage(Map<String,String> map) {
        String phone = map.get("mobile");
        String code = map.get("code");
        System.out.println("map手机号:"+map.get("mobile"));
        System.out.println("map验证码:"+map.get("code"));
        try {
            smsUtil.sendSms(phone, TEMPLATE_ID, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
