/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class TestingDilationErosionAndMorphologyCloseManually {
    
    public static void main( String[] args ) {
   
      try{	
         System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
         Mat source = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMask.bmp",  Highgui.CV_LOAD_IMAGE_UNCHANGED);
         Mat destination = new Mat(source.rows(),source.cols(),source.type());
         
         destination = source;

         int erosion_size = 5;
         int dilation_size = 5;
         
         /**************************************************************Dilation***************************************************************/
         
         source = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMask.bmp",  Highgui.CV_LOAD_IMAGE_UNCHANGED);
         
         destination = source;
         
         Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(2*dilation_size + 1, 2*dilation_size+1));
         
         Imgproc.dilate(source, destination, element1);
         
         Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\dilation.bmp", destination);
         
         
         /****************************************************Erosion*************************************************************************/
         Mat source2 = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMask.bmp",  Highgui.CV_LOAD_IMAGE_UNCHANGED);
         Mat destination2 = new Mat(source2.rows(),source2.cols(),source2.type());
         
         destination2 = source2;
         Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(2*erosion_size + 1, 2*erosion_size+1));
     
         Imgproc.erode(source2, destination2, element2);
       
         Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\erosion.bmp", destination2);

         
         /****************************************************Morphology Close*******************************************************************/
         
         /*erode(dilate(src,element))*/
         Mat source3=destination;
         Mat destination3 = new Mat(source3.rows(),source3.cols(),source3.type());
         
         destination3 = source3;
         Mat element3 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new  Size(2*erosion_size + 1, 2*erosion_size+1));
     
         Imgproc.erode(source3, destination3, element3);
       
         Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\erosion+dilation=morphologyClose.bmp", destination3);
      }catch (Exception e) {
         System.out.println("error: " + e.getMessage());
      } 
   }
}
