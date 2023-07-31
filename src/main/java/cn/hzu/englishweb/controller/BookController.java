package cn.hzu.englishweb.controller;

import cn.hzu.englishweb.model.Book;
import cn.hzu.englishweb.service.impl.BookServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @className BookController
 * @description 书籍接口
 * @author Hzurang
 * @createDate 2021/12/2
 */
@Controller
public class BookController {
    @Autowired
    BookServiceImpl bookService;

    //查看所有书籍
    @RequestMapping("/viewbook")
    public String ViewBook(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 10);

        List<Book> book = bookService.queryAllBook();

        PageInfo<Book> pageInfo = new PageInfo<Book>(book);

        model.addAttribute("pageInfo", pageInfo);
        return "select-book";
    }

    //具体进入某一书籍
    @RequestMapping("/watchBook/{bookId}")
    public String watchBook(@PathVariable("bookId")Integer bookId, Model model){
        Book book = bookService.queryBookById(bookId);
        List<Book> bookList = bookService.queryNewBook();
        model.addAttribute("book",book);

        model.addAttribute("bookList",bookList);
        return "watch-book";
    }
}
