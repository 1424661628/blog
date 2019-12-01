package com.lmh.blog.service;

import com.lmh.blog.dao.UserRepository;
import com.lmh.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lvmen on 2019/11/18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String password) {


        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }
}
