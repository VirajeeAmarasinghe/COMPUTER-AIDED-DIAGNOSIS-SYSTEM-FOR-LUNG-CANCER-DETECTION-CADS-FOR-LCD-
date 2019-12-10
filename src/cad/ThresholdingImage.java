/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
public class ThresholdingImage {

    public static BufferedImage image = null;

    public static void main(String[] args) {
        //reading the image
        System.out.println("Reading the Grayscale Image");
        try {
            File input = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\grayScaleImage.bmp");
            image = ImageIO.read(input);
        } catch (IOException e) {
        }

        int threshold = autoThreshold(image);

        image = toBinary(image, threshold);

        save(image);

    }

    public static int autoThreshold(BufferedImage img) {
        //final threshold value for the image
        //Initially thresholdValue = 127 [i.e. 255/2 = 127 (integer part)] 
        int thresholdValue = 127;

        //iThreshold will hold the intermediate threshold value during computation of final threshold value.
        int iThreshold;

        int sum1; //holds the sum of avgOfRGB values less than thresholdValue
        int sum2; //holds the sum of avgOfRGB values greater than or equal to the thresholdValue
        int count1; //holds the number of pixels whose avgOfRGB value is less than thresholdValue
        int count2; //holds the number of pixels whose avgOfRGB value is greater than or equal to thresholdValue

        //mean1 is equal to sum1/count1. mean2 is equal to sum2/count2.         
        int mean1, mean2;

        //calculating thresholdValue         
        while (true) {
            sum1 = sum2 = count1 = count2 = 0;
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    int color = img.getRGB(x, y);
                    int r = (color >> 16) & 255;
                    int g = (color >> 8) & 255;
                    int b = (color) & 255;
                    int avgOfRGB = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                    if (avgOfRGB < thresholdValue) {
                        sum1 += avgOfRGB;
                        count1++;
                    } else {
                        sum2 += avgOfRGB;
                        count2++;
                    }
                }
            }

            //calculating mean             
            mean1 = (count1 > 0) ? (int) (sum1 / count1) : 0;
            mean2 = (count2 > 0) ? (int) (sum2 / count2) : 0;

            //calculating intermediate threshold             
            iThreshold = (mean1 + mean2) / 2;

            if (thresholdValue != iThreshold) {
                thresholdValue = iThreshold;
            } else {
                break;
            }
        }
        return thresholdValue;
    }

    public static BufferedImage toBinary(BufferedImage img, int thresholdValue) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] imgArr = new int[width][height];
        Raster raster = img.getData();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imgArr[i][j] = raster.getSample(i, j, 0);
                if (imgArr[i][j] >= thresholdValue) {
                    img.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    img.setRGB(i, j, Color.WHITE.getRGB());
                }
            }
        }
        return img;
    }

    public static void save(BufferedImage img) {
        try {
            File output = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary.bmp");
            ImageIO.write(img, "bmp", output);
        } catch (Exception e) {
        }

    }
}
