package com.pipi.entity;

import javax.persistence.*;

/*
@Table标签，指定数据库中对应的表名
 */
@Entity
@Table(name = "tbl_user")
public class User {
    //id配置为主键，生成策略为自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    private String name;
    private String password;

    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
}
