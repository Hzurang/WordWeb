package cn.hzu.englishweb.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.Code;
import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.model.User;
import cn.hzu.englishweb.service.SmsService;
import cn.hzu.englishweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @className RegisterController
 * @description 用户注册接口
 * @author Hzurang
 * @createDate 2021/11/30
 */
@Controller
public class RegisterController {
    @Resource
    UserService userService;

    @Resource
    SmsService smsService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 获取验证码服务
     * @author Hzurang
     * @param registerWay 用户注册方式（0：手机登录；1：邮箱登录）
     * @param userNum 用户注册使用的账户（手机号码或者邮箱号码）
     * @return 获取验证码结果
     */
    @ResponseBody
    @RequestMapping("/user/register/code")
    public String registerCode(String registerWay, String userNum, HttpSession session) {
        int i = Integer.parseInt(registerWay);

        // 保存申请发送验证码时的手机号或邮箱号，作为注册时的验证
        session.setAttribute("registerWay", i);
        session.setAttribute("userNum", userNum);

        switch (i) {
            case 0:
                smsService.sendByPhone(userNum);
                break;
            case 1:
                smsService.sendByEmail(userNum);
                break;
        }

        return "alert('验证码已发送，请注意查看')";
    }

    /**
     * 注册服务
     * @author Hzurang
     * @param registerWay 用户注册方式（0：手机登录；1：邮箱登录）
     * @param userNum  用户注册使用的账户（手机号码或者邮箱号码）
     * @param userPw  用户注册的密码
     * @param code 验证码
     * @return 登录结果
     */
    @ResponseBody
    @RequestMapping("/user/register")
    public String registerCheck(String registerWay, String userNum, String userPw, String code, HttpSession session) {
        int i = Integer.parseInt(registerWay);
        User user = new User();
        String resStr = null;

        // 点击注册时 手机号/邮箱号 与发送验证码时填入的号码不一样
        // 防止用户发送验证码后修改号码
        if (!userNum.equals(session.getAttribute("userNum").toString())) {
            switch (i) {
                case 0:
                    return "alert('注册失败，手机号码与发送验证码的手机号不匹对')";
                case 1:
                    return "alert('注册失败，邮箱号码与发送验证码的邮箱号不匹对')";
            }
        }

        // 根据用户输出的手机号或邮箱号进行注册
        switch (i) {
            case 0:
                user.setTel(userNum);
                user.setPassword(userPw);
                resStr = userService.registerByTel(user, code);
                break;
            case 1:
                user.setEmail(userNum);
                user.setPassword(userPw);
                resStr = userService.registerByMail(user, code);
                break;
        }


        // 注册完成
        // 返回登录页面
        session.invalidate();
        System.out.println("\n\n\n"+resStr);
        return resStr;
    }
}
