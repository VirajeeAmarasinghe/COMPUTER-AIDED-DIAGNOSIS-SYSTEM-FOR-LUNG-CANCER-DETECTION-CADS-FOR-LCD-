/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class DrawingBoundingBox {

    public DrawingBoundingBox() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp");

        if (image.empty() == true) {
            System.out.println("Error: no image found!");
        } else {

            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Mat image_2 = new Mat();

            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            MatOfPoint2f approxCurve = new MatOfPoint2f();

            for (int i = 0; i < contours.size(); i++) {

                /* Drawing Bounding Box */
                //Convert contours(i) from MatOfPoint to MatOfPoint2f
                MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());

                //Processing on mMOP2f1 which is in type MatOfPoint2f
                double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;

                Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

                //Convert back to MatOfPoint
                MatOfPoint points = new MatOfPoint(approxCurve.toArray());

                // Get bounding rect of contour
                Rect rect = Imgproc.boundingRect(points);

                // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
                Core.rectangle(image, rect.tl(), rect.br(), new Scalar(255, 0, 0), 1, 8, 0);

                //calculations
                //System.out.println("Area(using area() method)=" + rect.area() + " WidthxHeight=" + (rect.width * rect.height));
                //System.out.println("Perimeter=" + ((2 * rect.width) + (2 * rect.height)));
                //System.out.println("Contour Area(Number of Pixels)=" + contourArea(contours.get(i)));
                //System.out.println("Contour-"+i+" (Contour Area/Bounding Box Area)=" + ((contourArea(contours.get(i))) / rect.area())*100);
                System.out.println("Contour-" + i + "=" + ((contourArea(contours.get(i))) / rect.area()) * 100);
                //System.out.println("(Contour Area/Bounding Box Perimeter)=" + ((contourArea(contours.get(i))) / ((2 * rect.width) + (2 * rect.height)))*100);
                //System.out.println();
            }
            String path = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\BoundngBox.bmp";
            Highgui.imwrite(path, image);

        }
    }

    public double[] calculateRatioOfObjectsPixelsToItsBoundingBox(List<MatOfPoint> contours, Mat image,String boundingBoxPath) {
        double[] r = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");

        MatOfPoint2f approxCurve = new MatOfPoint2f();

        for (int i = 0; i < contours.size(); i++) {

            /* Drawing Bounding Box */
            //Convert contours(i) from MatOfPoint to MatOfPoint2f
            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());

            //Processing on mMOP2f1 which is in type MatOfPoint2f
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;

            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            //Convert back to MatOfPoint
            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            // Get bounding rect of contour
            Rect rect = Imgproc.boundingRect(points);

            // draw enclosing rectangle (all same color, but you could use variable i to make them unique)
            Core.rectangle(image, rect.tl(), rect.br(), new Scalar(255, 0, 0), 1, 8, 0);

            //calculations    
            if (Double.isNaN(((contourArea(contours.get(i))) / rect.area()) * 100)) {
                r[i] = 0; 
            } else {
                double calculation = ((contourArea(contours.get(i))) / rect.area()) * 100;
                r[i] = Double.parseDouble(df.format(calculation)); 
            }
        }
        String path = boundingBoxPath;
        Highgui.imwrite(path, image);
        return r;
    }

    public ArrayList<Integer> getNaNIndices(List<MatOfPoint> contours) {
        ArrayList<Integer> nanIndices = new ArrayList<>();

        MatOfPoint2f approxCurve = new MatOfPoint2f();

        for (int i = 0; i < contours.size(); i++) {

            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());

            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;

            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

            MatOfPoint points = new MatOfPoint(approxCurve.toArray());

            Rect rect = Imgproc.boundingRect(points);

            //calculations    
            if (Double.isNaN(((contourArea(contours.get(i))) / rect.area()) * 100)) {
                nanIndices.add(i);
            }
        }
        return nanIndices;
    }

}
