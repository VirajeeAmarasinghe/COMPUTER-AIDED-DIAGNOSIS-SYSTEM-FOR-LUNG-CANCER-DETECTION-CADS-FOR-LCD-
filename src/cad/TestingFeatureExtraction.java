/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

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
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class TestingFeatureExtraction {

    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String originalImagePath="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp";
        Mat image = Highgui.imread(originalImagePath);

        if (image.empty() == true) {
            System.out.println("Error: no image found!");
        } else {

            List<MatOfPoint> contours = new ArrayList<>();
            Mat image_2 = new Mat();

            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            
            //getting threshold value
            
            File f=new File(originalImagePath);
            
            BufferedImage img=ImageIO.read(f);
            
            ConvertRGBImageToGrayScale c = new ConvertRGBImageToGrayScale();
            
            BufferedImage img2 = c.convertToGrayscale(img);
            
            
            
            int threshold = ThresholdingImage.autoThreshold(img2);
            
            Imgproc.threshold(image_2, image_2, threshold, 255, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            System.out.println("Area of Contour");

            AreaOfEachNodule a = new AreaOfEachNodule();
            a.calculateNoduleArea(contours);

            System.out.println("Average Intensity Of Contour");

            AverageIntensity ai = new AverageIntensity();
            ai.calculateAverageIntensity(contours, image_2);

            System.out.println("Ratio Of Object's Pixels To Its BoundingBox (x100)");

            DrawingBoundingBox d = new DrawingBoundingBox();
            //d.calculateRatioOfObjectsPixelsToItsBoundingBox(contours, image);

            System.out.println("Eccentricity of Contour");

            Eccentricity e = new Eccentricity();
            e.getEccentricity(contours);

            System.out.println("Solidity of Contour");

            Solidity s = new Solidity();
            s.calculateSolidity(contours,image_2);

            System.out.println("Major Axis Length");

            FindMajorAndMinorAxisLength f1 = new FindMajorAndMinorAxisLength();
            f1.calculateMajorAxisLength(contours);

            System.out.println("Minor Axis Length");

            FindMajorAndMinorAxisLength f2 = new FindMajorAndMinorAxisLength();
            f2.calculateMinorAxisLength(contours);

            //drawing
            for (int i = 0; i < contours.size(); i++) {
                Imgproc.drawContours(image, contours, i, new Scalar(240, 0, 0), 2);

                String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetectionFeatureExtraction-"+i+".bmp";
                Highgui.imwrite(path, image); 
            }
        }
    }
}


