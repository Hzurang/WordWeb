package cn.hzu.englishweb.service.impl;

import cn.hzu.englishweb.dao.BookDao;
import cn.hzu.englishweb.model.Book;
import cn.hzu.englishweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public Book queryBookById(Integer bookId) {
        return bookDao.queryBookById(bookId);
    }

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public int deleteBook(Integer bookId) {
        return bookDao.deleteBook(bookId);
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public List<Book> queryAllBook() {
        return bookDao.queryAllBook();
    }

    @Override
    public List<Book> queryNewBook() {
        return bookDao.queryNewBook();
    }
}
