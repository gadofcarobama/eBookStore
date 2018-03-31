package dao;

import bean.Book;
import bean.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.MyJDBCUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public List<Category> findCate() {
        DataSource ds= MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<Category> list=queryRunner.query("select * from category",new BeanListHandler<Category>(Category.class));
            return  list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category findCateByCid(String cid) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
          Category category =  queryRunner.query("select * from category where cid=?",new BeanHandler<Category>(Category.class),cid);
          return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCategory(Category category) {
        DataSource dataSource=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(dataSource);
        try {
            queryRunner.update("update category set cname=? where cid=?",category.getCname(),category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookCid(Book book) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            queryRunner.update("update book set cid=? where bid=?",null,book.getBid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCate(String cid) {
        DataSource dataSource=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(dataSource);
        try {
            queryRunner.update("delete from category where cid=?",cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategory(Category category) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            queryRunner.update("insert into category values(?,?)",category.getCid(),category.getCname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
