/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author virajee
 */
public class ConvertRGBImageToGrayScaleTest {

    public ConvertRGBImageToGrayScaleTest() {
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
     * Test of convertToGrayscale method, of class ConvertRGBImageToGrayScale.
     */
    @Test
    public void testConvertToGrayscale() {
        System.out.println("convertToGrayscale");

        File f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp");
        ConvertRGBImageToGrayScale instance = new ConvertRGBImageToGrayScale();

        BufferedImage sourceImage = instance.readImage(f);
        byte[] imageArray = ((DataBufferByte) sourceImage.getData().getDataBuffer()).getData();

        BufferedImage result = instance.convertToGrayscale(sourceImage);
        byte[] resultArray = ((DataBufferByte) result.getData().getDataBuffer()).getData();

        assertArrayEquals(imageArray, resultArray);

    }

    /**
     * Test of readImage method, of class ConvertRGBImageToGrayScale.
     */
    @Test
    public void testReadImage() {
        System.out.println("readImage");
        File f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp");
        ConvertRGBImageToGrayScale instance = new ConvertRGBImageToGrayScale();
        BufferedImage result = instance.readImage(f);
        assertNotNull(result);
    }

    /**
     * Test of writeImage method, of class ConvertRGBImageToGrayScale.
     */
    @Test
    public void testWriteImage() {
        System.out.println("writeImage");
        BufferedImage img = new BufferedImage(512, 512, 1);
        File f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\TestWriteImage.bmp");
        ConvertRGBImageToGrayScale instance = new ConvertRGBImageToGrayScale();
        boolean result = instance.writeImage(img, f);
        assertEquals(true, result);
    }

}
