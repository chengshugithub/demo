package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

        /**
         * 查询用户
         * @param id
         * @return
         */
        User getUserById(Integer id);

        /**
         * 增加用户
         * @param user
         * @return
         */
        void addUser(User user);


        void deleteUser(Integer id);


        void updateUser(User user);

}
