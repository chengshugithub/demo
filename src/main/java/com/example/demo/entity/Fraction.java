package com.example.demo.entity;

public class Fraction {
    private Integer id;

    private Integer mark;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Fraction(Integer mark, Integer userId) {
        this.mark = mark;
        this.userId = userId;
    }
}
