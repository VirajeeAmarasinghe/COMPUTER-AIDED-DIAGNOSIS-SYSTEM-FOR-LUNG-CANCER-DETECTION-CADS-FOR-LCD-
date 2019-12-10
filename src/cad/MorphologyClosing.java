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
public class MorphologyClosing {

    public boolean morphologyClosing() {
        boolean success=true;
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            /*
             filename - Name of file to be loaded.
             flags - Flags specifying the color type of a loaded image:

             >0 Return a 3-channel color image
             =0 Return a grayscale image
             <0 Return the loaded image as is. Note that in the current implementation the alpha channel, 
             if any, is stripped from the output image. For example, a 4-channel RGBA image is loaded as RGB if flags >= 0. 
             */
            Mat source = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMask.bmp", Highgui.CV_LOAD_IMAGE_UNCHANGED);
            if (source != null) {                
                Mat destination = new Mat(source.rows(), source.cols(), source.type());

                destination = source;

                /*
                 public static Mat getStructuringElement(int shape, Size ksize)
                 shape-Element shape that could be one of the following:
                 MORPH_RECT - a rectangular structuring element
                 MORPH_ELLIPSE-an elliptic structuring element, that is, a filled ellipse inscribed into the rectangle Rect(0, 0, esize.width, 0.esize.height)
                 MORPH_CROSS - a cross-shaped structuring element
                 CV_SHAPE_CUSTOM - custom structuring element (OpenCV 1.x API)
                 ksize-Size of the structuring element.
                 Returns a structuring element of the specified size and shape for morphological operations.
                 The function constructs and returns the structuring element that can be further passed to 
                 "createMorphologyFilter", "erode", "dilate" or "morphologyEx".
                 */
                Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(21, 21));

                /*
                 public static void morphologyEx(Mat src, Mat dst, int op, Mat kernel)
                 src-Source (input) image
                 The number of channels can be arbitrary. 
                 The depth should be one of CV_8U, CV_16U, CV_16S, CV_32F or CV_64F.
                 dst-Destination image of the same size and type as src
                 op-The kind of morphology transformation to be performed
                 Closing: MORPH_CLOSE: 3
                 kernel-The kernel to be used/structuring element
                 */
                /*
                 morphology closing-dilation of an image followed by an erosion
                 useful to remove small holes (dark regions)or small black points on the object. 
                 */
                Imgproc.morphologyEx(source, destination, Imgproc.MORPH_CLOSE, element); 

                //destination.convertTo(destination, CvType.CV_8U);           
                Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp", destination);

            } else {
                String text = UploadDicomImage.lbl_result_display.getText() + "<html><p style='margin-left:10px;margin-top:10px;'>Sorry Error Occured Reading the Initial Lung Mask....</p>";
                UploadDicomImage.lbl_result_display.setText(null);
                success=false;
            }

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            success=false;
        }
        return success;
    }
}
