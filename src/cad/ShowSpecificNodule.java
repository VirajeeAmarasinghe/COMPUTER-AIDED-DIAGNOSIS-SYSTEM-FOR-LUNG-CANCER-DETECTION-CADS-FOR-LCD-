/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.findContours;

/**
 *
 * @author virajee
 */
public class ShowSpecificNodule {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp");

        if (image.empty() == true) {
            System.out.println("Error: no image found!");
        } else {

            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Mat image_2 = new Mat();

            //image.convertTo(image_2, CvType.CV_32SC1);
            Imgproc.cvtColor(image, image_2, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(image_2, image_2, 120, 325, CV_THRESH_BINARY);

            findContours(image_2, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            for (int i = 0; i < contours.size(); i++) {
                System.out.println("Contour-"+i+"="+contourArea(contours.get(i)));
                for (Point p : contours.get(i).toList()) {                    
//                    if(p.x==325 && p.y==120){
//                        System.out.println(p.x + " " + p.y);
//                        Imgproc.drawContours(image, contours, i, new Scalar(255,0,0), 2);
//                    }
                }
                //Imgproc.drawContours(image, contours, i, new Scalar(255,0,0), 2);
                //String path="D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CT-Training-lc001-ShowNodules-Contour-"+i+".bmp";
                //Highgui.imwrite(path, image); // DEBUG
            }
            AreaOfEachNodule a=new AreaOfEachNodule();
            //a.calculateNoduleArea(contours);
        }
        //Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CT-Training-lc001-ShowSpecificNodule.bmp", image); // DEBUG
    }
}
