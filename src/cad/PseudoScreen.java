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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PseudoScreen {

 

        public static BufferedImage scr;

        BufferedImage setLabels() throws IOException {
            File f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Binary.bmp");
            BufferedImage scr = ImageIO.read(f);
            int m = 2;
            //scr is a pseudo screen object 
            for (int y = 0; y < scr.getHeight(); y++) {
                for (int x = 0; x < scr.getWidth(); x++) {
                    if (scr.getRGB(x, y) == 1) {
                        compLabel(x, y, m++);
                    }
                }
            }
            return scr;
        }

        void compLabel(int i, int j, int m) {
            if (scr.getRGB(i, j) == 1) {
                scr.setRGB(i, j, m);
//assign label 

//thread delay 
                compLabel(i - 1, j - 1, m);
                compLabel(i - 1, j, m);
                compLabel(i - 1, j + 1, m);
                compLabel(i, j - 1, m);
                compLabel(i, j + 1, m);
                compLabel(i + 1, j - 1, m);
                compLabel(i + 1, j, m);
                compLabel(i + 1, j + 1, m);
            }
        }
    public static void main(String []args) throws IOException{
         PseudoScreen p=new PseudoScreen();
         BufferedImage b=p.setLabels();
         
         File f2=new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\output_2.bmp");
         
         ImageIO.write(b,"bmp", f2);
    }
}
