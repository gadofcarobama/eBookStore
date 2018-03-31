package dao;

import bean.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.MyJDBCUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByName(String username) {
        DataSource ds = MyJDBCUtils.getDataSource();
        //根据用户名查询数据库
        QueryRunner queryRunner = new QueryRunner(ds);
        try {
            User user = queryRunner.query("select * from tb_user where username=?", new BeanHandler<User>(User.class), username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean add(User user1) {
        try {
            DataSource ds = MyJDBCUtils.getDataSource();
            QueryRunner queryRunner = new QueryRunner(ds);
            Object[] os = {user1.getUid(), user1.getUsername(), user1.getPassword(), user1.getAddress(), user1.getStatus()};
            queryRunner.update("insert into tb_user values(?,?,?,?,?)", os);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User find(String username, String password) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            User user = queryRunner.query("select * from tb_user where username=? and password=? and status=?",
                    new BeanHandler<User>(User.class), username,password,"1");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {
        //查询所有用户
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<User> users=queryRunner.query("select * from tb_user",new BeanListHandler<User>(User.class));
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserById(String uid) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
          User user=queryRunner.query("select * from tb_user where uid=?",new BeanHandler<User>(User.class),uid);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update1(String uid) {
        DataSource ds = MyJDBCUtils.getDataSource();
        QueryRunner queryRunner = new QueryRunner(ds);
        try {
            queryRunner.update("update tb_user set status=? where uid=?", 0,uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update0(String uid) {
        DataSource ds = MyJDBCUtils.getDataSource();
        QueryRunner queryRunner = new QueryRunner(ds);
        try {
            queryRunner.update("update tb_user set status=? where uid=?", 1,uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
