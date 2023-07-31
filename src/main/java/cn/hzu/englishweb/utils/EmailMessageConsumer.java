package cn.hzu.englishweb.utils;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "email")
public class EmailMessageConsumer {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void onMessage(Map<String,String> map) {
        String email = map.get("email");
        String code = map.get("code");
        System.out.println("map邮箱号:"+map.get("email"));
        System.out.println("map验证码:"+map.get("code"));
        try {
            smsUtil.sendEmail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
