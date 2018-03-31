package dao;

import bean.User;

import java.util.List;

public interface UserDao {

   public User findByName(String username);

   public boolean add(User user1);

   public User find(String username, String password);

    List<User> findAllUser();

    User findUserById(String uid);
//修改状态为1的变为0
    void update1(String uid);
  //修改状态为0的变为1
    void update0(String uid);
}
