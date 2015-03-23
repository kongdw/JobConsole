package cn.com.cis.persistence;

import cn.com.cis.domain.User;

import java.util.List;

public interface UserMapper {

  User selectUserById(int id);

  User selectUserByUsernameAndPassword(String username, String password);

  void insertUser(User user);

  void deleteUser(int id);

  void updateUser(User user);

  List<User> selectAllUser();

}
