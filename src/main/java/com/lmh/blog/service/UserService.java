package com.lmh.blog.service;

import com.lmh.blog.po.User;

/**
 * Created by lvmen on 2019/11/18
 */
public interface UserService {


    User checkUser(String username, String password);
}
