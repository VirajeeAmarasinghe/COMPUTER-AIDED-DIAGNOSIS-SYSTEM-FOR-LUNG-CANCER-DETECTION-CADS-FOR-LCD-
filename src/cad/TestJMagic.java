/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.io.File;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

/**
 *
 * @author virajee
 */

//This class is for converting DICOM to BitMap
public class TestJMagic {

    public static void main(String[] args) throws Exception {
        String myPath = "C:\\Program Files (x86)\\ImageMagick-6.3.9-Q16";
        ProcessStarter.setGlobalSearchPath(myPath);
        // create command
        ConvertCmd cmd = new ConvertCmd();

        // create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        System.out.println("comes here");
        File input = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CR-MONO1-10-chest.dicom");
        File output = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CR-MONO1-10-chest-bitmap.bmp");
        op.addImage();
        //op.resize(200,200);
        op.addImage();

        ConvertCmd convert = new ConvertCmd();
        convert.run(op, new Object[]{input, output});
        // execute the operation
        //cmd.run(op);
    }
}
