/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_OTSU;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvThreshold;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import static org.opencv.highgui.Highgui.IMREAD_GRAYSCALE;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class ThresholdingXRay {

    public static void main(String args[]) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        String originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegionXRaY.bmp";
        
        Mat image = Highgui.imread(originalImagePath);
        
        List<MatOfPoint> contours = new ArrayList<>();
        
        if (image.empty() == true) {
            
        } else {
            
            Mat image_2 = new Mat();
            
            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            
            //getting threshold value
            File f = new File(originalImagePath);
            
            BufferedImage img = ImageIO.read(f);
            
            ConvertRGBImageToGrayScale c = new ConvertRGBImageToGrayScale();
            
            BufferedImage img2 = c.convertToGrayscale(img);
            
            int threshold = ThresholdingImage.autoThreshold(img2);
            
            Imgproc.threshold(image_2, image_2,threshold, 255, CV_THRESH_BINARY);
            
            findContours(image_2, contours, new Mat(), Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
            
            for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(image, contours, i, new Scalar(240, 0, 0), 2);
                String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetectionXRay-"+i+".bmp";
                Highgui.imwrite(path, image);
            }

        }
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetectionXRAY.bmp", image);
        
    }
}
