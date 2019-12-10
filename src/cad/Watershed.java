/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvConvertScale;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import com.googlecode.javacv.cpp.opencv_imgproc;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_OTSU;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvThreshold;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvWatershed;
import java.awt.image.BufferedImage;
import org.opencv.core.Core;

/**
 *
 * @author virajee
 */
//link-https://stackoverflow.com/questions/11294859/how-to-define-the-markers-for-watershed-in-opencv
public class Watershed {

    public Watershed() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void implementWatershed(String inputPath,String outputPath) {
        
        String sourcePath = inputPath;
        opencv_core.IplImage sourceImage = cvLoadImage(sourcePath);

        //convert source image to grey image
        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);        
        cvCvtColor(sourceImage, grayImage, CV_BGR2GRAY);

        //thresholding 
        //get threshold value
        BufferedImage img2 = grayImage.getBufferedImage();
        ThresholdingImage t = new ThresholdingImage();        
        int threshold = ThresholdingImage.autoThreshold(img2);
      
        //otsu binarization
        cvThreshold(grayImage, grayImage, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU);

        //erosion
        opencv_core.IplImage erode = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);       
        opencv_imgproc.cvErode(grayImage, erode, null, 1);  

        //dilation
        opencv_core.IplImage dilate = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);        
        opencv_imgproc.cvDilate(grayImage, dilate, null, 3);         

        //
        opencv_core.IplImage bg = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);
        cvThreshold(dilate, bg, 150, 128, 1); 

        //
        opencv_core.IplImage marker = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);
        opencv_core.cvAdd(erode, bg, marker, null);        

        //convert to 32SC1 (32 bit signed single channel).
        opencv_core.IplImage marker32 = cvCreateImage(cvGetSize(sourceImage), opencv_core.IPL_DEPTH_32S, 1);
        cvConvertScale(marker, marker32, 1, 0);
        
        //apply watershed
        cvWatershed(sourceImage, marker32);        

        //convert result back into uint8 image
        opencv_core.IplImage marker8U = cvCreateImage(cvGetSize(sourceImage), opencv_core.IPL_DEPTH_8U, 1);
        cvConvertScale(marker32, marker8U, 1, 0);
        String destPath8 = outputPath;
        cvSaveImage(destPath8, marker8U);

    }
}
