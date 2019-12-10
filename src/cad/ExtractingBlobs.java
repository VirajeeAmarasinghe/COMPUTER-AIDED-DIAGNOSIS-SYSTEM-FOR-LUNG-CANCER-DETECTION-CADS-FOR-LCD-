/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author virajee
 */
public class ExtractingBlobs {

    public void extractingBlobs() {
        File f = null;
        BufferedImage b = null;
        try {
            f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConnectedComponents.bmp");
            b = ImageIO.read(f);
            int width = b.getWidth();
            int height = b.getHeight();

            Map m = new HashMap<Integer, Integer>();
            Set<Integer> uniqueValues = new HashSet<Integer>();
            
            //reading image pixel RGB values

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    int rgb = b.getRGB(i, j);

                    if (m.get(rgb) != null) {
                        if ((i == 1 && j >= 1) || (i >= 1 && j == 1) || ((i == (width - 2))) && j >= 1 || (i >= 1 && j == (height - 2))) {

                            uniqueValues.add(rgb);
                        }
                        Object count = m.get(rgb);
                        int co = (int) count;
                        co++;
                        m.put(rgb, co);

                    } else if (m.get(rgb) == null) {
                        if ((i == 1 && j >= 1) || (i >= 1 && j == 1) || ((i == (width - 2))) && j >= 1 || (i >= 1 && j == (height - 2))) {
                            uniqueValues.add(rgb);
                        }
                        m.put(rgb, 1);
                    }
                }
            }

            Map m_altered = new HashMap<Integer, Integer>();

            Iterator it = m.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                int value = (int) pair.getValue();
                int rgbFromMap = (int) pair.getKey();

                boolean found = found(uniqueValues, rgbFromMap);
                if (!found) {
                    m_altered.put(rgbFromMap, value);
                }
            }

            Iterator it_2 = m_altered.entrySet().iterator();
           

            int max = 0;
            int maxRGB = 0;

            while (it_2.hasNext()) {
                Map.Entry pair = (Map.Entry) it_2.next();
                int value = (int) pair.getValue();
                int rgbFromMap = (int) pair.getKey();

                if (value > max) {
                    max = value;
                    maxRGB = (int) pair.getKey();
                }
            }
            
            System.out.println(m_altered);
            
            m_altered.remove(maxRGB);

           Iterator it_3 = m_altered.entrySet().iterator();
           

            int max_2 = 0;
            int maxRGB_2 = 0;

            while (it_3.hasNext()) {
                Map.Entry pair = (Map.Entry) it_3.next();
                int value = (int) pair.getValue();
                int rgbFromMap = (int) pair.getKey();

                if (value > max_2) {
                    max_2 = value;
                    maxRGB_2 = (int) pair.getKey();
                }
            }    
            
            System.out.println("maxValues"+""+max+" "+max_2);
           
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    int rgb = b.getRGB(i, j);

                    if (maxRGB == rgb || maxRGB_2==rgb) {
                        b.setRGB(i, j, Color.WHITE.getRGB());
                    } else {
                        b.setRGB(i, j, Color.BLACK.getRGB());
                    }
                }
            }

            //saving file
            File output = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\InitialLungMask.bmp");
            boolean result=ImageIO.write(b, "bmp", output);            
            

        } catch (Exception e) {
        }

    }

    public static boolean found(Set<Integer> uniqueValues, int rgbFromMap) {
        boolean found = false;
        for (Integer invalidRGB : uniqueValues) {
            int invalidRGBInt = invalidRGB;

            if (invalidRGBInt == rgbFromMap) {
                found = true;
                return found;
            }
        }
        return found;
    }
}
