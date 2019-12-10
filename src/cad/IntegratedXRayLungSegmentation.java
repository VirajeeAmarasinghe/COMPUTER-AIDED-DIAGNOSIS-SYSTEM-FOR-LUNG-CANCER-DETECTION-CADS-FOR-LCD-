/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvConvertScale;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import com.googlecode.javacv.cpp.opencv_imgproc;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_OTSU;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvThreshold;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvWatershed;
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
public class IntegratedXRayLungSegmentation {

    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // resize to a fixed width (not proportional)
        String inputImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ConvertedBitMapXRay.bmp";
        String outputImagePath1 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ResizedBitMapXRay.bmp";

        int scaledWidth = 512;
        int scaledHeight = 512;
        ResizeImage.resizeWithImageJ(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);

        //grayscale conversion
        File f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ResizedBitMapXRay.bmp");
        ConvertRGBImageToGrayScale c = new ConvertRGBImageToGrayScale();
        BufferedImage img = c.readImage(f);
       
            BufferedImage image = c.convertToGrayscale(img);
            File grayScaleFile = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\GrayScaleBXRay.bmp");
            c.writeImage(image,grayScaleFile); //

            //Contrast Limited Adaptive Histogram Equalization (Adaptive Histogram Equalization)
            opencv_core.IplImage sourceImage = cvLoadImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\GrayScaleBXRay.bmp");
            opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(sourceImage), IPL_DEPTH_8U, 1);
            cvCvtColor(sourceImage, grayImage, CV_BGR2GRAY);
            opencv_core.IplImage claheImage = cvCreateImage(cvGetSize(grayImage), grayImage.depth(), grayImage.nChannels());

            //CLAHE 
            opencv_imgproc.CLAHE clahe = opencv_imgproc.createCLAHE(3.0, cvSize(8, 8));  //2.0 8x8 //28 8x8
            clahe.apply(grayImage, claheImage);

            cvSaveImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ContrastLimitedAdaptiveHistogramEqualized.bmp", claheImage);

            //median filter
            Mat source = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ContrastLimitedAdaptiveHistogramEqualized.bmp");
            Mat medianFilteredImage = new Mat();

            Imgproc.medianBlur(source, medianFilteredImage, 1);

            Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\MedianFilter.bmp", medianFilteredImage);

            //watershed
            String sourcePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\MedianFilter.bmp";
            opencv_core.IplImage sourceImage2 = cvLoadImage(sourcePath);

            opencv_core.IplImage grayImage2 = cvCreateImage(cvGetSize(sourceImage2), IPL_DEPTH_8U, 1);

            //convert to greyImage
            cvCvtColor(sourceImage2, grayImage2, CV_BGR2GRAY);

            //thresholding 
            BufferedImage img2 = grayImage2.getBufferedImage();
            ThresholdingImage t = new ThresholdingImage();
            int threshold = t.autoThreshold(img2);
            System.out.println(threshold);
            cvThreshold(grayImage2, grayImage2, 0, 255, CV_THRESH_BINARY + CV_THRESH_OTSU);

            String destPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\OtsuBinarization.bmp";

            cvSaveImage(destPath, grayImage2);

            opencv_core.IplImage erode = cvCreateImage(cvGetSize(sourceImage2), IPL_DEPTH_8U, 1);
            //erosion
            opencv_imgproc.cvErode(grayImage2, erode, null, 1);  //

            String destPath2 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ErodedXRay.bmp";

            cvSaveImage(destPath2, erode);

            opencv_core.IplImage dilate = cvCreateImage(cvGetSize(sourceImage2), IPL_DEPTH_8U, 1);

            //dilation
            opencv_imgproc.cvDilate(grayImage2, dilate, null, 3); //

            String destPath3 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\DilatedXRay.bmp";

            cvSaveImage(destPath3, dilate);

            opencv_core.IplImage bg = cvCreateImage(cvGetSize(sourceImage2), IPL_DEPTH_8U, 1);

            cvThreshold(dilate, bg, 150, 128, 1); //1

            String destPath4 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\BG.bmp";

            cvSaveImage(destPath4, bg);

            opencv_core.IplImage marker = cvCreateImage(cvGetSize(sourceImage2), IPL_DEPTH_8U, 1);

            opencv_core.cvAdd(erode, bg, marker, null);

            String destPath5 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\Added.bmp";

            cvSaveImage(destPath5, marker);

            //convert to 32SC1 (32 bit signed single channel).
            opencv_core.IplImage marker32 = cvCreateImage(cvGetSize(sourceImage2), opencv_core.IPL_DEPTH_32S, 1);

            cvConvertScale(marker, marker32, 1, 0);

            String destPath6 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\Marker32.bmp";

            cvSaveImage(destPath6, marker32);

            //apply watershed
            cvWatershed(sourceImage2, marker32);

            String destPath7 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\Watershed.bmp";

            cvSaveImage(destPath7, marker32);

            //convert result back into uint8 image
            opencv_core.IplImage marker8U = cvCreateImage(cvGetSize(sourceImage2), opencv_core.IPL_DEPTH_8U, 1);

            cvConvertScale(marker32, marker8U, 1, 0);

            String destPath8 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\Marker8U.bmp";

            cvSaveImage(destPath8, marker8U);

            ExtractingLungRegionFromXRay ex = new ExtractingLungRegionFromXRay();
            //ex.getLungRegion();

            //contour detection
           
            String originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\LungRegionXRaY.bmp";

            Mat image3 = Highgui.imread(originalImagePath);

            List<MatOfPoint> contours = new ArrayList<>();

            

                Mat image_2 = new Mat();

                Imgproc.cvtColor(image3, image_2, Imgproc.COLOR_BGR2GRAY);

                //getting threshold value
                File ff = new File(originalImagePath);

                BufferedImage buf = ImageIO.read(ff);

                ConvertRGBImageToGrayScale cc = new ConvertRGBImageToGrayScale();

                BufferedImage buf2 = c.convertToGrayscale(buf);

                int threshold2 = ThresholdingImage.autoThreshold(buf2);

                Imgproc.threshold(image_2, image_2, 150, 255, CV_THRESH_BINARY);

                findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                for (int i = 0; i < contours.size(); i++) {
                    Imgproc.drawContours(image3, contours, i, new Scalar(240, 0, 0), 2);
                    //String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ContourDetection-"+i+".bmp";
                    //Highgui.imwrite(path, image3);
                }
            
            Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ContourDetection.bmp", image3);   
                                       
    }
}
