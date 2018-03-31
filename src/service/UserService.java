package service;

import bean.User;
import dao.UserDao;
import dao.UserDaoImpl;

import java.util.List;

public class UserService {
    public User findByUserName(String username){
        UserDao userDao=new UserDaoImpl();
        User user=userDao.findByName(username);
        return user;
    }

    public boolean add(User user1) {
        //调用方法进行添加
        UserDao userDao=new UserDaoImpl();
        boolean flag=userDao.add(user1);
        return flag;
    }

    public User find(String username, String password) {
        UserDao userDao=new UserDaoImpl();
        User user=userDao.find(username,password);
        return user;
    }

    public List<User> findAllUser() {
        UserDao userDao=new UserDaoImpl();
        List<User> list=userDao.findAllUser();
        return list;
    }

    public void updateUser(String uid) {
        //根据id查询用户，用户状态是激活则改为禁用，用户状态是禁用则改为激活
        UserDao userDao=new UserDaoImpl();
        User user=userDao.findUserById(uid);
        String status =user.getStatus();
        int status1=Integer.parseInt(status);
        if (status1==1){
            userDao.update1(uid);
        }else {
            userDao.update0(uid);
        }
    }
}
