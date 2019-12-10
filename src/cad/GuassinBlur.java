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

//link-http://docs.opencv.org/3.1.0/d4/d13/tutorial_py_filtering.html
public class GuassinBlur {

    public GuassinBlur() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void applyGuassinBlur(String inputPath, String outputPath) {
        Mat gaborSource = Highgui.imread(inputPath);
        Mat gussianBlur = new Mat(gaborSource.width(), gaborSource.height(), CvType.CV_8UC1);
        Imgproc.GaussianBlur(gaborSource, gussianBlur, new Size(51, 51), 50);
        Highgui.imwrite(outputPath, gussianBlur);
    }
}
