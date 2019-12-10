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
import static org.opencv.core.Core.mean;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class AbnormalDetectionInXRay {

    public AbnormalDetectionInXRay() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    

    public void detectAbnormality(String inputPath,String gaborFilterPath) {        
        Mat source = Highgui.imread(inputPath);
        Mat gabor1 = new Mat(source.width(), source.height(), CvType.CV_8UC1);

       //predefine parameters for Gabor kernel
       /*
         ksize - si the size of the window, not the entire matrix. i.e. 17x17, 21x21, ... always odd number.
         lambda - wavelenght, the thickness of the wave. can be 10, 15, 20, 30 or whatever you want.
         sigma - standard deviation. it's defined like X*lambda, 0< X < 1. But you also can asign any number.
         theta - is the orientation (in degrees) of your filter. can be 0 to 360.
         gamma - is de width of the wave (elipticity). between 0.02 and 1. 1 is round, 0.02 is practically a line.
         psi - is the offset of phase(in degrees). can be 0, 90, -90, 180, -180. if you want the wave to process the image as it. is, you should asign 0.
         ktype - is CvType.CV_32F or CvType.CV_64F.
         */
        Size kSize = new Size(21, 21); 

        double theta1 = 180;
        double lambda = 4;  //1
        double sigma = 0.65;
        double gamma = 1;
        double psi = 0;

        // gabor filter
        Mat kernel1 = Imgproc.getGaborKernel(kSize, sigma, theta1, lambda, gamma, psi, CvType.CV_32F);

        // apply filters on source image. The result is stored in gabor1
        Imgproc.filter2D(source, gabor1, -1, kernel1);        

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\GaborFilter.bmp", gabor1);
        
        Mat gussianBlur = new Mat(source.width(), source.height(), CvType.CV_8UC1);
        
        Imgproc.GaussianBlur(gabor1, gussianBlur,new Size(51,51), 50); //0.8  50 11x11
        
        //link-http://docs.opencv.org/3.1.0/d4/d13/tutorial_py_filtering.html
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\GaussianBlurring.bmp", gussianBlur);
        
        Mat difference = new Mat();
        Core.subtract(gabor1, gussianBlur, difference);
        
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\Difference.bmp", difference);
        
        Mat mm=Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\LungRegionXRaY.bmp");
        
        List<MatOfPoint> contours = new ArrayList<>();
        Mat image_2 = new Mat();
        
        Imgproc.cvtColor(difference, image_2, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(image_2, image_2, 0, 255, CV_THRESH_BINARY+CV_THRESH_OTSU);
        
        findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < contours.size(); i++) {
            Mat mask = new Mat(image_2.cols(), image_2.rows(), CV_8U, new Scalar(0));
            Imgproc.drawContours(image_2, contours, i, new Scalar(240, 0, 0), 2);
            Imgproc.drawContours(mask, contours, i, new Scalar(240, 0, 0), 2); //i->-1
            String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ContourDetection2-"+i+".bmp";
            Highgui.imwrite(path, image_2);
            Imgproc.moments(contours.get(i));
            
            Scalar m = mean(mm, mask);
            System.out.println(m.val[0]+"-"+i);
        }
        //Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\X-Ray Output\\ContourDetection2.bmp", mask);
    }
}
