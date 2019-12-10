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
import java.util.LinkedList;
import java.util.Queue;
import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
public class FoodFill {
    public static void floodFillImage(BufferedImage image,int x, int y, Color color) 
{
    int srcColor = image.getRGB(x, y);
    boolean[][] hits = new boolean[image.getHeight()][image.getWidth()];

    Queue<Point> queue = new LinkedList<>();
    queue.add(new Point(x, y));

    while (!queue.isEmpty()) 
    {
        Point p = queue.remove();

        if(floodFillImageDo(image,hits,p.x,p.y, srcColor, color.getRGB()))
        {     
            queue.add(new Point(p.x,p.y - 1)); 
            queue.add(new Point(p.x,p.y + 1)); 
            queue.add(new Point(p.x - 1,p.y)); 
            queue.add(new Point(p.x + 1,p.y)); 
        }
    }
}

private static boolean floodFillImageDo(BufferedImage image, boolean[][] hits,int x, int y, int srcColor, int tgtColor) 
{
    if (y < 0) return false;
    if (x < 0) return false;
    if (y > image.getHeight()-1) return false;
    if (x > image.getWidth()-1) return false;

    if (hits[y][x]) return false;

    if (image.getRGB(x, y)!=srcColor)
        return false;

    // valid, paint it

    image.setRGB(x, y, tgtColor);
    hits[y][x] = true;
    return true;
}

public static void main(String[] args) throws IOException{
    File f=new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary.bmp");
    BufferedImage b=ImageIO.read(f);
    float nLabels = 0;

		// iterate on image pixels to fin new regions
		for (int y = 0; y < b.getHeight(); y++) 
		{
			
			for (int x = 0; x < b.getWidth(); x++) 
			{
                            nLabels++;
                            System.out.println(x+","+y);
				
	                    floodFillImage(b, x, y,Color.BLACK);
			}
		}
                
                File f2 = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMask.bmp");
            ImageIO.write(b, "bmp", f2);
                
                
}
}
