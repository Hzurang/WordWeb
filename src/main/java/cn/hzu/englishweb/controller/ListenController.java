package cn.hzu.englishweb.controller;

import cn.hzu.englishweb.model.Listen;
import cn.hzu.englishweb.service.impl.ListenServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListenController {
    @Autowired
    private ListenServiceImpl listenService;

    //查看所有书籍
    @RequestMapping("/viewListen")
    public String ViewListen(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Listen> listen = listenService.queryAllListen();

        PageInfo<Listen> pageInfo = new PageInfo<>(listen);

        List<Listen> listenList = listenService.queryNewListen();

        model.addAttribute("pageInfo", pageInfo);

        model.addAttribute("listenList", listenList);
        return "select-listen";
    }

    //具体进入某一听力
    @RequestMapping("/watchListen/{listenId}")
    public String watchListen(@PathVariable("listenId") Integer listenId, Model model) {
        Listen listen = listenService.queryListenById(listenId);
        model.addAttribute("listen", listen);
        return "watch-listen";
    }
}
