package cn.hzu.englishweb.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hzu.englishweb.model.Notice;
import cn.hzu.englishweb.service.impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    private NoticeServiceImpl noticeService;

    //去历史公告页面
    @RequestMapping("/toViewNotice")
    public String toViewNotice(Model model){
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        List<Notice> notice = noticeService.queryAllNotice();
        model.addAttribute("notice",notice);
        return "select-notice";
    }

    //具体查看公告
    @RequestMapping("/viewNotice/{noticeId}")
    public String ViewNotice(@PathVariable("noticeId")Integer noticeId, Model model){
        if (!StpUtil.isLogin()) {
            return "redirect:/";
        }
        Notice notice = noticeService.queryNoticeById(noticeId);
        model.addAttribute("notice",notice);
        return "watch-notice";
    }
}
