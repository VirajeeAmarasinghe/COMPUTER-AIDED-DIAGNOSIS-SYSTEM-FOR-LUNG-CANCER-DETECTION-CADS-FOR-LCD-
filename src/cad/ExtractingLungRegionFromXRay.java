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
public class ExtractingLungRegionFromXRay {

    public ExtractingLungRegionFromXRay() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void getLungRegion(String inputPath, String initialLungMaskPath, String medianFilterPath, String completeLungMaskPath, String lungRegionPath) {
        
        //find contours
        Mat Cimage = Highgui.imread(inputPath);

        if (Cimage.empty() == true) {
            System.out.println("Error: no image found!");
        } else {
            List<MatOfPoint> contours = new ArrayList<>();
            Mat image_2 = new Mat();

            Imgproc.cvtColor(Cimage, image_2, Imgproc.COLOR_BGR2GRAY);

            Imgproc.threshold(image_2, image_2, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            double[] contourArea = new double[contours.size()];

            for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(Cimage, contours, i, new Scalar(240, 0, 0), 2);
                double area = contourArea(contours.get(i));
                contourArea[i] = area;
            }

            //get the 2nd and 3rd largest
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

            //draw 2nd and 3rd largest contours
            Mat m = new Mat(Cimage.rows(), Cimage.cols(), Cimage.type());

            for (int i = 0; i < contours.size(); i++) {
                if (i == max2Index || i == max3Index) {
                    Imgproc.drawContours(m, contours, i, new Scalar(255, 255, 255), CV_FILLED);
                }

            }
            Highgui.imwrite(initialLungMaskPath, m);

            //Morphology Closing
            try {

                Mat source = Highgui.imread(initialLungMaskPath, Highgui.CV_LOAD_IMAGE_UNCHANGED);
                if (source != null) {
                    Mat destination = new Mat(source.rows(), source.cols(), source.type());

                    destination = source;

                    Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(21, 21));

                    Imgproc.morphologyEx(source, destination, Imgproc.MORPH_CLOSE, element);

                    Highgui.imwrite(completeLungMaskPath, destination);

                }

                //extracting lung region
                Mat originalImage = Highgui.imread(medianFilterPath);
                Mat maskImage = Highgui.imread(completeLungMaskPath);

                if (originalImage == null || maskImage == null) {

                } else {
                    Mat difference = new Mat();
                    originalImage.copyTo(difference, maskImage);

                    //Core.subtract(maskImage, originalImage, difference);
                    Highgui.imwrite(lungRegionPath, difference);
                }

            } catch (Exception e) {
            }
        }
    }
}
