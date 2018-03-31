package dao;

import bean.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();

    List<Book> findBookByCid(String cid);

    Book findBook(String bid);

    void mod(Book book);

    void addBook(Book book);

    void del(String bid);
}
