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
 * @author lenovo
 */
public class Text extends JPanel implements MyShape {

    private String text;        //文字类属性
    
    //构造方法
    public Text(String text) {
        
        this.text = text;
        
    }
    
    
    //toString方法
    @Override
    public String toString() {
        
        return "Text{" + "text=" + text + '}';
        
    }
    
    
    //toShortString方法
    public String toShortString() {
        
        return text;
        
    }
    
    
    public void setText(String t) {
        this.text = t;
    }
    
    
    public String getText() {
        return this.text ;
    }
    
    
    //绘制文字
    @Override
    public void paint(Graphics g) {
        
        g.setColor(Color.BLACK);
        //public abstract void drawString(String s,float x,float y)
        //参数:①s - 要呈现的String  ②x - 呈现 String 位置的 x 坐标  ③y - 呈现 String 位置的 y 坐标
        g.drawString(text, 100, 100);
        
    }
    
}
