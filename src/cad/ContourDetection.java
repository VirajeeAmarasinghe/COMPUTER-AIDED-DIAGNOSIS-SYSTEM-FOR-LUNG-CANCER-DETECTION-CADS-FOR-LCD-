/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static cad.UploadDicomImage.lbl_result_display;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
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
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class ContourDetection {

    public ContourDetection() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public List<MatOfPoint> detectContours() {

        try {
            String originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp";

            Mat image = Highgui.imread(originalImagePath);

            List<MatOfPoint> contours = new ArrayList<>();

            if (image.empty() == true) {
                String text = lbl_result_display.getText() + "<html><p style='margin-left:10px;margin-top:10px;'>Error: no image found!</p>";
                lbl_result_display.setText(text);
            } else {

                Mat image_2 = new Mat();

                Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);

                //getting threshold value
                File f = new File(originalImagePath);

                BufferedImage img = ImageIO.read(f);

                ConvertRGBImageToGrayScale c = new ConvertRGBImageToGrayScale();

                BufferedImage img2 = c.convertToGrayscale(img);

                int threshold = ThresholdingImage.autoThreshold(img2);

                Imgproc.threshold(image_2, image_2, threshold, 255, CV_THRESH_BINARY);

                findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                for (int i = 0; i < contours.size(); i++) {
                    Imgproc.drawContours(image, contours, i, new Scalar(240, 0, 0), 2);
                    String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetection-"+i+".bmp";
                    Highgui.imwrite(path, image);
                }

            }
            Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetection.bmp", image); 
            return contours;
        } catch (IOException e) {
            return null;
        }

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
            }

        }
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetection.bmp", image); // DEBUG
    }

}
