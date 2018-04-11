package com.test;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App obj = new App();
        System.out.println("Unique ID : " + obj.generateUniqueKey());
    }

    //生成包含恰好36位字母表的唯一密钥
    public String generateUniqueKey(){

        String id = UUID.randomUUID().toString();
        return id;

    }

}
