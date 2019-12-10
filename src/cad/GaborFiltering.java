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
public class GaborFiltering {

    public GaborFiltering() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void applyGaborFilter(String inputPath, String outputPath) {
        Mat source = Highgui.imread(inputPath);
        Mat gabor1 = new Mat(source.width(), source.height(), CvType.CV_8UC1);
        //predefine parameters for Gabor kernel
       /*
         ksize - si the size of the window, not the entire matrix. i.e. 17x17, 21x21, ... always odd number.
         lambda - wavelenght, the thickness of the wave. can be 10, 15, 20, 30 or whatever you want.
         sigma - standard deviation. it's defined like X*lambda, 0< X < 1. But you also can asign any number.
         theta - is the orientation (in degrees) of your filter. can be 0 to 360.
         gamma - is de width of the wave (elipticity). between 0.02 and 1. 1 is round, 0.02 is practically a line.
         psi - is the offset of phase(in degrees). can be 0, 90, -90, 180, -180. if you want the wave to process the image as it. is, you should asign 0.
         ktype - is CvType.CV_32F or CvType.CV_64F.
         */
        Size kSize = new Size(21, 21);

        double theta1 = 180;
        double lambda = 4;
        double sigma = 0.65;
        double gamma = 1;
        double psi = 0;

        // gabor filter
        Mat kernel1 = Imgproc.getGaborKernel(kSize, sigma, theta1, lambda, gamma, psi, CvType.CV_32F);

        // apply filters on source image. The result is stored in gabor1
        Imgproc.filter2D(source, gabor1, -1, kernel1);

        Highgui.imwrite(outputPath, gabor1);
    }

}
