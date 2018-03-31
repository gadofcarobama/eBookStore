package dao;

import bean.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.MyJDBCUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

     public class BookImpl implements BookDao {
    public List<Book> findAll(){
        try {
            DataSource ds= MyJDBCUtils.getDataSource();
            QueryRunner queryRunner=new QueryRunner(ds);
           List<Book> bookList= queryRunner.query
                   ("select * from book",new BeanListHandler<Book>(Book.class));
              return bookList;
        } catch (SQLException e) {
            e.printStackTrace();

        }
    return null;
    }

         @Override
         public List<Book> findBookByCid(String cid) {
         DataSource ds=MyJDBCUtils.getDataSource();
         QueryRunner queryRunner=new QueryRunner(ds);
             try {
                List<Book> list = queryRunner.query("select * from book where cid=?",new BeanListHandler<Book>(Book.class),cid);
                return list;
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             return null;
         }

         @Override
         public Book findBook(String bid) {
         DataSource ds=MyJDBCUtils.getDataSource();
         QueryRunner queryRunner=new QueryRunner(ds);
             try {
                Book book= queryRunner.query("select * from book where bid=?",new BeanHandler<Book>(Book.class),bid);

                return book;
             } catch (SQLException e) {
                 e.printStackTrace();
             }
             return null;
         }

         @Override
         public void mod(Book book) {
             //根据bid进行修改
             DataSource ds=MyJDBCUtils.getDataSource();
             QueryRunner queryRunner=new QueryRunner(ds);

             Object[] os={book.getBname(),book.getPrice(),book.getAuthor(),book.getCid(),book.getBid()};
             try {
                 queryRunner.update("UPDATE book SET bname=?,price=?,author=?,cid=? WHERE bid=?", os);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }

         @Override
         public void addBook(Book book) {
             DataSource ds=MyJDBCUtils.getDataSource();
             QueryRunner queryRunner=new QueryRunner(ds);
             Object[] os={book.getBid(),book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCid()};
             try {
                 queryRunner.update("insert into book values(?,?,?,?,?,?)",os);
             } catch (SQLException e) {
             e.printStackTrace();
             }
         }

         @Override
         public void del(String bid) {
             DataSource ds=MyJDBCUtils.getDataSource();
             QueryRunner queryRunner=new QueryRunner(ds);
             try {
                 queryRunner.update("delete from book where bid=?",bid);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
     }
