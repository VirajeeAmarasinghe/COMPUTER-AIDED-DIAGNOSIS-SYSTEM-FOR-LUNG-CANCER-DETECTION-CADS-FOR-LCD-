/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_OTSU;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
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
//links-http://docs.opencv.org/trunk/d7/d4d/tutorial_py_thresholding.html
public class LungSegmentation {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Otsu Binarization
        String originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\NonLinearFiltering.bmp";

        Mat image = Highgui.imread(originalImagePath);

        Mat grayImage = new Mat();

        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        Mat thresholdedImage = new Mat();

        Imgproc.threshold(grayImage, thresholdedImage, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU);        

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\OtsuBinarized.bmp", thresholdedImage);

        //Morphological open to clear borders of image to isolate lung area from borders of the image
        Mat morphologicalImage = new Mat(thresholdedImage.rows(), thresholdedImage.cols(), thresholdedImage.type());

        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(9, 9)); //11x11

        Imgproc.morphologyEx(thresholdedImage, morphologicalImage, Imgproc.MORPH_OPEN, element);

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\MorphologyOpen2.bmp", morphologicalImage);

        //connected components
        List<MatOfPoint> contours = new ArrayList<>();

        findContours(morphologicalImage, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        Mat originalImage = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ContrastLimitedAdaptiveHistogramEqualized.bmp");

        double areaArray[] = new double[contours.size()];

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(originalImage, contours, i, new Scalar(240, 0, 0), 2);
            areaArray[i] = contourArea(contours.get(i));
        }

        String path = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ConnectedComponents.bmp";
        Highgui.imwrite(path, originalImage);
        
        //largest connected components

        double max1 = 0;
        double max1Index = 0;

        for (int j = 0; j < areaArray.length; j++) {
            if (max1 < areaArray[j]) {
                max1 = areaArray[j];
                max1Index = j;
            }
        }

        double max2 = 0;
        double max2Index = 0;

        for (int j = 0; j < areaArray.length; j++) {
            if (max1Index == j) {
                continue;
            } else {
                if (max2 < areaArray[j]) {
                    max2 = areaArray[j];
                    max2Index = j;
                }
            }
        }

        double max3 = 0;
        double max3Index = 0;

        for (int k = 0; k < areaArray.length; k++) {
            if (max1Index == k || max2Index == k) {
                continue;
            } else {
                if (max3 < areaArray[k]) {
                    max3 = areaArray[k];
                    max3Index = k;
                }
            }
        }

        Mat originalImage2 = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ContrastLimitedAdaptiveHistogramEqualized.bmp");
        Mat largestContour=new Mat(originalImage2.rows(),originalImage2.cols(),originalImage2.type());
        
        for (int i = 0; i < contours.size(); i++) {
            if (i == max2Index || i == max3Index) {
                Imgproc.drawContours(originalImage2, contours, i, new Scalar(240, 0, 0), 2);
                Imgproc.drawContours(largestContour, contours, i, new Scalar(255, 255, 255), CV_FILLED);
            }
        }

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\InitialLung.bmp", originalImage2);
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\InitialLung2.bmp",largestContour);
        
        //again morphological open
        
        Mat m=Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\InitialLung2.bmp");        
       
        Mat morphologicalImage2 = new Mat(thresholdedImage.rows(), thresholdedImage.cols(), thresholdedImage.type());
        
        Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(40, 40)); //45x45
        
        Imgproc.morphologyEx(m, morphologicalImage2, Imgproc.MORPH_OPEN, element2);

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\Morphology3.bmp", morphologicalImage2);
        
        //convex hull
        Mat m2=Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\Morphology3.bmp",0);
        
        Mat thresholdedM2=new Mat();
        Imgproc.threshold(m2, thresholdedM2, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU);        
        
        List<MatOfPoint> contours2 = new ArrayList<>();

        findContours(thresholdedM2, contours2, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);        
       
        //        
        List<MatOfInt> hull = new ArrayList<>();
        for (int i = 0; i < contours2.size(); i++) {
            hull.add(new MatOfInt());
        }
        for (int i = 0; i < contours2.size(); i++) {
            Imgproc.convexHull(contours2.get(i), hull.get(i));
        }

        // Convert MatOfInt to MatOfPoint for drawing convex hull
        // Loop over all contours
        List<Point[]> hullpoints = new ArrayList<>();
        for (int i = 0; i < hull.size(); i++) {
            Point[] points = new Point[hull.get(i).rows()];

            // Loop over all points that need to be hulled in current contour
            for (int j = 0; j < hull.get(i).rows(); j++) {
                int index = (int) hull.get(i).get(j, 0)[0];
                points[j] = new Point(contours2.get(i).get(index, 0)[0], contours2.get(i).get(index, 0)[1]);
            }

            hullpoints.add(points);
        }

        // Convert Point arrays into MatOfPoint
        List<MatOfPoint> hullmop = new ArrayList<>();
        for (int i = 0; i < hullpoints.size(); i++) {
            MatOfPoint mop = new MatOfPoint();
            mop.fromArray(hullpoints.get(i));
            hullmop.add(mop);
        }        

        // Draw contours + hull results
        Mat originalImage3 = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\Morphology3.bmp");
        Scalar color = new Scalar(255, 255, 255);   // Green
        for (int i = 0; i < contours2.size(); i++) {            
            Imgproc.drawContours(originalImage3, hullmop, i, color,CV_FILLED);
        }       
     

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ConvexHull.bmp", originalImage3);
        
        Mat mm=Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ContrastLimitedAdaptiveHistogramEqualized.bmp");
        //Mat subtraction=new Mat();
        Mat subtraction2=new Mat();
        //Core.subtract(originalImage3, mm,subtraction);
        mm.copyTo(subtraction2, originalImage3);
        
        //Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\extractedLungXRay1.bmp", subtraction);
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\extractedLungXRay2.bmp", subtraction2);
        
        //otsu thresholding
        
        Mat grayImage2 = new Mat();

        Imgproc.cvtColor(subtraction2, grayImage2, Imgproc.COLOR_BGR2GRAY);

        Mat thresholdedImage2 = new Mat();

        //Imgproc.threshold(grayImage2, thresholdedImage2, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU); 
        Imgproc.threshold(grayImage2, thresholdedImage2, 50, 255, CV_THRESH_BINARY); 

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\OtsuBinarized2.bmp", thresholdedImage2);
        
        //morphological dialtion
        Mat finalLung=new Mat();
        Mat element3 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(1,1));
         
         Imgproc.dilate(thresholdedImage2, finalLung, element3);
         
         Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\FinalLung.bmp", finalLung);
    }
}
