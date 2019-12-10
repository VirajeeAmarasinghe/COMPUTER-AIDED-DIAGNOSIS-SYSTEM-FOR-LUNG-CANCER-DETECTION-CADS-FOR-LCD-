/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class MedianFilter {

    public MedianFilter() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }    

    //http://opencvexamples.blogspot.com/2013/10/applying-median-filter.html
    //http://www.bogotobogo.com/OpenCV/opencv_3_tutorial_imgproc_gausian_median_blur_bilateral_filter_image_smoothing_B.php
    //input image should be a grayscale one
    public void medianFiltering(String inputPath, String outputPath) {
        
        Mat source = Highgui.imread(inputPath);
        Mat medianFilteredImage = new Mat();

        Imgproc.medianBlur(source, medianFilteredImage, 1);

        Highgui.imwrite(outputPath, medianFilteredImage);

    }
}
