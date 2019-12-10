/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class CAD {

    static BufferedImage myJpegImage = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        // TODO code application logic here
//        //open DICOM image
////       DICOM dcm = new DICOM();
////        dcm.open("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CR-MONO1-10-chest");
////        if (dcm.getWidth() == 0) {
////            IJ.log("Error opening image.dicom");
////        } else {
////            dcm.show();
////
////        }
//        //------------------------------------------------------------------
//        
//        //convert DICOM file to BITMAP
//        
//        try{
//            File src = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\CR-MONO1-10-chest");
//            System.out.println("comes here");
//            File dest = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\Test.jpg");           
//            Dcm2Jpeg dcm2jpg= new Dcm2Jpeg();           
//            dcm2jpg.convert(src, dest);         
//            System.out.println("Completed");
//        } catch(IOException e){
//            e.printStackTrace();
//        }

        //get the rgb value
//        Color c = new Color(-4538534 , true);
//    System.out.println(c.getRed());
//    System.out.println(c.getGreen());
//    System.out.println(c.getBlue());
//    System.out.println(c.getAlpha());
//    System.out.println(c.BLACK.getRGB());
//        String a, b, c;
//
//        MultiMap mMap = new MultiValueMap();
//        mMap.put("a", "Hello there, It's a wonderful day");
//        mMap.put("a", "nice to meet you");
//
//        Set<String> keys = mMap.keySet();
//
//        for (String key : keys) {
//            System.out.println("Key = " + key);
//            System.out.println("Values = " + mMap.get(key));
//            List<String> list = (List<String>) mMap.get(key);
//
//            b = list.get(0);
//            c = list.get(1);
//            System.out.println("B : " + b);
//            System.out.println("C : " + c);
//        }
//
//        DecimalFormat df = new DecimalFormat("#.####");
//        System.out.println(df.format(21.459876));
        
//        double[] areaArray={1,2,3,4,5,6,7,8,9};
//        ArrayList<Integer> AreaNaN=new ArrayList<>();
//        AreaNaN.add(2);
//        AreaNaN.add(7);
//        AreaNaN.add(9);
//        
//        for(int k=0;k<areaArray.length;k++){
//               boolean found=false;
//               for(int l=0;l<AreaNaN.size();l++){               
//                  if(areaArray[k]==AreaNaN.get(l)){
//                      //System.out.println(k);
//                     found=true;
//                     break;
//                  }
//               }
//               if(found){
//                   System.out.println(areaArray[k]);
//                   found=false;
//               }
//           }
        
//        double d=18.514;
//        if(d <= 18.514 && d>=12.035){
//            System.out.println("a");
//        }
        
        JOptionPane.showMessageDialog(null, "Enter Valid Username");
    }

}
