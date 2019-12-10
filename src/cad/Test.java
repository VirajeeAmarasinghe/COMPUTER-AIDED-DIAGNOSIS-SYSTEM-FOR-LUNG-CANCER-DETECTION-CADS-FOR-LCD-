/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
public class Test {
    public Test() throws IOException {
    BufferedImage image = ImageIO.read(new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary.bmp"));
    new FloodFill_2().floodFill(image, new Point(0, 0), Color.WHITE, Color.BLACK);
    ImageIO.write(image, "bmp", new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\output.bmp"));
  }
 
  public static void main(String[] args) throws IOException {
    new Test();
  }
}
