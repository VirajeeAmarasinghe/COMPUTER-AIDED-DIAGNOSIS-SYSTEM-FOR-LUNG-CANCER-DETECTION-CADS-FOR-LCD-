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
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
//links-http://book2s.com/java/api/org/opencv/core/matofpoint/toarray-0.html
public class FindMajorAndMinorAxisLength {

    static RotatedRect r;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp");

        if (image.empty() == true) {
            System.out.println("Error: no image found!");
        } else {

            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Mat image_2 = new Mat();

            //image.convertTo(image_2, CvType.CV_32SC1);
            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            for (int i = 0; i < contours.size(); i++) {
                //Contour must have at least 5 points for fitEllipse
                if (contours.get(i).toArray().length < 5) {
                    System.out.println("Contour must have at least 5 points for fitEllipse");
                } else {
                    //Copy MatOfPoint to MatOfPoint2f
                    MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
                    //Fit an ellipse to the current contour
                    r = Imgproc.fitEllipse(matOfPoint2f);

                    //The size-member contains the length of you major and minor axis.
                    System.out.println("Contour-" + i + " Major Axis Length=" + r.size.height + " Minor Axis Length=" + r.size.width);

                }
                //draw the enclosing ellipse                               
                Core.ellipse(image, r, new Scalar(255, 0, 0), 1);
            }

            String path = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Ellipse.bmp";
            Highgui.imwrite(path, image);

        }
    }

    public double[] calculateMajorAxisLength(List<MatOfPoint> contours) {
        RotatedRect r;
        double[] majorArray = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");

        for (int i = 0; i < contours.size(); i++) {
            //Contour must have at least 5 points for fitEllipse
            if (contours.get(i).toArray().length < 5) {
                majorArray[i] = 0;   System.out.println("Contour-"+i+"="+majorArray[i]);
            } else {
                //Copy MatOfPoint to MatOfPoint2f
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
                //Fit an ellipse to the current contour
                r = Imgproc.fitEllipse(matOfPoint2f);

                //The size-member contains the length of you major and minor axis.
                if (Double.isNaN(r.size.height)) {
                    majorArray[i] = 0;  System.out.println("Contour-"+i+"="+majorArray[i]);
                } else {
                    majorArray[i] = Double.parseDouble(df.format(r.size.height)); System.out.println("Contour-"+i+"="+majorArray[i]);
                }
            }
        }
        return majorArray;
    }

    public ArrayList<Integer> GetNaNIndicesMajorAxisLength(List<MatOfPoint> contours) {
        RotatedRect r;
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < contours.size(); i++) {
            //Contour must have at least 5 points for fitEllipse
            if (contours.get(i).toArray().length < 5) {
                indices.add(i);
            } else {
                //Copy MatOfPoint to MatOfPoint2f
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
                //Fit an ellipse to the current contour
                r = Imgproc.fitEllipse(matOfPoint2f);

                //The size-member contains the length of you major and minor axis.
                if (Double.isNaN(r.size.height)) {
                    indices.add(i);
                }
            }
        }
        return indices;
    }

    public double[] calculateMinorAxisLength(List<MatOfPoint> contours) {
        RotatedRect r;
        double[] minorArray = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");

        for (int i = 0; i < contours.size(); i++) {
            //Contour must have at least 5 points for fitEllipse
            if (contours.get(i).toArray().length < 5) {
                minorArray[i] = 0;   System.out.println("Contour-"+i+"="+minorArray[i]);
            } else {
                //Copy MatOfPoint to MatOfPoint2f
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
                //Fit an ellipse to the current contour
                r = Imgproc.fitEllipse(matOfPoint2f);

                //The size-member contains the length of you major and minor axis.
                if (Double.isNaN(r.size.width)) {
                    minorArray[i] = 0; System.out.println("Contour-"+i+"="+minorArray[i]);
                } else {
                    minorArray[i] = Double.parseDouble(df.format(r.size.width)); System.out.println("Contour-"+i+"="+minorArray[i]);
                }
            }
        }
        return minorArray;
    }

    public ArrayList<Integer> GetNaNIndicesMinorAxisLength(List<MatOfPoint> contours) {
        RotatedRect r;
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < contours.size(); i++) {
            if (contours.get(i).toArray().length < 5) {
                indices.add(i);
            } else {
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());

                r = Imgproc.fitEllipse(matOfPoint2f);

                if (Double.isNaN(r.size.width)) {
                    indices.add(i);
                }
            }
        }
        return indices;
    }
}
