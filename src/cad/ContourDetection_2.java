/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacpp.Loader.sizeof;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
import static com.googlecode.javacv.cpp.opencv_core.CV_WHOLE_SEQ;
import static com.googlecode.javacv.cpp.opencv_core.CvScalar.BLUE;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvDrawContours;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import com.googlecode.javacv.cpp.opencv_imgproc;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RETR_LIST;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindContours;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvThreshold;
import java.util.Random;

/**
 *
 * @author virajee
 */
public class ContourDetection_2 {

    public static void main(String[] args) {
        String sourcePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp";
        String targetPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\testContourDetection_2.bmp";

        opencv_core.IplImage image = cvLoadImage(sourcePath);
        /*
         cvCreateImage(CvSize size,int depth,int channels)
         size (CvSize)-Image width and height
         depth(int)-Bit depth of image elements
         channels (Int32)-Number of channels per element(pixel). Can be 1, 2, 3 or 4. The channels are interleaved, for example the usual data layout of a color image is: b0 g0 r0 b1 g1 r1 ... 
         */
        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

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
        opencv_core.CvMemStorage mem;
        opencv_core.CvSeq contours = new opencv_core.CvSeq();
        opencv_core.CvSeq ptr = new opencv_core.CvSeq();

        /*
         cvThreshold(opencv_core.CvArr cvarr, opencv_core.CvArr cvarr1, double d, double d1, int i)
         cvarr-Source array (single-channel, 8-bit of 32-bit floating point).\
         cvarr1-Destination array; must be either the same type as src or 8-bit
         d-Threshold value
         d1-Maximum value to use with CV_THRESH_BINARY and CV_THRESH_BINARY_INV thresholding types
         i-Thresholding type 
         */
        cvThreshold(grayImage, grayImage, 150, 255, CV_THRESH_BINARY);  //this is no need, already thresholded
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

        /*
         http://docs.opencv.org/2.4/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html?highlight=#cv2.findContours
                        
         */
        cvFindContours(grayImage, mem, contours, sizeof(opencv_core.CvContour.class), CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE, cvPoint(0, 0));

        Random rand = new Random();
        for (ptr = contours; ptr != null; ptr = ptr.h_next()) {
            //Color randomColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            //opencv_core.CvScalar color = CV_RGB(randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue());

            //cvDrawContours(IntPtr img,IntPtr contour,MCvScalar externalColor,MCvScalar holeColor,int maxLevel,int thickness,LINE_TYPE lineType,Point offset)
            //img-Image where the contours are to be drawn. Like in any other drawing function, the contours are clipped with the ROI
            //contour-Pointer to the first contour
            //externalColor-Color of the external contours
            //holeColor-Color of internal contours 
            //maxLevel-Maximal level for drawn contours. If 0, only contour is drawn. If 1, the contour and all contours after it on the same level are drawn. If 2, all contours after and all contours one level below the contours are drawn, etc. If the value is negative, the function does not draw the contours following after contour but draws child contours of contour up to abs(maxLevel)-1 level. 
            //thickness-Thickness of lines the contours are drawn with. If it is negative the contour interiors are drawn
            //lineType-Type of the contour segments
            //         enum LINE_TYPE
            //         EIGHT_CONNECTED-8-connected
            //         FOUR_CONNECTED-4-connected
            //         CV_AA-Antialias
            //offset-Shift all the point coordinates by the specified value. It is useful in case if the contours retrived in some image ROI and then the ROI offset needs to be taken into account during the rendering. 
            cvDrawContours(image, ptr, BLUE, CV_RGB(240, 0, 0), -1, 2, 8, cvPoint(0, 0));

            /*
             static double cvContourArea(IntPtr contour,MCvSlice slice,int oriented)
            
             contour-sequence or array of vertices
             slice-Starting and ending points of the contour section of interest, by default area of the whole contour is calculated
             oriented-If zero, the absolute area will be returned. Otherwise the returned value mighted be negative
            
             return-The area of the whole contour or contour section
            
             http://www.swarthmore.edu/NatSci/mzucker1/opencv-2.4.10-docs/modules/imgproc/doc/structural_analysis_and_shape_descriptors.html
             http://www.emgu.com/wiki/files/2.4.2/document/html/9e8689ba-7c6f-906d-f1c1-668d78d2826b.htm
             */
            double numOfPixels_contourArea = opencv_imgproc.cvContourArea(ptr, CV_WHOLE_SEQ, 0);
        
        }

        cvSaveImage(targetPath, image);
    }
}
