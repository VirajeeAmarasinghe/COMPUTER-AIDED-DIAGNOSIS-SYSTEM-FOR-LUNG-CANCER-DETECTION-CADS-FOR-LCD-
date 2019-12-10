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

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

public class ImageMasker {
     public static BufferedImage createTileMask(BufferedImage tile, BufferedImage mask)
    {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        BufferedImage result = gc.createCompatibleImage(tile.getWidth(null), tile.getHeight(null), Transparency.BITMASK);
        BufferedImage temp = gc.createCompatibleImage(tile.getWidth(null), tile.getHeight(null), Transparency.BITMASK);
        
        WritableRaster raster = result.getRaster();
        Raster maskData = mask.getRaster();
        Raster tileData = tile.getRaster();
        
        Graphics g;
        
        int[] pixel = new int[4];
        int width = tile.getWidth(null);
        int height = tile.getHeight(null);
        
        for(int y=0; y<height; y++)
        {
            for(int x=0; x<width; x++)
            {
                pixel = maskData.getPixel(x, y, pixel);
                
                if(pixel[0] == 0) 
                {
                    tileData.getPixel(x, y, pixel);
                    pixel[3] = 255;
                    raster.setPixel(x, y, pixel);
                    
                    pixel = tileData.getPixel(x, y, pixel);
                }
            }
        }
        
        result.setData(raster);
        
        g = temp.createGraphics();
        g.drawImage(result, 0, 0, null);
        g.dispose();
        
        return temp;
    }
    
    public static void main(String[] args)
    {
        try
        {
            BufferedImage tile = ImageIO.read(new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp"));
            BufferedImage mask = ImageIO.read(new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\completeLungMask.bmp"));
            BufferedImage masked = createTileMask(tile, mask);
            
            JFrame frame = new JFrame();
            ImageIcon icon = new ImageIcon(masked);
            JLabel label = new JLabel(icon);
            
            frame.getContentPane().add(label);
            frame.pack();
            frame.setVisible(true);
            
        }
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
