/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import javax.media.jai.ROI;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

/**
 *
 * @author virajee
 */
public class ExtractionOfLungs_2 {
    public static void main(String []args){
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            IplImage originalImage=cvLoadImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp");
            IplImage maskImage=cvLoadImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp");
           
            originalImage.maskROI(maskImage);
            
            
            
            cvSaveImage("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\success_3.bmp", maskImage);
            
    }
    
   
}
