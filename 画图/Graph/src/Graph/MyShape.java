package Graph;
//引入图形包
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Pipi
 */
public interface MyShape {
    
    //图形绘制
    void paint(Graphics g); 
    //转化为短字符串
    public String toShortString();
    
}
