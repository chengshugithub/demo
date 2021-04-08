package com.example.demo.mapper;



import com.example.demo.entity.Fraction;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface FractionMapper {
    //根据用户id，获取分数

   Map<String,Object> getFractionByUserId(Integer userId);
    //增加一个分数

   void addFraction(Fraction fraction);
}
