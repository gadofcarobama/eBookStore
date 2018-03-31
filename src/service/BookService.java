package service;

import bean.Book;
import dao.BookDao;
import dao.BookImpl;

import java.util.List;

public class BookService {
    public List<Book> findAll(){
       BookDao bookDao=new BookImpl();
       List<Book> booklist=bookDao.findAll();
        return booklist;
    }

    public List<Book> findBookByCid(String cid) {
        BookDao bookDao=new BookImpl();
       List<Book> bookList = bookDao.findBookByCid(cid);
       return bookList;
    }

    public Book findBook(String bid) {
        BookDao bookDao=new BookImpl();
        Book book=bookDao.findBook(bid);

        return book;
    }

    public void mod(Book book) {
        BookDao bookDao=new BookImpl();
        bookDao.mod(book);
    }

    public void addBook(Book book) {
        BookDao bookDao=new BookImpl();
        bookDao.addBook(book);
    }

    public void delBook(String bid) {
        BookDao bookDao=new BookImpl();
        bookDao.del(bid);
    }
}
