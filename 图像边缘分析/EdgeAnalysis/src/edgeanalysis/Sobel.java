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

/*
* GX = [-1 0 1    GY = [-1 -2 -1
*       -2 0 2           0  0  0
*       -1 0 1]          1  2  1]
*/
public class Sobel {
    
    //要进行sobel处理的原图像
    private BufferedImage originalImage;
    //保存图像的灰度图
    private BufferedImage grayImage;
    //保存灰度图sobel处理后的最大值
    private double max;
    //保存灰度图每个像素点的数值
    private int[] grayImagePixels;
    //保存灰度图每个像素点计算后各对应点的数值
    private double[] calcGrayImagePixels;
    //存放处理后的图象各像素点的数组
    int[] resultImage;

    //获取原图像的灰度图
    private void getGrayImage() {
        //将原图灰度化
        this.grayImage = new ImageUtil().toGrayImage(this.originalImage);
    }

    //对灰度图进行sobel算子计算
    private void sobel(){
        //获取灰度图的宽高
        int width = grayImage.getWidth();
        int height= grayImage.getHeight();

        //存放灰度图每个像素点的数值
        grayImagePixels = new int[width * height];
        //保存灰度图每个像素点计算后各对应点的数值
        calcGrayImagePixels = new double[width * height];

        //获取灰度图像素信息保存为int数组，并赋给grayBitmapPixels数组
        grayImagePixels = grayImage.getRGB(0, 0, width, height, null, 0, width);

        //保存数值最大的数
        max = -999;
        //进行计算
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //计算横向数值
                double gx = GX(i, j, grayImage);
                //计算纵向数值
                double gy = GY(i, j, grayImage);
                //进行开方处理，calcGrayBitmapPixels保存计算后的值
                calcGrayImagePixels[j * width + i] = Math.sqrt(gx * gx + gy * gy);
                //保存最大值
                if (max < calcGrayImagePixels[j * width + i]) {
                    max = calcGrayImagePixels[j * width + i];
                }
            }//end for
        }//end for
    }

    //x方向sobel处理
    private double GX(int x, int y, BufferedImage image) {
        /**
         * GX = [-1 0 1
         *       -2 0 2
         *       -1 0 1]
         */
        double res ;
        res = (-1) * getPixel(x - 1, y - 1, image)
                + 1 * getPixel(x + 1, y - 1, image)
                + (-2) * getPixel(x - 1, y, image)
                + 2 * getPixel(x + 1, y, image)
                + (-1) * getPixel(x - 1, y + 1, image)
                + 1 * getPixel(x + 1, y + 1, image);
        return res;
    }

    //y方向sobel处理
    private double GY(int x, int y, BufferedImage image) {
        /**
         * GY = [-1 -2 -1
         *       0  0  0
         *       1  2  1]
         */
        double res ;
        res = (-1) * getPixel(x - 1, y - 1, image)
                + (-2) * getPixel(x, y - 1, image)
                + (-1) * getPixel(x + 1, y - 1, image)
                + 1 * getPixel(x - 1, y + 1, image)
                + 2 * getPixel(x, y + 1, image)
                + 1 * getPixel(x + 1, y + 1, image);
        return res;
    }

    //获取第x行第y列的像素值
    private double getPixel(int x, int y, BufferedImage image) {
        //判断是否超出范围
        if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
            return 0;
        }
        return grayImagePixels[y * grayImage.getWidth() + x];
    }

    //根据阈值将图像二值化
    private void getBinaryImage(){
        //获取灰度图的宽高
        int width = grayImage.getWidth();
        int height= grayImage.getHeight();
        //存放处理后的图象各像素点的数组
        this.resultImage = new int[width * height];

        //筛选计算
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (calcGrayImagePixels[j * width + i] > max * 0.1) {
                    //黑色
                    resultImage[j * width + i] = -16777216;
                } else {
                    //白色
                    resultImage[j * width + i] = -1;
                }
            }
        }
    }

    //根据设定的阙值获取处理后的图片
    public BufferedImage sobelDetect(BufferedImage originalImage){

        //获取原图
        this.originalImage = originalImage;
        //先获得灰度图
        getGrayImage();
        //对灰度图进行sobel算子计算
        sobel();
        //根据阈值将图像二值化
        getBinaryImage();
        //将筛选出来的结果生成BufferedImage
        BufferedImage result = new BufferedImage(grayImage.getWidth(),grayImage.getHeight(),originalImage.getType());
        result.setRGB(0, 0, grayImage.getWidth(), grayImage.getHeight(), resultImage, 0, grayImage.getWidth());
        
        return result;

    }
    
}
