/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cad;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;
/**
 *
 * @author virajee
 */

//This class is for converting DICOM images to JPEG
public class TestDCM4 {
    static BufferedImage myJpegImage=null;

    public static void main(String[] args) {
        //reading the dicom image
        File file = new File("CR-MONO1-10-chest.dcm");
        Iterator<ImageReader> iterator =ImageIO.getImageReadersByFormatName("DICOM");
        while (iterator.hasNext()) {
            ImageReader imageReader = (ImageReader) iterator.next();
            DicomImageReadParam dicomImageReadParam = (DicomImageReadParam) imageReader.getDefaultReadParam();
            try {
                ImageInputStream iis = ImageIO.createImageInputStream(file);
                imageReader.setInput(iis,false);
                myJpegImage = imageReader.read(0, dicomImageReadParam);
                iis.close();
                if(myJpegImage == null){
                    System.out.println("Could not read image!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //converting happens here
            File file2 = new File("/test.jpg");
            try {
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file2));
                //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
                //encoder.encode(myJpegImage);
                ImageIO.write(myJpegImage, "JPEG", outputStream);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Completed");
        }

    }
}
