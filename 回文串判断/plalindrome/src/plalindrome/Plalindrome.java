/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plalindrome;

import java.util.Scanner;

/**
 *
 * @author pipi
 */
public class Plalindrome {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //用于保存输入的字符串
        String st = null;
        
        System.out.println("请输入一个字符串：");
        Scanner sc = new Scanner(System.in);
        st = sc.nextLine();
        
        int len = st.length();
        boolean flag = true;
        char [] c = new char[len];
        for(int i = 0; i < len; i ++)
            c[i]=st.charAt(i);
        //逐个字母判断是否为回文串
        for(int i = 0; i < len / 2; i ++)
        {
            if(c[i] != c[len-i-1])
            {
                flag=false;
                break;
            }
        }
        
        if(flag == true)System.out.println("YES");
        else System.out.println("NO");
        
    }
    
}
