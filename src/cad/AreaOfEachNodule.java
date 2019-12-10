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
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class AreaOfEachNodule {

    public AreaOfEachNodule() {
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

            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            for (int i = 0; i < contours.size(); i++) {

                //Calculates a contour area
                //for more details->http://docs.opencv.org/java/2.4.9/org/opencv/imgproc/Imgproc.html#contourArea(org.opencv.core.Mat)
                System.out.println("Contour-" + i + "=" + contourArea(contours.get(i)));
            }

        }
    }

    public double[] calculateNoduleArea(List<MatOfPoint> contours) {
        double[] areaArray = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");

        for (int i = 0; i < contours.size(); i++) {
            if (Double.isNaN(contourArea(contours.get(i)))) {
                areaArray[i] = 0; System.out.println("Contour-"+i+"="+areaArray[i]);
            } else {
                double contourArea = contourArea(contours.get(i));
                double formattedContourArea = Double.parseDouble(df.format(contourArea)); 
                areaArray[i] = formattedContourArea; System.out.println("Contour-"+i+"="+areaArray[i]);
            }
        }
        return areaArray;
    }
    
    public ArrayList<Integer> getNaNIndices(List<MatOfPoint> contours) {
        ArrayList<Integer> indices=new ArrayList<>();        

        for (int i = 0; i < contours.size(); i++) {
            if (Double.isNaN(contourArea(contours.get(i)))) {
                indices.add(i);
            } 
        }
        return indices;
    }
}
