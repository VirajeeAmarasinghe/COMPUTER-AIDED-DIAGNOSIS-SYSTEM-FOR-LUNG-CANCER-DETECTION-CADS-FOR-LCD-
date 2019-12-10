/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 *
 * @author virajee
 */
public class MedianFilterTest {
    
    public MedianFilterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of medianFiltering method, of class MedianFilter.
     */
    @Test
    public void testMedianFiltering() {
        System.out.println("medianFiltering");
        String inputPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\"
                + "X-ray\\X-Ray Output\\ContrastLimitedAdaptiveHistogramEqualized.bmp";
        String outputPath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\"
                + "X-ray\\X-Ray Output\\TestMedianFiltering.bmp";
        MedianFilter instance = new MedianFilter();
        
        //apply median filter and save the image in output path
        instance.medianFiltering(inputPath, outputPath);
        
        try {
            File f1=new File(inputPath);
            //read the input image
            BufferedImage histogramEqualizedImage=ImageIO.read(f1);
            
            File f2=new File(outputPath);
            //read the filtered image
            BufferedImage filteredImage=ImageIO.read(f2);
            //get the actual Raster data of images as a byte array
            byte[] imageArray1 = ((DataBufferByte) histogramEqualizedImage.getData().getDataBuffer()).getData();
            byte[] imageArray2 = ((DataBufferByte) filteredImage.getData().getDataBuffer()).getData();
            
            //check the both arrays are equal 
            assertArrayEquals(imageArray1, imageArray2);
            
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
    }
    
}
