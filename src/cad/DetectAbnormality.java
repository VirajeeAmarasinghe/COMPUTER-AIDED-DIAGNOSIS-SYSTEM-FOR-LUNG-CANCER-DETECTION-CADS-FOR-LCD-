/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class DetectAbnormality {

    public DetectAbnormality() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void detectAbnormality(List<MatOfPoint> contours, double[] avgIntensities, double[] boundingBoxRatio, String sourceImagePath, String outputPath) {
        Mat source = Highgui.imread(sourceImagePath);
        double max = 0;
        int maxIndex = 0;
        for (int i = 0; i < avgIntensities.length; i++) {
            if (max < avgIntensities[i]) {
                max = avgIntensities[i];
                maxIndex = i;
            }
        }
        System.out.println(max + " " + boundingBoxRatio[maxIndex]);
        for (int j = 0; j < contours.size(); j++) {
            if (j == maxIndex) {
                if (max > 180) {
                    if (boundingBoxRatio[j] < 20) {
                        Imgproc.drawContours(source, contours, j, new Scalar(240, 0, 0), 20);
                    }
                } else if (max > 175) {
                    if (boundingBoxRatio[j] < 46) {
                        Imgproc.drawContours(source, contours, j, new Scalar(240, 0, 0), 20);
                    }
                } else if (max > 170) {
                    if (boundingBoxRatio[j] >45) {
                        Imgproc.drawContours(source, contours, j, new Scalar(240, 0, 0), 20);
                    }
                } else if (max > 165) {
                    if (boundingBoxRatio[j] > 34) {
                        Imgproc.drawContours(source, contours, j, new Scalar(240, 0, 0), 20);
                    }
                } else if (max > 160) {
                    if (boundingBoxRatio[j] > 24) {
                        Imgproc.drawContours(source, contours, j, new Scalar(240, 0, 0), 20);
                    }
                } else if (max < 150) {
                    if (boundingBoxRatio[j] > 20) {
                        Imgproc.drawContours(source, contours, j, new Scalar(240, 0, 0), 20);
                    }
                }

            }
        }

        Highgui.imwrite(outputPath, source);

    }
}
