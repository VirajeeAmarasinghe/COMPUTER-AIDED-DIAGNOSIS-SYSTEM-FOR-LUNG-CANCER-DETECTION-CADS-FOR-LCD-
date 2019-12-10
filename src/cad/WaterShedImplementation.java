/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
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
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */

//link-https://stackoverflow.com/questions/11294859/how-to-define-the-markers-for-watershed-in-opencv

public class WaterShedImplementation {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String sourcePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\MedianFilter.bmp";

        opencv_core.IplImage image = cvLoadImage(sourcePath);

        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

        //convert to greyImage
        cvCvtColor(image, grayImage, CV_BGR2GRAY);

        //threshold
        //Otsu's binarization, so it would find the best threshold value
        //cvThreshold(grayImage, grayImage, 0, 255, CV_THRESH_BINARY+CV_THRESH_OTSU);  //original code
        cvThreshold(grayImage, grayImage,124, 255, CV_THRESH_BINARY);

        String destPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\OtsuBinarization.bmp";

        cvSaveImage(destPath, grayImage);

        opencv_core.IplImage erode = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        //erosion
        opencv_imgproc.cvErode(grayImage, erode, null, 0); //original code 2 not the 1

        String destPath2 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ErodedXRay.bmp";

        cvSaveImage(destPath2, erode);

        opencv_core.IplImage dilate = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        //erosion
        opencv_imgproc.cvDilate(grayImage, dilate, null, 3);  //original code 3

        String destPath3 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\DilatedXRay.bmp";

        cvSaveImage(destPath3, dilate);

        opencv_core.IplImage bg = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        cvThreshold(dilate, bg, 1, 128, 1);

        String destPath4 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\BG.bmp";

        cvSaveImage(destPath4, bg);

        opencv_core.IplImage marker = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

        opencv_core.cvAdd(erode, bg, marker, null);

        String destPath5 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Added.bmp";

        cvSaveImage(destPath5, marker);

        //convert to 32SC1 (32 bit signed single channel).
        opencv_core.IplImage marker32 = cvCreateImage(cvGetSize(image), opencv_core.IPL_DEPTH_32S, 1);

        //cvConvertScale(marker,marker32,1.0/255.0,0.0);
        //cvConvertScale(marker,marker32,1.0/255.,0.0);
        cvConvertScale(marker, marker32, 1, 0);

        //apply watershed
        cvWatershed(image, marker32);

        //convert result back into uint8 image
        opencv_core.IplImage marker8U = cvCreateImage(cvGetSize(image), opencv_core.IPL_DEPTH_8U, 1);
        cvConvertScale(marker32, marker8U, 1, 0);

        String destPath6 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Marker8U.bmp";
        cvSaveImage(destPath6, marker8U);
        //threshold
        cvThreshold(marker8U, marker8U, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU);
        String destPath7 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\FinalThresholded.bmp";
        cvSaveImage(destPath7, marker8U);
        opencv_core.IplImage lung = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
        System.out.println(image.depth() + " " + marker32.depth() + " " + marker8U.depth());
        System.out.println(image.nChannels() + " " + marker32.nChannels() + " " + marker8U.nChannels());
        //opencv_core.cvSub(image, marker8U, lung, null);
        //opencv_core.cvSub(image, marker8U, lung, null);
        //String destPath8="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\XRayLung.bmp";
        //cvSaveImage(destPath8,lung);

        //contours
        Mat Cimage = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Marker8U.bmp");

        if (Cimage.empty() == true) {
            System.out.println("Error: no image found!");
        } else {

            List<MatOfPoint> contours = new ArrayList<>();
            Mat image_2 = new Mat();

            //image.convertTo(image_2, CvType.CV_32SC1);
            Imgproc.cvtColor(Cimage, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            double[] contourArea = new double[contours.size()];
            for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(Cimage, contours, i, new Scalar(240, 0, 0), 2);
                double area = contourArea(contours.get(i));
                contourArea[i] = area;
                String path = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetectionXRay-" + i + ".bmp";
                Highgui.imwrite(path, Cimage);
            }

            double max1 = 0;
            double max1Index = 0;

            for (int j = 0; j < contourArea.length; j++) {
                if (max1 < contourArea[j]) {
                    max1 = contourArea[j];
                    max1Index = j;
                }
            }

            double max2 = 0;
            double max2Index = 0;

            for (int j = 0; j < contourArea.length; j++) {
                if (max1Index == j) {
                    continue;
                } else {
                    if (max2 < contourArea[j]) {
                        max2 = contourArea[j];
                        max2Index = j;
                    }
                }
            }

                double max3 = 0;
                double max3Index = 0;

                for (int k = 0; k < contourArea.length; k++) {
                    if (max1Index == k || max2Index == k) {
                        continue;
                    } else {
                        if (max3 < contourArea[k]) {
                            max3 = contourArea[k];
                            max3Index = k;
                        }
                    }
                }
                
                Mat m=new Mat(Cimage.rows(),Cimage.cols(),Cimage.type());

                for (int i = 0; i < contours.size(); i++) {
                    if (i == max2Index || i == max3Index) {
                        Imgproc.drawContours(m, contours, i, new Scalar(255, 255, 255), CV_FILLED);
                    } 

                }

            
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetectionXRay.bmp", m);

        }
        
        //morphology closing
        
        
        try {          
            
            Mat source = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetectionXRay.bmp", Highgui.CV_LOAD_IMAGE_UNCHANGED);
            if (source != null) {                
                Mat destination = new Mat(source.rows(), source.cols(), source.type());

                destination = source;

                /*
                 public static Mat getStructuringElement(int shape, Size ksize)
                 shape-Element shape that could be one of the following:
                 MORPH_RECT - a rectangular structuring element
                 MORPH_ELLIPSE-an elliptic structuring element, that is, a filled ellipse inscribed into the rectangle Rect(0, 0, esize.width, 0.esize.height)
                 MORPH_CROSS - a cross-shaped structuring element
                 CV_SHAPE_CUSTOM - custom structuring element (OpenCV 1.x API)
                 ksize-Size of the structuring element.
                 Returns a structuring element of the specified size and shape for morphological operations.
                 The function constructs and returns the structuring element that can be further passed to 
                 "createMorphologyFilter", "erode", "dilate" or "morphologyEx".
                 */
                Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(21, 21));

                /*
                 public static void morphologyEx(Mat src, Mat dst, int op, Mat kernel)
                 src-Source (input) image
                 The number of channels can be arbitrary. 
                 The depth should be one of CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
                 dst-Destination image of the same size and type as src
                 op-The kind of morphology transformation to be performed
                 Closing: MORPH_CLOSE: 3
                 kernel-The kernel to be used/structuring element
                 */
                /*
                 morphology closing-dilation of an image followed by an erosion
                 useful to remove small holes (dark regions)or small black points on the object. 
                 */
                Imgproc.morphologyEx(source, destination, Imgproc.MORPH_CLOSE, element);

                //destination.convertTo(destination, CvType.CV_8U);           
                Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp", destination);

            } 

        } catch (Exception e) {
            
        }
        
        
        
        try {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat originalImage = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\MedianFilter.bmp");
            Mat maskImage = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp");

            if (originalImage == null || maskImage == null) {
                
            } else {
                Mat difference = new Mat();

            //subtract(Mat src1,Mat src2,Mat dst)
                //Calculates the per-element difference between two arrays or array and a scalar
                //src1-First source array or a scalar
                //src2-Second source array or a scalar
                //dst-Destination array of the same size and the same number of channels as the input array
                Core.subtract(maskImage, originalImage, difference);

                Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegionXRAY.bmp", difference);

            }
            
        } catch (Exception e) {
            
        }
    }

    }
