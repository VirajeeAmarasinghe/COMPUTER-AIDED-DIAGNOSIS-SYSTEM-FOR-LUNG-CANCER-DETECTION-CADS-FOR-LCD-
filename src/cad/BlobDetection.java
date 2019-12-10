/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacpp.Loader.sizeof;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvDrawContours;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RETR_CCOMP;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindContours;

import java.awt.Color;
import java.util.Random;

import com.googlecode.javacv.cpp.opencv_core.CvContour;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 *
 * @author virajee
 */
public class BlobDetection {

    public void detectingBlobs() {
        String sourcePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary.bmp";
        String targetPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConnectedComponents.bmp";

        IplImage image = cvLoadImage(sourcePath);
        /*
         cvCreateImage(CvSize size,int depth,int channels)
         size (CvSize)-Image width and height
         depth(int)-Bit depth of image elements
         channels (Int32)-Number of channels per element(pixel). Can be 1, 2, 3 or 4. The channels are interleaved, for example the usual data layout of a color image is: b0 g0 r0 b1 g1 r1 ... 
         */
        IplImage grayImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

        /**
         * *********************Converting to
         * Grayscale****************************************
         */
        /*
         cvCvtColor(opencv_core.CvArr cvarr, opencv_core.CvArr cvarr1, int i)
         cvarr-The source 8-bit (8u), 16-bit (16u) or single-precision floating-point (32f) image
         cvarr1-The destination image of the same data type as the source one. 
         The number of channels may be different
         i-Color conversion operation that can be specifed using CV_src_color_space2dst_color_space constants
         CV_BGR2GRAY-Convert BGR color to GRAY color 
         */
        cvCvtColor(image, grayImage, CV_BGR2GRAY);  //this process need to convert the number of channels of the image

        /**
         * **********************Thresholding***************************************************
         */
        CvMemStorage mem;
        CvSeq contours = new CvSeq();
        CvSeq ptr = new CvSeq();

        /*
         cvThreshold(opencv_core.CvArr cvarr, opencv_core.CvArr cvarr1, double d, double d1, int i)
         cvarr-Source array (single-channel, 8-bit of 32-bit floating point).\
         cvarr1-Destination array; must be either the same type as src or 8-bit
         d-Threshold value
         d1-Maximum value to use with CV_THRESH_BINARY and CV_THRESH_BINARY_INV thresholding types
         i-Thresholding type 
         */
        //cvThreshold(grayImage, grayImage, 150, 255, CV_THRESH_BINARY);  //this is no need, already thresholded
        /**
         * *****************************************************************************
         */
        /*
         cvCreateMemStorage(int i)
         Creates a memory storage and returns pointer to it. 
         Initially the storage is empty. All fields of the header, 
         except the block_size, are set to 0.
        
         i-Size of the storage blocks in bytes. 
         If it is 0, the block size is set to default value - currently it is 64K
         */
        mem = cvCreateMemStorage(0);

        cvFindContours(grayImage, mem, contours, sizeof(CvContour.class), CV_RETR_CCOMP, CV_CHAIN_APPROX_SIMPLE, cvPoint(0, 0));

        Random rand = new Random();
        for (ptr = contours; ptr != null; ptr = ptr.h_next()) {
            Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            CvScalar color = CV_RGB(randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue());
            cvDrawContours(image, ptr, color, CV_RGB(0, 0, 0), -1, CV_FILLED, 8, cvPoint(0, 0));
        }   
        
        cvSaveImage(targetPath, image);
    }

}
