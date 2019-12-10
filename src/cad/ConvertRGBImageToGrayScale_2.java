/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
public class ConvertRGBImageToGrayScale_2 {
    public static void main(String []args){
        //image source
        File originalImage=new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Left-Image_CT_JPEG.jpg");
        BufferedImage img=null;
        
        try {
            img=ImageIO.read(originalImage);
            
            //image for grayscale result
            BufferedImage grayScaleImage=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
            
            for(int i=0;i<img.getWidth();i++){
               for(int j=0;j<img.getHeight();j++){
                   //get RGB Color on each pixel
                   Color c=new Color(img.getRGB(i,j));
                   
                   int r=c.getRed();
                   int g=c.getGreen();
                   int b=c.getBlue();
                   int a=c.getAlpha();
                   
                   //grayscaling
                   int gr=(int)(0.3 * r + 0.59 * g + 0.11 * b);      
                
                 
                   //create graycolor
                   Color gColor=new Color(gr,gr,gr,a);
                   grayScaleImage.setRGB(i,j,gColor.getRGB());
                
               }
            }
            
            //write image
            System.out.println("comes here");
            ImageIO.write(grayScaleImage,"jpg",new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CT_Image_For_Lung_Cancer_JPG_Gray_Scale_2.jpg"));
        } catch (Exception e) {
        }
    }
}
