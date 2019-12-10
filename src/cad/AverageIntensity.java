/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_core.CV_8U;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import static org.opencv.core.Core.mean;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class AverageIntensity {

    public AverageIntensity() {
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

            for (int i = 0; i < contours.size(); i++) {

                /* calculating average intensity */
                // First, make a mask image of each contour
                Mat mask = new Mat(image_2.cols(), image_2.rows(), CV_8U, new Scalar(0));

                drawContours(mask, contours, i, new Scalar(255), CV_FILLED);
                //If want to save contours one by one as differenct images
                //String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\debug_image_testing"+i+".bmp";
                //Highgui.imwrite(path, mask); 
                // Second, calculate average brightness with mask

                /*new cvScalar(blue, green, red, unused)
                 CvScalar CV_RGB(double r, double g, double b)
                 */
                /*https://stackoverflow.com/questions/35867301/how-to-filter-contour-by-brightness/35888819*/
                Scalar m = mean(image, mask);

                //System.out.println("Average Intensity of Contour-" +i+"="+ m.val[0]);
                System.out.println("Contour-" + i + "=" + m.val[0]);
            }

        }
    }

    public double[] calculateAverageIntensity(List<MatOfPoint> contours, Mat image) {
        double[] avgInt = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i = 0; i < contours.size(); i++) {

            /* calculating average intensity */
            // First, make a mask image of each contour
            Mat mask = new Mat(image.cols(), image.rows(), CV_8U, new Scalar(0));

            drawContours(mask, contours, i, new Scalar(255), CV_FILLED);
            //If want to save contours one by one as differenct images
            //String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\debug_image_testing.bmp";
            //Highgui.imwrite(path, mask); 
            // Second, calculate average brightness with mask

            /*new cvScalar(blue, green, red, unused)
             CvScalar CV_RGB(double r, double g, double b)
             */
            /*https://stackoverflow.com/questions/35867301/how-to-filter-contour-by-brightness/35888819*/
            Scalar m = mean(image, mask);
            if (Double.isNaN(m.val[0])) {
                avgInt[i] = 0; 
            } else {
                avgInt[i] = Double.parseDouble(df.format(m.val[0])); 
            }
        }
        return avgInt;
    }

    public ArrayList<Integer> getNanIndices(List<MatOfPoint> contours, Mat image) {
        ArrayList<Integer> nanIndices = new ArrayList<>();

        for (int i = 0; i < contours.size(); i++) {

            Mat mask = new Mat(image.cols(), image.rows(), CV_8U, new Scalar(0));

            drawContours(mask, contours, i, new Scalar(255), CV_FILLED);

            Scalar m = mean(image, mask);
            if (Double.isNaN(m.val[0])) {
                nanIndices.add(i);
            }
        }
        return nanIndices;
    }
}
