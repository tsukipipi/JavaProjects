/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author pipi
 */
public class Line extends JPanel implements MyShape {
    
    private int L_x1,L_y1,L_x2,L_y2;        //直线段起点和终点坐标

    
    //构造函数
    public Line(int L_x1, int L_y1, int L_x2, int L_y2) {
        this.L_x1 = L_x1;
        this.L_y1 = L_y1;
        this.L_x2 = L_x2;
        this.L_y2 = L_y2;
    }
    
    
    //构造方法
    public Line(String str) {
        
        //存放用","分割的各个数据
        String []tempSide;
        
        //比较两个字符串内容：a.equals(b)   比较a和b的内存地址：a==b
        if(str.startsWith("L"))
        {
            tempSide=str.substring(1).split(",");//分割字符
            
            L_x1=Integer.parseInt(tempSide[0]);
            L_y1=Integer.parseInt(tempSide[1]);
            L_x2=Integer.parseInt(tempSide[2]);
            L_y2=Integer.parseInt(tempSide[3]);
            
        }
        
    }
    

    //toString方法
    public String toString() {
        
        return "Line{" + "x1=" + L_x1 + ", y1=" + L_y1 + ", x2=" + L_x2 + ", y2=" + L_y2 + '}';
        
    }
    
    
    //toShortString方法
    public String toShortString() {
        
        return "L" + L_x1 + "," + L_y1 + "," + L_x2 + "," + L_y2 ;
    
    }
    
    
    public void setX1(int x) {
        this.L_x1  = x;
    }
    
    
    public void setX2(int x) {
        this.L_x2  = x;
    }
    
    public void setY1(int y) {
        this.L_y1 = y;
    }
    
    
    public void setY2(int y) {
        this.L_y2 = y;
    }
    
    
    public int getX1() {
        return this.L_x1  ;
    }
    
    
    public int getX2() {
        return this.L_x2 ;
    }
    
    
    public int getY1() {
        return this.L_y1 ;
    }
    
    
    public int getY2() {
        return this.L_y2 ;
    }
    

    //绘制直线
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(L_x1, L_y1, L_x2, L_y2);
        
    }
    
}
