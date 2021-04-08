package com.example.demo.service.impl;

import com.example.demo.entity.Fraction;
import com.example.demo.mapper.FractionMapper;
import com.example.demo.service.FractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FractionServiceImpl implements FractionService {

    @Autowired
    private FractionMapper fractionMapper;

    @Override
    public void addFraction(Fraction Fraction) {
        fractionMapper.addFraction(Fraction);
    }

    @Override
    public Map<String, Object> getFractionByUserId(Integer userId) {
        return fractionMapper.getFractionByUserId(userId);
    }
}
