package com.example.demo.controller;

import com.example.demo.entity.Fraction;
import com.example.demo.service.FractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fraction")
public class FractionController {
    @Autowired
    private FractionService fractionService;

    @GetMapping("/getone/{id}")
    public Map<String, Object> getFractionByUserId(@PathVariable("id") Integer id){
        return fractionService.getFractionByUserId(id);
    }

    @PostMapping("/addFraction")
    public String addFraction(@RequestParam Integer mark, @RequestParam Integer userId){
        Fraction fraction = new Fraction(mark,userId);
        fractionService.addFraction(fraction);
        return "success";
    }

}
