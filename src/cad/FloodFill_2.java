/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author virajee
 */
/*
  Input is the image, the starting node (x, y), the target color we want to fill, 
and the replacement color that will replace the target color. It implements a 4-way flood fill algorithm.
For large images, the performance can be improved by drawing the scanlines 
instead of setting each pixel to the replacement color, or by working directly on the databuffer. 

https://rosettacode.org/wiki/Bitmap/Flood_fill#Java

Test class os Test.java
*/
public class FloodFill_2 {

    public void floodFill(BufferedImage image, Point node, Color targetColor, Color replacementColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor.getRGB();
        int replacement = replacementColor.getRGB();
        if (target != replacement) {
            Deque<Point> queue = new LinkedList<Point>();
            do {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getRGB(x - 1, y) == target) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getRGB(x, y) == target) {
                    image.setRGB(x, y, replacement);
                    if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && image.getRGB(x, y - 1) != target) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1 && image.getRGB(x, y + 1) == target) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1 && image.getRGB(x, y + 1) != target) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.pollFirst()) != null);
        }
    }
}
