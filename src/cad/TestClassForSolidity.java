/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class TestClassForSolidity {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp");

        if (image.empty() == true) {
            System.out.println("Error: no image found!");
        } else {
            List<MatOfPoint> contours = new ArrayList<>();
            Mat image_2 = new Mat();

            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            // Find the convex hull
            List<MatOfInt> hull = new ArrayList<>();
            for (int i = 0; i < contours.size(); i++) {
                hull.add(new MatOfInt());
            }
            for (int i = 0; i < contours.size(); i++) {
                Imgproc.convexHull(contours.get(i), hull.get(i));
            }

            // Convert MatOfInt to MatOfPoint for drawing convex hull
            // Loop over all contours
            List<Point[]> hullpoints = new ArrayList<>();
            for (int i = 0; i < hull.size(); i++) {
                Point[] points = new Point[hull.get(i).rows()];

                // Loop over all points that need to be hulled in current contour
                for (int j = 0; j < hull.get(i).rows(); j++) {
                    int index = (int) hull.get(i).get(j, 0)[0];
                    points[j] = new Point(contours.get(i).get(index, 0)[0], contours.get(i).get(index, 0)[1]);
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

            for (int k = 0; k < hullmop.size(); k++) {
                double hullArea = contourArea(hullmop.get(k));
                double contourArea=contourArea(contours.get(k));
                double solidity=contourArea/hullArea;
                System.out.println("Contour-"+k+" Solidity="+solidity);
            }

         
            // Draw contours + hull results
            Mat overlay = new Mat(image_2.size(), CvType.CV_8UC3);
            Scalar color = new Scalar(0, 255, 0);   // Green
            for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(overlay, contours, i, color);
                Imgproc.drawContours(overlay, hullmop, i, color);
            }
            String path = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvexHullForContour.bmp";

            Highgui.imwrite(path, overlay);

        }

    }

}
