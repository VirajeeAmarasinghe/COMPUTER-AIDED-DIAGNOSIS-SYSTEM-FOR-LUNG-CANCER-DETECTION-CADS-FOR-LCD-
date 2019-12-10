/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class NonLinearFiltering {
    public static void main(String []args){
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       
       //morphology open
       Mat source=Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\ContrastLimitedAdaptiveHistogramEqualized.bmp");
       
       Mat destination = new Mat(source.rows(), source.cols(), source.type());
       
       Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(359,359));  //361x361
       
       Imgproc.morphologyEx(source, destination, Imgproc.MORPH_OPEN, element); 
       
       Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\MorphologyOpen.bmp", destination);
       
       //median filter       
       Mat medianFilteredImage=new Mat();
       
       Imgproc.medianBlur(source,medianFilteredImage,15);    
        
       Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\MedianFilter.bmp", medianFilteredImage);
       
       //subtract
       Mat difference = new Mat();
            
       Core.subtract(medianFilteredImage,destination, difference);
       
       Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\X-ray\\XRayOutput\\NonLinearFiltering.bmp", difference);
    }
}
