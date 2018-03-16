package Graph;


import java.awt.Color;
import java.awt.Graphics;
//引入substring方法的包
import java.lang.String;

import javax.swing.JPanel;


/**
 *
 * @author Pipi
 */
public class Circle extends JPanel implements MyShape{
        
    private int C_radius;        //圆半径
    private int C_x,C_y;        //圆心坐标

    //构造方法
    public Circle() {
        
        this.C_radius = 100;
        this.C_x = 200;
        this.C_y = 200;
        
    }

    
    //构造方法
    public Circle(int C_radius, int C_x, int C_y) {
        this.C_radius = C_radius;
        this.C_x = C_x;
        this.C_y = C_y;
    }
    
    
    //构造方法，将字符串转化为整型，并赋值给属性
    public Circle(String str){
        
        //存放用","分割的各个数据
        String []tempSide;
        
        //比较两个字符串内容：a.equals(b)   比较a和b的内存地址：a==b
        if(str.startsWith("C"))
        {
            tempSide=str.substring(1).split(",");//分割字符
            
            C_radius=Integer.parseInt(tempSide[0]);
            C_x=Integer.parseInt(tempSide[1]);
            C_y=Integer.parseInt(tempSide[2]);
        }
            
        
    }
    
    
    //toString方法
    @Override    
    public String toString(){   
        
        return "Circle{" + "radius=" + C_radius + ", x=" + C_x + ", y="+C_y+'}';
    }

    //toShortString
    public String toShortString(){   
        
        return "C" + C_radius + "," + C_x + ","+ C_y;
        
    }
    
    
    public void setR(int r) {
        this.C_radius = r;
    }
    
    
    public void setX(int x) {
        this.C_x = x;
    }
    
    public void setY(int y) {
        this.C_y = y;
    }
    
    
    public int getR() {
        return this.C_radius ;
    }
    
    
    public int getX() {
        return this.C_x ;
    }
    
    
    public int getY() {
        return this.C_y ;
    }
    

    //绘制圆形
    public void paint(Graphics g) {
        
        //drawOval(int x,int y,int width,int height)绘制椭圆的边框,得到的是一个圆或椭圆,它恰好适合放在由 x、y、width 和 height 参数指定的矩形内。
        //参数：x,y左上角的坐标，width和height是椭圆的长和宽，相等时画出来的是圆
        g.setColor(Color.white);
        //填充
        g.fillOval(C_x-C_radius, C_y-C_radius, 2*C_radius, 2*C_radius);
        //无填充
        //g.drawOval(C_x-C_radius, C_y-C_radius, 2*C_radius, 2*C_radius);
        
    }

}
