package cn.hzu.englishweb.utils;

import cn.hzu.englishweb.model.Code;
import cn.hzu.englishweb.model.Result;
import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SmsUtil {
    private static String APP_ID;
    private static String APP_KEY;

    @Value("${spring.project.sms.apiUrl}")
    private String apiUrl;

    @Value("${spring.project.sms.timeOut}")
    private Integer timeOut;

    @Value("${spring.project.sms.appId}")
    public void setAppId(String appId) {
        APP_ID = appId;
    }

    @Value("${spring.project.sms.appSecret}")
    public void setAppKey(String appKey) {
        APP_KEY = appKey;
    }

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSms(String tel, String templateId, String RanCode) {
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, APP_ID, APP_KEY);

        Map<String, Object> map = new HashMap<String, Object>();

        JSONObject json = null;
        map.put("number", tel);
        map.put("templateId", templateId);

        // 添加模板参数
        String[] templateParams = new String[2];
        templateParams[0] = RanCode;
        templateParams[1] = "1分钟";
        map.put("templateParams", templateParams);

        try {
            String result_sms = client.send(map);
            json = JSONObject.parseObject(result_sms);
            System.out.println(json);
            if (json.getIntValue("code") != 0) {
                // TODO 处理服务端错误码
                log.error("验证码发送失败，手机号：{}，错误信息：{}", tel, "发送短信失败");
            }
        } catch (Exception e) {
            String str;
            if (e == null) {
                str = "";
            }
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            str = stringWriter.toString();
            log.error("验证码发送出现异常：{}", str);
        }
    }

    public void sendEmail(String email, String RanCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("【鸭鸭记单词】验证码邮件");//主题

            mailMessage.setText("【鸭鸭记单词】你的验证码为：" + RanCode + "，有效时间为1分钟(若不是本人操作，可忽略该条邮件)");//内容
            mailMessage.setTo(email);//发给谁
            mailMessage.setFrom(from);//你自己的邮箱
            mailMessage.setSubject("鸭鸭记单词");
            mailSender.send(mailMessage);//发送
            log.info("文本邮件发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
