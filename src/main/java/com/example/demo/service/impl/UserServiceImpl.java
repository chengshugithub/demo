package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public boolean testLock(Integer id) {
        User user=userMapper.getUserById(id);
        if(user!=null&user.getAge()>0){
            user.setAge(user.getAge()-1);
            userMapper.updateUser(user);
            return  true;
        }
        return false;
    }

    @Override
    public boolean testAddLock(Integer id) throws Exception {


        Boolean result=false;
        //获取key，获取锁
        final String lockKey=new StringBuffer().append(id).append("-RedissonLock").toString();
        RLock lock=redissonClient.getLock(lockKey);

        try {
            //TODO:第一个参数30s=表示尝试获取分布式锁，并且最大的等待获取锁的时间为30s
            //TODO:第二个参数10s=表示上锁之后，10s内操作完毕将自动释放锁
            Boolean cacheRes=lock.tryLock(30,10, TimeUnit.SECONDS);
            if (cacheRes){
                //TODO:核心业务逻辑的处理
                User user=userMapper.getUserById(id);
                if(user!=null&user.getAge()>0){
                    user.setAge(user.getAge()-1);
                    userMapper.updateUser(user);
                    result=true;
                }

            }
        }finally {
            //TODO:释放锁
            lock.unlock();
        }
        return result;

    }

}
