/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author virajee
 */

//link-https://www.tutorialspoint.com/java_dip/basic_thresholding.htm

public class ImageThresholdingUsingOpenCV {
    public static void main( String[] args ){
   
      try{
         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
         //Loads an image from a file
         Mat source = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\grayScaleImage.bmp",  Highgui.CV_LOAD_IMAGE_COLOR);
         if(source==null){
             System.out.println("Image is not loaded");
         }else{
         Mat destination = new Mat(source.rows(),source.cols(),source.type());

         destination = source;
         Imgproc.threshold(source,destination,150,255,Imgproc.THRESH_TOZERO);
         Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary_3.bmp", destination);
         }
      }catch (Exception e) {
         System.out.println("error: " + e.getMessage());
      }
      
   }
    
}
