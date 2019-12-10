/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

/**
 *
 * @author virajee
 */
import ij.*;
import ij.io.FileSaver;
import ij.measure.*;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import static org.opencv.core.CvType.channels;
import static org.opencv.core.CvType.depth;

/** converting  an ImagePlus object to a different type. */
/*Chnage the Bit Depth*/
public class ImageConverter {
    private ImagePlus imp;
    private int type;
    private static boolean doScaling = true;

    /** Construct an ImageConverter based on an ImagePlus object. */
    public ImageConverter(ImagePlus imp) {
        this.imp = imp;
        this.type = imp.getType();
        System.out.println(imp.getType());
    }

    /** Convert your ImagePlus to 8-bit grayscale.
     * @return  */
    public ImagePlus convertToGray8() {
        System.out.println(this.imp.getBitDepth());
        if (this.type==ImagePlus.GRAY8)
            return imp;
        if (!(this.type==ImagePlus.GRAY16||this.type==ImagePlus.GRAY32||this.type==ImagePlus.COLOR_RGB))
            throw new IllegalArgumentException("Unsupported conversion");
        ImageProcessor ip = imp.getProcessor();
        imp.trimProcessor();
        Calibration cal = imp.getCalibration();
        //imp.setProcessor(null, ip.convertToFloat());
        imp.setProcessor(null, ip.convertToShort(doScaling));
        imp.setCalibration(cal); //update calibration
        return imp;
    }

    /** Set true to scale to 0-255 when converting short to byte or float
        to byte and to 0-65535 when converting float to short.
     * @param scaleConversions */
    public static void setDoScaling(boolean scaleConversions) {
        doScaling = scaleConversions;
        IJ.register(ImageConverter.class); 
    }

    /** Returns true if scaling is enabled.
     * @return  */
    public static boolean getDoScaling() {
        return doScaling;
    }
    
    public static void main(String []args) throws IOException{
         ImagePlus i=new ImagePlus("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\grayScaleImage.bmp");
         ImageConverter ic=new ImageConverter(i);
         i=ic.convertToGray8();
         
         
         //BufferedImage imgBuffer=i.getProcessor().getBufferedImage();
         
         //System.out.println(imgBuffer);
         System.out.println(i.getBitDepth());
         //***********************************************//
                  
         FileSaver fs=new FileSaver(i);
         boolean result=fs.saveAsBmp("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\grayScaleImage_32.bmp");
         
    }
}
