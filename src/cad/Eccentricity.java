/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.findContours;
import org.opencv.imgproc.Moments;

//link-http://answers.opencv.org/question/44331/eccentricity-elongation-of-contours/
public class Eccentricity {

    public Eccentricity() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp");

        if (image.empty() == true) {
            System.out.println("Error: no image found!");
        } else {

            List<MatOfPoint> contours = new ArrayList<>();
            Mat image_2 = new Mat();

            //image.convertTo(image_2, CvType.CV_32SC1);
            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(image, contours, -1, new Scalar(240, 0, 0), 2);

                Moments m = Imgproc.moments(contours.get(i));
                Eccentricity e = new Eccentricity();
                double eccentricity = e.getEccentricityFormulaValue(m);

                //System.out.println("Contour-" + i + " Eccentricity=" + eccentricity);
                System.out.println("Contour-" + i + "=" + eccentricity);
            }
        }
    }

    public double getEccentricityFormulaValue(Moments mu) {
        double bigSqrt = sqrt((mu.get_mu20() - mu.get_m02()) * (mu.get_mu20() - mu.get_m02()) + 4 * mu.get_m11() * mu.get_m11());
        return (double) (mu.get_m20() + mu.get_m02() + bigSqrt) / (mu.get_m20() + mu.get_m02() - bigSqrt);
    }

    public double[] getEccentricity(List<MatOfPoint> contours) {
        double[] ecc = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i = 0; i < contours.size(); i++) {

            Moments m = Imgproc.moments(contours.get(i));
            Eccentricity e = new Eccentricity();
            if (Double.isNaN(e.getEccentricityFormulaValue(m))) {
                ecc[i] = 0; System.out.println("Contour-"+i+"="+ecc[i]);
            } else {
                double eccentricity = e.getEccentricityFormulaValue(m);
                ecc[i] = Double.parseDouble(df.format(eccentricity));System.out.println("Contour-"+i+"="+ecc[i]);
            }
        }
        return ecc;
    }

    public ArrayList<Integer> getNaNIndices(List<MatOfPoint> contours) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < contours.size(); i++) {
            Moments m = Imgproc.moments(contours.get(i));
            Eccentricity e = new Eccentricity();
            if (Double.isNaN(e.getEccentricityFormulaValue(m))) {
                indices.add(i);
            }
        }
        return indices;
    }
}
