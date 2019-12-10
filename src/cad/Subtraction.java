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
public class Subtraction {

    public Subtraction() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void subtractGaborFromGuassian(String gaborPath, String guassianPath, String output) {
        Mat gabor1 = Highgui.imread(gaborPath);
        Mat gussianBlur = Highgui.imread(guassianPath);
        Mat difference = new Mat();
        Core.subtract(gabor1, gussianBlur, difference);

        Highgui.imwrite(output, difference);
    }
}
