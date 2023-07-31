package cn.hzu.englishweb.dao;

import cn.hzu.englishweb.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookDao {
    //根据ID查询一条书的信息
    Book queryBookById(Integer bookId);
    //增加书
    int addBook(Book book);
    //删除书
    int deleteBook(Integer bookId);
    //修改书
    int updateBook(Book book);
    //查看书所有信息
    List<Book> queryAllBook();
    //查看最新的三本书
    List<Book> queryNewBook();
}
