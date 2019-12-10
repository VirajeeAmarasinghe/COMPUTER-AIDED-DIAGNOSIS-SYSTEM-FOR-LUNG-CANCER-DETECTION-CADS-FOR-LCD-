/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import static com.googlecode.javacv.cpp.opencv_core.CV_8U;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_OTSU;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class ContourDetectionXRay {

    public ContourDetectionXRay() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public List<MatOfPoint> detectContours(String inputPath,String outputPath){
       Mat difference=Highgui.imread(inputPath);
       
       List<MatOfPoint> contours = new ArrayList<>();
       Mat image_2 = new Mat();
       
       Imgproc.cvtColor(difference, image_2, Imgproc.COLOR_BGR2GRAY);
       Imgproc.threshold(image_2, image_2, 0, 255, CV_THRESH_BINARY+CV_THRESH_OTSU);
       
       findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
       
       for (int i = 0; i < contours.size(); i++) {
          Mat mask = new Mat(image_2.cols(), image_2.rows(), CV_8U, new Scalar(0));
          Imgproc.drawContours(image_2, contours, i, new Scalar(240, 0, 0), 2);
       }
        Highgui.imwrite(outputPath, image_2);
       return contours;
    }
    
}
