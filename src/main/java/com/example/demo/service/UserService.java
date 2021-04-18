package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

     User getUserById(Integer id);

    /**
     * 增加用户
     * @param user
     */
     void addUser(User user);


    void deleteUser(Integer id);

    void updateUser(User user);

    void sameToDo();

}
