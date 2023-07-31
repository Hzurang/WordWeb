package cn.hzu.englishweb.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.Notice;
import cn.hzu.englishweb.model.Result;
import cn.hzu.englishweb.model.Sentence;
import cn.hzu.englishweb.model.User;
import cn.hzu.englishweb.service.impl.NoticeServiceImpl;
import cn.hzu.englishweb.service.impl.SentenceServiceImpl;
import cn.hzu.englishweb.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ModifyController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NoticeServiceImpl noticeService;

    @Autowired
    private SentenceServiceImpl sentenceService;

    @ResponseBody
    @RequestMapping("/user/modify")
    public String main(String userName, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setUserName(userName);
        userService.updateUserNameById(user);
        session.setAttribute("user", user);
//
//        Notice notice = noticeService.queryNewNoticeById();
//        model.addAttribute("notice", notice);
//
//        //查询每日一句
//        Result<Sentence> result1 = sentenceService.todaySentence();
//        model.addAttribute("sentence", result1.getData());

        return "更新成功";
    }

    @RequestMapping("/toUser")
    public String toUser(Model model, HttpSession session) {
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        User User = (User) session.getAttribute("user");
        model.addAttribute("user", User);
        return "modify";
    }
}
