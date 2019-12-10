/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
//link-http://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
//link-https://gist.github.com/tc/1217766/8e330b03c6200b770461952ab07d91b5dcbdc41b
public class ResizeImage {

    public static void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {

        try {
            // reads input image
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);

            // creates output image
            BufferedImage outputImage = new BufferedImage(scaledWidth,
                    scaledHeight, inputImage.getType());

            // scales the input image to the output image
            Graphics2D g2d = outputImage.createGraphics();
            g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            // extracts extension of output file
            String formatName = outputImagePath.substring(outputImagePath
                    .lastIndexOf(".") + 1);

            // writes to output file
            ImageIO.write(outputImage, formatName, new File(outputImagePath));
        } catch (IOException e) {
        }
    }

    public static void resizeWithImageJ(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {

        try {
            ImagePlus imp = IJ.openImage(inputImagePath);
            ImageProcessor ip = imp.getProcessor();
            ip.setInterpolationMethod(ImageProcessor.BILINEAR);
            ip = ip.resize(scaledWidth, scaledHeight);
            BufferedImage resizedImage = ip.getBufferedImage();
            ImageIO.write(resizedImage, "bmp", new File(outputImagePath));
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        String inputImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp";
        String outputImagePath1 = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ResizedBitMap.bmp";

        // resize to a fixed width (not proportional)
        int scaledWidth = 512;
        int scaledHeight = 512;
        ResizeImage.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);

    }

}
