/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.*;
import java.io.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class CreateHistogram extends JPanel {

    int[] bins = new int[256];

    public CreateHistogram(int[] pbins) {
        bins = pbins;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < 256; i++) {
            System.out.println("bin[" + i + "]===" + bins[i]);
            g.drawLine(200 + i, 300, 200 + i, 300 - (bins[i]) / 1000);
        }
    }

    public static void main(String[] arge) throws IOException {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        int[] pbins = new int[256];
        double[] sbins = new double[256];
        //PlanarImage image = JAI.create("fileload", "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\grayScaleImage.jpg");
        //BufferedImage bi = image.getAsBufferedImage();
        File f = new File("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungRegion.bmp");
        BufferedImage bi = ImageIO.read(f);
        System.out.println("tipe is          " + bi.getType());
        int[] pixel = new int[3];

        int k = 0;
        Color c = new Color(k);
        Double d = 0.0;
        Double d1;
        System.out.println("width="+bi.getWidth()+",height="+bi.getHeight());
        System.out.println("times"+bi.getWidth()*bi.getHeight());
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                pixel = bi.getRaster().getPixel(x, y, new int[3]);
                d = (0.2125 * pixel[0]) + (0.7154 * pixel[1]) + (0.072 * pixel[2]);
                k = (int) (d / 256);
                sbins[k]++;
                System.out.println("x="+x+",y="+y+" time="+(x*y));
                int number = 10;
            HistogramDataset dataset = new HistogramDataset();
            dataset.setType(HistogramType.RELATIVE_FREQUENCY);
            dataset.addSeries("Histogram", sbins, number);
            String plotTitle = "Histogram";
            String xaxis = "number";
            String yaxis = "value";
            PlotOrientation orientation = PlotOrientation.VERTICAL;
            boolean show = false;
            boolean toolTips = false;
            boolean urls = false;
            JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
                    dataset, orientation, show, toolTips, urls);
            int width = 500;
            int height = 300;
            try {
                ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            }
            
           
          
            
        }
//        System.out.println("copleted" + d + "--" + k);
//        JTabbedPane jtp = new JTabbedPane();
//        ImageIcon im = new ImageIcon(bi);
//        jtp.addTab("Histogram", new CreateHistogram(sbins));
//        frame.add(jtp);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        double[] value = new double[100];
//        Random generator = new Random();
//        for (int i = 1; i < 100; i++) {
//            value[i] = generator.nextDouble();
//            int number = 10;
//            HistogramDataset dataset = new HistogramDataset();
//            dataset.setType(HistogramType.RELATIVE_FREQUENCY);
//            dataset.addSeries("Histogram", value, number);
//            String plotTitle = "Histogram";
//            String xaxis = "number";
//            String yaxis = "value";
//            PlotOrientation orientation = PlotOrientation.VERTICAL;
//            boolean show = false;
//            boolean toolTips = false;
//            boolean urls = false;
//            JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
//                    dataset, orientation, show, toolTips, urls);
//            int width = 500;
//            int height = 300;
//            try {
//                ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
//            } catch (IOException e) {
//            }
//        }
    }

}
