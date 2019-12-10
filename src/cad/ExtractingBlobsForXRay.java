/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static cad.UploadDicomImage.lbl_result_display;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RETR_CCOMP;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
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
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class ExtractingBlobsForXRay {

    public ExtractingBlobsForXRay() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void ExtractingBlobs() {
        try {
            String originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary.bmp";

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

                double[] contourAreaArray = new double[contours.size()];
                for (int i = 0; i < contours.size(); i++) {
                    double contourArea = contourArea(contours.get(i));
                    contourAreaArray[i] = contourArea;
                }

                double max1 = 0;
                int max1Index = 0;

                for (int j = 0; j < contourAreaArray.length; j++) {
                    if (contourAreaArray[j] > max1) {
                        max1 = contourAreaArray[j];
                        max1Index = j;
                    }
                }

                double max2 = 0;
                int max2Index = 0;

                for (int k = 0; k < contourAreaArray.length; k++) {
                    if (max1Index == k) {
                        continue;
                    } else {
                        if (max2 < contourAreaArray[k]) {
                            max2 = contourAreaArray[k];
                            max2Index = k;
                        }
                    }
                }
                
                for(int l=0;l<contours.size();l++){
                    //if(l==max1Index || l==max2Index){
                        Imgproc.drawContours(image, contours, l, new Scalar(240, 0, 0), 2);                         
                    //}else{
                       //Imgproc.drawContours(image, contours, l, new Scalar(0, 0, 0), 2);  
                    //}
                }

            }
            Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMaskForXRay.bmp", image);

        } catch (IOException e) {

        }
    }

}
