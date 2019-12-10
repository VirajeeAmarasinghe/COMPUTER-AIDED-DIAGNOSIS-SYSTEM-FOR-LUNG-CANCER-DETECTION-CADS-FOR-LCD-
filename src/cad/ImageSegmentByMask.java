/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class ImageSegmentByMask {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // will read image
        Mat image = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp");
        Mat mask = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp", Highgui.CV_LOAD_IMAGE_GRAYSCALE);

        Rect rectangle = new Rect(10, 10, image.cols() - 20, image.rows() - 20);

        Mat bgdModel = new Mat(); // extracted features for background
        Mat fgdModel = new Mat(); // extracted features for foreground
        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(0));

        convertToOpencvValues(mask); // from human readable values to OpenCV values 

        int iterCount = 1;
        Imgproc.grabCut(image, mask, rectangle, bgdModel, fgdModel, iterCount, Imgproc.GC_INIT_WITH_MASK);

        convertToHumanValues(mask); // back to human readable values
        Imgproc.threshold(mask, mask, 128, 255, Imgproc.THRESH_TOZERO);

        
        Mat foreground = new Mat(image.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        image.copyTo(foreground, mask);

        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\sucess2.bmp", foreground);
    }

    private static void convertToHumanValues(Mat mask) {
        byte[] buffer = new byte[3];
        for (int x = 0; x < mask.rows(); x++) {
            for (int y = 0; y < mask.cols(); y++) {
                mask.get(x, y, buffer);
                int value = buffer[0];
                if (value == Imgproc.GC_BGD) {
                    buffer[0] = 0; // for sure background
                } else if (value == Imgproc.GC_PR_BGD) {
                    buffer[0] = 85; // probably background
                } else if (value == Imgproc.GC_PR_FGD) {
                    buffer[0] = (byte) 170; // probably foreground
                } else {
                    buffer[0] = (byte) 255; // for sure foreground

                }
                mask.put(x, y, buffer);
            }
        }
    }

    /**
     * Converts level of grayscale into OpenCV values. White - foreground, Black
     * - background.
     *
     * @param mask
     */
    private static void convertToOpencvValues(Mat mask) {
        byte[] buffer = new byte[3];
        for (int x = 0; x < mask.rows(); x++) {
            for (int y = 0; y < mask.cols(); y++) {
                mask.get(x, y, buffer);
                int value = buffer[0];
                if (value >= 0 && value < 64) {
                    buffer[0] = Imgproc.GC_BGD; // for sure background
                } else if (value >= 64 && value < 128) {
                    buffer[0] = Imgproc.GC_PR_BGD; // probably background
                } else if (value >= 128 && value < 192) {
                    buffer[0] = Imgproc.GC_PR_FGD; // probably foreground
                } else {
                    buffer[0] = Imgproc.GC_FGD; // for sure foreground

                }
                mask.put(x, y, buffer);
            }
        }

    }
}
