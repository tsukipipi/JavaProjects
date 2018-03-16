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
public class Square extends JPanel implements MyShape {
        
    private int S_side;         //正方形的边长
    private int S_x,S_y;        //正方形的最小坐标
    
    //构造方法
    public Square(int S_side, int S_x, int S_y) {
        
        this.S_side=S_side;
        this.S_x = S_x;
        this.S_y = S_y;
        
    }
    
    
    //构造方法，将字符串转化为整型，并赋值给属性
    public Square(String str){
        
        //存放用","分割的各个数据
        String []tempSide;
        
        //比较两个字符串内容：a.equals(b)   比较a和b的内存地址：a==b
        if(str.startsWith("S"))
        {
            tempSide=str.substring(1).split(",");//分割字符
            
            S_side=Integer.parseInt(tempSide[0]);
            S_x=Integer.parseInt(tempSide[1]);
            S_y=Integer.parseInt(tempSide[2]);
            
        }
        
    }
    
    
    //toString方法
    @Override
    public String toString() {
        
        return "Square{" + "side=" + S_side + ", x=" + S_x + ", y=" + S_y+'}';
        
    }
    
    
    //toShortString
    public String toShortString(){   
        
        return "S" + S_side + "," + S_x + ","+ S_y;
        
    }
    
    
    public void setSide(int s) {
        this.S_side = s;
    }
    
    
    public void setX(int x) {
        this.S_x = x;
    }
    
    public void setY(int y) {
        this.S_y = y;
    }
    
    
    public int getSide() {
        return this.S_side ;
    }
    
    
    public int getX() {
        return this.S_x ;
    }
    
    
    public int getY() {
        return this.S_y ;
    }

    
    //绘制正方形
    public void paint(Graphics g) {
        
        g.setColor(Color.PINK);
        g.fillRect(S_x, S_y, S_side, S_side);
        //g.drawRect(S_x, S_y, S_side, S_side);
        
    }
    
}
