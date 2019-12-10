/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import com.googlecode.javacv.cpp.opencv_imgproc;
import com.googlecode.javacv.cpp.opencv_imgproc.CLAHE;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
//link-http://docs.opencv.org/3.1.0/d5/daf/tutorial_py_histogram_equalization.html
public class HistogramsEqualization {

    public HistogramsEqualization() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //input image is grayscale image
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\Chest Lung Nodules\\JPCLN100-512x512-16bit.bmp", 0);

        //(global) histogram equalization
        Mat equalizedImage = new Mat();
        Imgproc.equalizeHist(image, equalizedImage);

        String outputPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\HistogramEqualized.bmp";
        Highgui.imwrite(outputPath, equalizedImage);

       //Contrast Limited Adaptive Histogram Equalization (Adaptive Histogram Equalization)
        IplImage sourceImage = cvLoadImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\Chest Lung Nodules\\JPCLN100-512x512-16bit.bmp");
        IplImage grayImage = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);
        cvCvtColor(sourceImage, grayImage, CV_BGR2GRAY);
        IplImage claheImage = cvCreateImage(cvGetSize(grayImage), grayImage.depth(), grayImage.nChannels());

        //CLAHE 
        CLAHE c = opencv_imgproc.createCLAHE(28.0, cvSize(8, 8));  //2.0 8x8
        c.apply(grayImage, claheImage);

        cvSaveImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ContrastLimitedAdaptiveHistogramEqualized.bmp", claheImage);

    }

    //Contrast Limited Adaptive Histogram Equalization (Adaptive Histogram Equalization)
    public void equalizeHisto(String inputPath, String outputPath) {        
        opencv_core.IplImage sourceImage = cvLoadImage(inputPath);
        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);
        cvCvtColor(sourceImage, grayImage, CV_BGR2GRAY);
        opencv_core.IplImage claheImage = cvCreateImage(cvGetSize(grayImage), grayImage.depth(), grayImage.nChannels());

        //CLAHE 
        opencv_imgproc.CLAHE clahe = opencv_imgproc.createCLAHE(3.0, cvSize(8, 8));  
        clahe.apply(grayImage, claheImage);

        cvSaveImage(outputPath, claheImage);
    }
}
