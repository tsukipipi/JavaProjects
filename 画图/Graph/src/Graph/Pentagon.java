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
public class Pentagon extends JPanel implements MyShape{
    
    //五边形的坐标,形成闭合图形，最后一个点与第一个点重合
    private int []P_x =new int[5], P_y =new int[5];
    
    
    //构造方法

    public Pentagon(int []x,int []y) {
        
        for(int i=0;i<5;i++)
        {
            P_x[i]=x[i];
            P_y[i]=y[i];
        }
        
    }
   

    
    //构造方法，将字符串转化为整型，并赋值给属性
    public Pentagon(String str) {
        
        //存放用","分割的各个数据
        String []tempSide;
        
        //比较两个字符串内容：a.equals(b)   比较a和b的内存地址：a==b
        if(str.startsWith("P"))
        {
            tempSide=str.substring(1).split(",");//分割字符

            P_x[0]=Integer.parseInt(tempSide[0]);
            P_y[0]=Integer.parseInt(tempSide[1]);
            
            P_x[1]=Integer.parseInt(tempSide[2]);
            P_y[1]=Integer.parseInt(tempSide[3]);
            
            P_x[2]=Integer.parseInt(tempSide[4]);
            P_y[2]=Integer.parseInt(tempSide[5]);
            
            P_x[3]=Integer.parseInt(tempSide[6]);
            P_y[3]=Integer.parseInt(tempSide[7]);
            
            P_x[4]=Integer.parseInt(tempSide[8]);
            P_y[4]=Integer.parseInt(tempSide[9]);
        }
        
    }
    
    
    //toString方法

    @Override
    public String toString() {
        return "Pentagon{" + "x0=" + P_x[0] + " y0=" + P_y[0]
                           + ",x1=" + P_x[1] + ",y1=" + P_y[1]
                           + ",x2=" + P_x[2] + ",y2=" + P_y[2]
                           + ",x3=" + P_x[3] + ",y3=" + P_y[3] 
                           + ",x4=" + P_x[4] + ",y4=" + P_y[4] + '}';
    }
    

   
    //toShortString
    public String toShortString() {
        
        return "P"  + P_x[0] + ","+ P_y[0]
                 + "," + P_x[1] + ","+ P_y[1]
                 + "," + P_x[2] + ","+ P_y[2]
                 + "," + P_x[3] + ","+ P_y[3]
                 + "," + P_x[4] + ","+ P_y[4];
        
    }
    
    
    public void setX0(int x) {
        this.P_x[0]  = x;
    }
    
    
    public void setX1(int x) {
        this.P_x[1]  = x;
    }
    
    
    public void setX2(int x) {
        this.P_x[2]  = x;
    }
    
    
    public void setX3(int x) {
        this.P_x[3]  = x;
    }
    
    
    public void setX4(int x) {
        this.P_x[4]  = x;
    }
    
    
    public void setY0(int y) {
        this.P_y[0] = y;
    }
    
    
    public void setY1(int y) {
        this.P_y[1] = y;
    }
    
    
    public void setY2(int y) {
        this.P_y[2] = y;
    }
    
    
    public void setY3(int y) {
        this.P_y[3] = y;
    }
    
    
    public void setY4(int y) {
        this.P_y[4] = y;
    }
    
    
    public int getX0() {
        return this.P_x[0]  ;
    }
    
    
    public int getX1() {
        return this.P_x[1] ;
    }
    
    
    public int getX2() {
        return this.P_x[2]  ;
    }
    
    
    public int getX3() {
        return this.P_x[3] ;
    }
    
    
    public int getX4() {
        return this.P_x[4] ;
    }
    
    
    public int getY0() {
        return this.P_y[0] ;
    }
    
    
    public int getY1() {
        return this.P_y[1] ;
    }
    
    
    public int getY2() {
        return this.P_y[2] ;
    }
    
    
    public int getY3() {
        return this.P_y[3] ;
    }
    
    
    public int getY4() {
        return this.P_y[4] ;
    }
    

    //绘制正五边形
    public void paint(Graphics g) {
        
        g.setColor(Color.BLUE);
        g.fillPolygon(P_x, P_y, 5);
        //g.drawPolygon(P_x, P_y, 5);
        
    }
        
}
