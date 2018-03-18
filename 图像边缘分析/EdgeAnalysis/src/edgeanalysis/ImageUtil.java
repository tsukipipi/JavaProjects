/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edgeanalysis;

import java.awt.image.BufferedImage;

/**
 *
 * @author pipi
 */
public class ImageUtil {
    
    //将图片转化成灰度图
    public BufferedImage toGrayImage(BufferedImage originalImage) {

        //获取图片的宽和高
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        //读取图像像素信息保存为int数组
        int[] data = originalImage.getRGB(0, 0, width, height, null, 0, width);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c = data[x + y * width];
                int R = (c >> 16) & 0xFF;  
                int G = (c >> 8) & 0xFF;  
                int B = (c >> 0) & 0xFF; 
                c = (int)(0.3f*R + 0.59f*G + 0.11f*B); //to gray  
                data[x + y * width] = (255<<24)|(c<<16)|(c<<8)|(c);
            }  
        }  
        //将int数组重新转化为Bufferedmage
        BufferedImage grayImage = new BufferedImage(width,height,originalImage.getType());
        grayImage.setRGB(0, 0, width, height, data, 0, width);
        /*try {  
            ImageIO.write(grayImage, "JPEG", new File("2.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return grayImage;
    }
    
}
