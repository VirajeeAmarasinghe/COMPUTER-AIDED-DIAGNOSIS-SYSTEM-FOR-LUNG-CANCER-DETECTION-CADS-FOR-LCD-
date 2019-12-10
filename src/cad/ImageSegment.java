/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import static org.opencv.core.CvType.CV_8U;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class ImageSegment {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = null;
        image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp");
        
        Mat image_2 = null;
        image_2 = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp");
        
        //Rect(int x,int y,int width,int height)
        //Template class for 2D rectangles, described by the following parameters:
        //x,y-Coordinates of the top-left corner
        //width,height-Rectangle width and height
        Rect rectangle = new Rect(25,25,image.cols()-64,image.rows()-64);
        
        Mat result = new Mat();
        Mat bgdModel = new Mat();  // extracted features for background
        Mat fgdModel = new Mat();  // extracted features for foreground
        
        //Mat(int rows, int cols, int type, Scalar s)
        //rows-Number of rows in a 2D array
        //cols-Number of columns in a 2D array
        //type-Array type. Use CV_8UC1,..., CV_64FC4 to create 1-4 channel matrices, 
        //     or CV_8UC(n),..., CV_64FC(n) to create multi-channel (up to CV_MAX_CN channels) matrices
        //s-An optional value to initialize each matrix element with. 
        //  To set all the matrix elements to the particular value after the construction, 
        //  use the assignment operator Mat.operator=(const Scalar& value).
        
        //Scalar class-Template class for a 4-element vector derived from Vec
        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(3));
        
        System.out.println("imge_2"+image_2.depth()+" "+image_2.channels());        
       
        
        //grabcut(Mat img,Mat mask,Rect rect,Mat bgdModel,Mat fgdModel,int iterCount,int mode)
        //Runs the GrabCut algorithm
        //img-Input 8-bit 3-channel image
        //mask-Input/output 8-bit single-channel mask. The mask is initialized by the 
        //     function when mode is set to GC_INIT_WITH_RECT. Its elements may have one of 
        //     following values:
        //     GC_BGD defines an obvious background pixels
        //     GC_FGD defines an obvious foreground (object) pixel
        //     GC_PR_BGD defines a possible background pixel
        //     GC_PR_BGD defines a possible foreground pixel. 
        //rect-ROI containing a segmented object. 
        //     The pixels outside of the ROI are marked as "obvious background". 
        //     The parameter is only used when mode==GC_INIT_WITH_RECT.
        //bgdModel-Temporary array for the background model. 
        //         Do not modify it while you are processing the same image.
        //fgdModel-Temporary arrays for the foreground model. 
        //         Do not modify it while you are processing the same image.
        //iterCount-Number of iterations the algorithm should make before returning the result. 
        //          Note that the result can be refined with further calls with mode==GC_INIT_WITH_MASK 
        //          or mode==GC_EVAL.
        //mode-Operation mode that could be one of the following:
        //     GC_INIT_WITH_RECT The function initializes the state and the mask using the provided rectangle. After that it runs iterCount iterations of the algorithm. 
        //     GC_INIT_WITH_MASK The function initializes the state using the provided mask. Note that GC_INIT_WITH_RECT and GC_INIT_WITH_MASK can be combined. Then, all the pixels outside of the ROI are automatically initialized with GC_BGD. 
        //     GC_EVAL The value means that the algorithm should just resume. 
        
        Imgproc.grabCut(image, result, rectangle, bgdModel, fgdModel, 1, Imgproc.GC_INIT_WITH_RECT);
    
        Core.compare(result, source,result, Core.CMP_EQ);
        
        Mat foreground= new Mat(image.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        image.copyTo(foreground, result);
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\sucess1.bmp", foreground);
        System.out.println("grabcut sucess!");
    }

}
