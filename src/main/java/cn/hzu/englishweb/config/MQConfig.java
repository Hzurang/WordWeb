package cn.hzu.englishweb.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String SMS_QUEUE = "sms";

    public static final String Email_QUEUE = "email";

    @Bean
    public Queue Sms_Queue(){
        return new Queue(SMS_QUEUE,true);
    }

    @Bean
    public Queue Email_Queue(){
        return new Queue(Email_QUEUE,true);
    }
}

