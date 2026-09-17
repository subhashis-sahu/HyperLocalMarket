package com.example.hyperlocalmarket.model;

public class Category {
    private Long id;
    private String name;

    public void setId(Long id){
        this.id=id;

    }
    public void setName(String name){
        this.name=name;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
}
