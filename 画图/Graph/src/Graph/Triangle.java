package Graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
//引入substring方法的包
import java.lang.String;
import javax.swing.JPanel;

/**
 *
 * @author Pipi
 */
public class Triangle extends JPanel implements MyShape {
        
    private int []T_x=new int[3],T_y=new int[3];

    
    //构造方法
    public Triangle(int T_x1, int T_y1, int T_x2, int T_y2, int T_x3, int T_y3) {
        
        this.T_x[0] = T_x1;
        this.T_y[0] = T_y1;
        this.T_x[1] = T_x2;
        this.T_y[1] = T_y2;
        this.T_x[2] = T_x3;
        this.T_y[2] = T_y3;
        
    }
    
    
    //构造方法，将字符串转化为整型，并赋值给属性
    public Triangle(String str){
        
        //定义一个字符型的数组，存放用空格分割的每个形状的边长
        ////存放用","分割的各个数据
        String []tempSide;
        
        //比较两个字符串内容：a.equals(b)   比较a和b的内存地址：a==b
        if(str.startsWith("T"))
        {
            tempSide=str.substring(1).split(",");//分割字符
            
            T_x[0] = Integer.parseInt(tempSide[0]);
            T_y[0] = Integer.parseInt(tempSide[1]);
            T_x[1] = Integer.parseInt(tempSide[2]);
            T_y[1] = Integer.parseInt(tempSide[3]);
            T_x[2] = Integer.parseInt(tempSide[4]);
            T_y[2] = Integer.parseInt(tempSide[5]);
            
        }//end if
        
    }//构造方法结束
    
    
    //toString方法
    @Override
    public String toString(){   
        
        return "Triangle{" + "x1=" + T_x[0] + ", y1=" + T_y[0]
                           + ", x2=" + T_x[1] + ", y2=" + T_y[1]
                           + ", x3=" + T_x[2] + ", y3=" + T_y[2];
        
    }

    
    //toShortString
    public String toShortString() {
        
        return "T" + T_x[0] + "," + T_y[0]  + "," + T_x[1] + "," + T_y[1] + "," + T_x[2] + "," + T_y[2];
        
    }
    
    
    public void setX0(int x) {
        this.T_x[0]  = x;
    }
    
    
    public void setX1(int x) {
        this.T_x[1]  = x;
    }
    
    
    public void setX2(int x) {
        this.T_x[2]  = x;
    }
    
    
    public void setY0(int y) {
        this.T_y[0] = y;
    }
    
    
    public void setY1(int y) {
        this.T_y[1] = y;
    }
    
    
    public void setY2(int y) {
        this.T_y[2] = y;
    }
    
    
    public int getX0() {
        return this.T_x[0]  ;
    }
    
    
    public int getX1() {
        return this.T_x[1]  ;
    }
    
    
    public int getX2() {
        return this.T_x[2] ;
    }
    
    
    public int getY0() {
        return this.T_y[0] ;
    }
    
    
    public int getY1() {
        return this.T_y[1] ;
    }
    
    
    public int getY2() {
        return this.T_y[2] ;
    }

    
    //绘制三角形
    @Override
    public void paint(Graphics g) {
        
        g.setColor(Color.GREEN);
        g.fillPolygon(T_x, T_y, 3);
        //T_x[3]=T_x[0];T_y[3]=T_y[0];
        //g.drawPolygon(T_x, T_y, 3);
        
    }
    
}
