package com.example.demo.service.abstracts;

import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUserService implements UserService {


    @Autowired
    protected UserMapper userMapper;

    /*
      相同的逻辑抽出来
     */
    @Autowired
    public  void  sameToDo(){
        //逻辑1(相同)
        //tod
        //逻辑2（相同）
        //tod
        //逻辑3(不同)
        DifferentToDo();
        //逻辑4(不同)
        DifferentToDo2();
    }
   /*
      不相同的逻辑抽出来
     */
   public abstract void  DifferentToDo();

   public abstract  void DifferentToDo2();

}
