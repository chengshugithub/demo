package com.example.demo.service;

import com.example.demo.entity.Fraction;

import java.util.Map;


public interface FractionService {

     void addFraction(Fraction Fraction);

    Map<String,Object>  getFractionByUserId(Integer userId);

}
