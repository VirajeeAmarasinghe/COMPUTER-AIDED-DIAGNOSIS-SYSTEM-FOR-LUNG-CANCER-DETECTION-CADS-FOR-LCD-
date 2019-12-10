/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 *
 * @author virajee
 */
public class ExtractionOfLungs {

    public boolean extract() {
        boolean result = true;
        try {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat originalImage = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp");
            Mat maskImage = Highgui.imread("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp");

            if (originalImage == null || maskImage == null) {
                result = false;
            } else {
                Mat difference = new Mat();

            //subtract(Mat src1,Mat src2,Mat dst)
                //Calculates the per-element difference between two arrays or array and a scalar
                //src1-First source array or a scalar
                //src2-Second source array or a scalar
                //dst-Destination array of the same size and the same number of channels as the input array
                Core.subtract(maskImage, originalImage, difference);

                Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp", difference);

            }
            return result;
        } catch (Exception e) {
            result = false;
            return result;
        }
    }

}
