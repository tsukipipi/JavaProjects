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
public class Hough {
    
    //要进行Hough处理的原图像
    private BufferedImage originalImage;
    //图像识别类
    private Sobel SobelDetect;
    //获得二值化的图像
    private BufferedImage binaryImage;
    //极坐标二维数组
    private int [][]polar;
    //获取二维数组的最大值
    private int max = 0;
    //保存线检测的最终图像
    private int[] resultImage;

    //检测直线
    //ρ = xcosθ + ysinθ
    public BufferedImage lineDetect(BufferedImage originalImage){
        
        this.originalImage = originalImage;
        
        SobelDetect = new Sobel();
        //用sobel算子获得二值化图像
        binaryImage = SobelDetect.sobelDetect(this.originalImage);

        //获取二值图像的宽高
        int width = binaryImage.getWidth();
        int height = binaryImage.getHeight();

        resultImage = new int[width * height];
        //获取原图像各像素点的数值，并赋给resultImage数组
        resultImage = originalImage.getRGB(0, 0, width, height, null, 0, width);

        //极坐标的ρ
        int ro = (int)Math.sqrt(width * width + height * height);
        //极坐标的θ
        int theta =180;
        //极坐标二维数组
        polar = new int[ro][theta];

        //直角坐标转化为极坐标
        double polarAngle = Math.PI/(theta*2);

        //对于直角坐标系上的每一个点(x,y)，计算出其180个角度（固定θ）下对应的ρ，即确定极坐标(ρ,θ)
        for(int i = 0; i < theta; i++){
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    if(((binaryImage.getRGB(x,y)) & 0xff0000) >> 16 == 0){
                        //计算出在θ为i时点(x,y)对应的极坐标的ρ
                        int temp_ro =(int)(x * Math.cos(i * polarAngle) + y *Math.sin(i * polarAngle));
                        //对应的极坐标的二维数组加上1
                        polar[temp_ro][i]++;
                    }
                }
            }
        }//end for:计算极坐标二维数组的值
        
        //找到累加的最大值
        for(int x = 0; x < ro; x++)
            for (int y = 0; y < theta; y++)
                if(polar[x][y] > max) max = polar[x][y];

        //阈值
        int threshold = (int)(0.6* max);
        //处理每个(ρ,θ)
        for(int x = 0; x < ro; x++){
            for (int y = 0; y < theta; y++){
                //处理大于阈值的(ρ,θ)对应的直线
                if(polar[x][y] > threshold){
                    //再找到图片中与(ρ,θ)对应的直线对应的像素点，将其染为红色
                    for (int i = 0; i < height; i++){
                        for (int j = 0; j < width; j++){
                            //看红色通道是否为0，如果是0，表示是黑色
                            //由于这是一张二值图像，白色为(255,255,255)，黑色为(0,0,0)
                            if (((binaryImage.getRGB(j,i)) & 0xff0000) >> 16 == 0){
                                //计算出在θ为y时点(j,i)对应的极坐标的ρ
                                int temp_ro =(int)(j * Math.cos(y * polarAngle) + i *Math.sin(y * polarAngle));
                                //判断是否为直线上的像素点
                                if(temp_ro == x) {
                                    //红色
                                    resultImage[i * width + j] = -65536;
                                }
                            }
                        }
                    }
                }
            }
        }

        //将筛选出来的结果生成bitmap
        BufferedImage result =  new BufferedImage(width,height,this.originalImage.getType());
        result.setRGB(0, 0, width, height, resultImage, 0, width);
        return result;

    }
    
    //检测圆
    //x = x0 + rcosθ
    //y = y0 + rsinθ
    public BufferedImage circleDetect(BufferedImage originalImage){
        
        this.originalImage = originalImage;
        
        SobelDetect = new Sobel();
        //用sobel算子获得二值化图像
        binaryImage = SobelDetect.sobelDetect(this.originalImage);
        
        //获取二值图像的宽高
        int width = binaryImage.getWidth();
        int height = binaryImage.getHeight();

        resultImage = new int[width * height];
        //获取原图像各像素点的数值，并赋给resultImage数组
        resultImage = this.originalImage.getRGB(0, 0, width, height, null, 0, width);

        
        //圆360°
        int theta =360;
        //二维数组，累加圆心的最大值
        polar = new int[width][height];
        
        int minx = 1000;

        //半径r
        for(int r = 20; r < 60; ){
            
            //将累加值初始化为0
            for(int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                    polar[x][y] = 0;
            max = 0;
            
            //找出每个像素点360个角度可能存在的圆心
            for(int i = 0; i < theta; i++){
                //转换为弧度角，角度值0 ~ 2*PI 
                double t = i * Math.PI/180;
                for(int y = 0; y < height; y++){
                    for(int x = 0; x < width; x++){
                        //找到像素为黑色的像素点
                        if(((binaryImage.getRGB(x,y)) & 0xff0000) >> 16 == 0 ){
                            //求出该像素点对应的圆心
                            int x0 = (int) Math.round(x - r * Math.cos(t));  
                            int y0 = (int) Math.round(y - r * Math.sin(t));  
                            //确认圆心在图片上，对应的圆心的二维数组加上1
                            if (x0 < width && x0 > 0 && y0 < height && y0 > 0) { 
                                polar[x0][y0]++;
                            }
                        }
                    }
                }
            }//end for:计算极坐标二维数组的值
        
            //找到累加的最大值
            for(int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                    if(polar[x][y] > max)
                        max = polar[x][y];
        
            //阈值
            int threshold = (int)(0.9* max);
            //对于每一个累加值大于0.9* max的圆心，找到其对应的圆
            for(int x0 = 0; x0 < width; x0++){
                for (int y0 = 0; y0 < height; y0++){
                    //处理大于阈值的圆心
                    if(polar[x0][y0] > threshold){
                        for(int i = 0; i < theta; i++){
                            //角度值0 ~ 2*PI 
                            double t = i * Math.PI/180;
                            //求出该圆心对应的圆
                            int x = (int) Math.round(x0 + r * Math.cos(t));  
                            int y = (int) Math.round(y0 + r * Math.sin(t));
                            if (x < width && x > 0 && y < height && y > 0) { 
                                //看红色通道是否为0，如果是0，表示是黑色
                                //由于这是一张二值图像，白色为(255,255,255)，黑色为(0,0,0)
                                if (((binaryImage.getRGB(x,y)) & 0xff0000) >> 16 == 0 ){
                                    //红色
                                    if(x < minx)minx = x;
                                    resultImage[y * width + x] = -65536;
                                }
                            }
                        }
                    }
                }
            }
            
            r = r + 10;
        }
        System.out.println("最小x轴的值：" + minx);
        //将筛选出来的结果生成bitmap
        BufferedImage result =  new BufferedImage(width,height,this.originalImage.getType());
        result.setRGB(0, 0, width, height, resultImage, 0, width);
        return result;
    }
    
}
