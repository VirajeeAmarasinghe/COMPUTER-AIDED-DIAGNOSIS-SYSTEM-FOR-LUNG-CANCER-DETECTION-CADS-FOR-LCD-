/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
//link-https://www.dyclassroom.com/image-processing-project/how-to-convert-a-color-image-into-grayscale-image-in-java
public class ConvertRGBImageToGrayScale {    
    

    public BufferedImage convertToGrayscale(final BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);

                int alpha = (color >> 24) & 255;
                int red = (color >> 16) & 255;
                int green = (color >> 8) & 255;
                int blue = (color) & 255;
            
                //relation used by GNU Image Manipulation Program
                final int lum = (int) (0.3 * red + 0.59 * green + 0.11 * blue);

                alpha = (alpha << 24);
                red = (lum << 16);
                green = (lum << 8);
                blue = lum;

                color = alpha + red + green + blue;

                image.setRGB(i, j, color);
            }
        }
        return image;
    }    
    
    public BufferedImage readImage(File f){
         //read image
        BufferedImage img=null;
        try {           
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return img;
    }
    
    public boolean writeImage(BufferedImage img,File f){
        //write image
        boolean result=false;
        try {            
            result=ImageIO.write(img, "bmp", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    

}
