/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import static cad.UploadDicomImage.lbl_result_display;
import static com.googlecode.javacv.cpp.opencv_core.CV_8U;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import static org.opencv.core.Core.mean;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.drawContours;
import static org.opencv.imgproc.Imgproc.findContours;
import org.opencv.imgproc.Moments;

/**
 *
 * @author virajee
 */
public class NewClass {

    public double[] calculateMajorAxisLength(List<MatOfPoint> contours) {
        RotatedRect r;
        double[] majorArray = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");

        for (int i = 0; i < contours.size(); i++) {
            //Contour must have at least 5 points for fitEllipse
            if (contours.get(i).toArray().length < 5) {
                majorArray[i] = 0;   
            } else {
                //Copy MatOfPoint to MatOfPoint2f
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
                //Fit an ellipse to the current contour
                r = Imgproc.fitEllipse(matOfPoint2f);

                //The size-member contains the length of you major and minor axis.
                if (Double.isNaN(r.size.height)) {
                    majorArray[i] = 0;  
                } else {
                    majorArray[i] = Double.parseDouble(df.format(r.size.height)); 
                }
            }
        }
        return majorArray;
    }
    
    public double[] calculateMinorAxisLength(List<MatOfPoint> contours) {
        RotatedRect r;
        double[] minorArray = new double[contours.size()];
        DecimalFormat df = new DecimalFormat("#.###");

        for (int i = 0; i < contours.size(); i++) {
            //Contour must have at least 5 points for fitEllipse
            if (contours.get(i).toArray().length < 5) {
                minorArray[i] = 0;   
            } else {
                //Copy MatOfPoint to MatOfPoint2f
                MatOfPoint2f matOfPoint2f = new MatOfPoint2f(contours.get(i).toArray());
                //Fit an ellipse to the current contour
                r = Imgproc.fitEllipse(matOfPoint2f);

                //The size-member contains the length of you major and minor axis.
                if (Double.isNaN(r.size.width)) {
                    minorArray[i] = 0; System.out.println("Contour-"+i+"="+minorArray[i]);
                } else {
                    minorArray[i] = Double.parseDouble(df.format(r.size.width)); 
                }
            }
        }
        return minorArray;
    }
}
