package cn.hzu.englishweb.controller;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.model.User;
import cn.hzu.englishweb.service.SmsService;
import cn.hzu.englishweb.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @className LoginController
 * @description 用户登录接口
 * @author Hzurang
 * @createDate 2021/11/30
 */
@Controller
public class LoginController {
    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SmsService smsService;

    /**
     * 用户登录
     * @author Hzurang
     * @param loginWay 用户登录方式（0：手机登录；1：邮箱登录）
     * @param userNum  用户登录账户（手机号码或者邮箱号码）
     * @param userPw   用户登录密码
     * @return 登录结果
     */
    @ResponseBody
    @RequestMapping("/user/login")
    public String loginCheck(String loginWay, String userNum, String userPw, HttpSession session) {
        String retStr = "";
        User user = new User();
        Result<User> result = null;

        int i = Integer.valueOf(loginWay).intValue();
        switch (i) {
            case 0:
                user.setTel(userNum);
                user.setPassword(SaSecureUtil.md5(userPw));
                result = userService.loginByTel(user);
                break;
            case 1:
                user.setEmail(userNum);
                user.setPassword(SaSecureUtil.md5(userPw));
                result = userService.loginByMail(user);
                break;
        }

        if (result.isSuccess()) {
            StpUtil.login(result.getData().getUserId());
            StpUtil.getSession().set("user_id", result.getData().getUserId());
            session.setAttribute("user", result.getData());
            return "登录成功";
        } else {
            return result.getMessage();
        }
    }

    /**
     * 用户登出
     * @author Hzurang
     * @return 结果对象
     */
    @RequestMapping("/user/logout")
    public String logout() {
        StpUtil.logout();
        return "redirect:/";
    }

    /**
     * 页面渲染
     * @author Hzurang
     * @return 找回密码页面
     */
    @RequestMapping("/findBackPassword")
    public String findBack() {
        return "findbackpd";
    }

    /**
     * 获取验证码服务
     * @param findBackWay 找回密码的方式
     * @param userNum 手机/邮箱
     * @return 获取验证码结果
     */
    @ResponseBody
    @RequestMapping("/user/findBackPassword/code")
    public String registerCode(String findBackWay, String userNum, HttpSession session) {
        int i = Integer.parseInt(findBackWay);

        // 保存申请发送验证码时的手机号或邮箱号，作为找回密码时的验证
        session.setAttribute("findBackWay", i);
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
     * 找回密码
     * @param findBackWay 找回密码的方式
     * @param userNum 邮箱/手机
     * @param pwd 密码
     * @param code 验证码
     * @return 找回密码结果
     */
    @ResponseBody
    @RequestMapping("/user/findBackPassword")
    public String findBackPwdByPhone(String findBackWay, String userNum, String pwd, String code, HttpSession session) {
        int i = Integer.parseInt(findBackWay);
        User user = new User();
        String resStr = null;

        if (!userNum.equals(session.getAttribute("userNum").toString())) {
            switch (i) {
                case 0:
                    return "alert('找回失败，手机号码与发送验证码的手机号不匹对')";
                case 1:
                    return "alert('找回失败，邮箱号码与发送验证码的邮箱号不匹对')";
            }
        }

        // 根据用户输出的手机号或邮箱号进行找回密码
        switch (i) {
            case 0:
                user.setTel(userNum);
                String str = SaSecureUtil.md5(pwd);
                user.setPassword(str);
                resStr = userService.findBackByTel(user, code);
                break;
            case 1:
                user.setEmail(userNum);
                String str1 = SaSecureUtil.md5(pwd);
                user.setPassword(str1);
                resStr = userService.findBackByMail(user, code);
                break;
        }

        session.invalidate();
        System.out.println(session);

        if (resStr.equals("用户密码重置成功")) {
            return "找回成功";
        } else {
            return resStr;
        }
    }
}

