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
 * @author lenovo
 */
public class Rectangle extends JPanel implements MyShape {
        
    private int R_width,R_length;       //矩形的宽和长
    private int R_x,R_y;                //矩形的最小坐标
    
    
    //构造方法
    
    public Rectangle(int R_width, int R_length, int R_x, int R_y) {
        
        this.R_width=R_width;
        this.R_length=R_length;
        this.R_x = R_x;
        this.R_y = R_y;
        
    }
    
    
    //构造方法，将字符串转化为整型，并赋值给属性
    public Rectangle(String str){
        
        //定义一个字符型的数组，存放用空格分割的每个形状的边长
        ////存放用","分割的各个数据
        String []tempSide;
        
        //比较两个字符串内容：a.equals(b)   比较a和b的内存地址：a==b
        if(str.startsWith("R"))
        {
            tempSide=str.substring(1).split(",");//分割字符
            
            R_width=Integer.parseInt(tempSide[0]);
            R_length=Integer.parseInt(tempSide[1]);
            R_x=Integer.parseInt(tempSide[2]);
            R_y=Integer.parseInt(tempSide[3]);
            
        }//end if
        
    }//构造方法结束
    
   
    //toString方法
    @Override    
    public String toString() {
        
        return "Rectangle{" + "width=" + R_width + ", length=" + R_length + ", x=" + R_x + ", y=" + R_y+'}';
        
    }
    
    
    //toShortString
    public String toShortString() {
        
        return "R" + R_width+ "," + R_length+ "," + R_x + ","+ R_y;
        
    }
    
    
    public void setLength(int l) {
        this.R_length = l;
    }
    
    
    public void setWidth(int w) {
        this.R_width = w;
    }
    
    
    public void setX(int x) {
        this.R_x = x;
    }
    
    public void setY(int y) {
        this.R_y = y;
    }
    
    
    public int getLength() {
        return this.R_length ;
    }
    
    
    public int getWidth() {
        return this.R_width ;
    }
    
    
    public int getX() {
        return this.R_x ;
    }
    
    
    public int getY() {
        return this.R_y ;
    }

    
    //绘制矩形
    public void paint(Graphics g) {
        
        g.setColor(Color.ORANGE);
        g.fillRect(R_x, R_y, R_width, R_length);
        //g.drawRect(R_x, R_y, R_width, R_length);
        
    }
    
}
