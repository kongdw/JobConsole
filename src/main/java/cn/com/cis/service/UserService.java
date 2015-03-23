package cn.com.cis.service;

import cn.com.cis.domain.User;
import cn.com.cis.persistence.UserMapper;
import cn.com.cis.plugins.mybatis.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper mapper;

    @Transactional(readOnly = true)
    public User selectUserById(int id) {
        return mapper.selectUserById(id);
    }

    @Transactional(readOnly = true)
    public User selectUserByUsernameAndPassword(String username, String password) {
        return mapper.selectUserByUsernameAndPassword(username, password);
    }

    public void insertUser(User user) {
        mapper.insertUser(user);
    }

    public void deleteUser(int id) {
        mapper.deleteUser(id);
    }

    public void updateUser(User user) {
        mapper.updateUser(user);
    }


    @Transactional(readOnly = true)
    public List<User> selectAllUser(int page) {
        PageHelper.startPage(page, 10);
        return mapper.selectAllUser();
    }

}
