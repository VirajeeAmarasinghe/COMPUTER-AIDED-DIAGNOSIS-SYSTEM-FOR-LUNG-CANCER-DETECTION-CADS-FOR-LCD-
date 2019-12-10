/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cad;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author virajee
 */
public class NoduleDetection_2 {

    public void detectNodule(double[] areaArray, ArrayList<Integer> AreaNaNIndices,
            double[] avgIntensity, ArrayList<Integer> AvgIntensityNaNIndices,
            double[] boundingBox, ArrayList<Integer> boundingBoxNaNIndices,
            double[] eccentricity, ArrayList<Integer> eccentricityNaNIndices,
            double[] solArray, ArrayList<Integer> solidityNaNIndices,
            double[] majorArray, ArrayList<Integer> majorNaNIndices,
            double[] minorArray, ArrayList<Integer> minorNaNIndices,
            List<MatOfPoint> contours) {
        //System.out.println(areaArray.length+" "+avgIntensity.length+" "+boundingBox.length+" "+eccentricity.length+" "+solArray.length+" "+majorArray.length+" "+minorArray.length);

        String originalImagePath = "";
        Mat image = null;
        try {
            originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp";
            image = Highgui.imread(originalImagePath);
        } catch (Exception e) {
        }

//get the first two larget contours
        double firstLargest = 0;
        int firstLargestIndex = 0;

        double secondLargest = 0;
        int secondLargestIndex = 0;

        for (int i = 0; i < areaArray.length; i++) {
            if (firstLargest < areaArray[i]) {
                firstLargest = areaArray[i];
                firstLargestIndex = i;
            }
            //System.out.println(i + "=" + areaArray[i]);
        }

        for (int j = 0; j < areaArray.length; j++) {
            if (firstLargestIndex == j) {
                continue;
            } else {
                if (secondLargest < areaArray[j]) {
                    secondLargest = areaArray[j];
                    secondLargestIndex = j;
                }
            }
        }
        //System.out.println(firstLargestIndex+"="+firstLargest);
        //System.out.println(secondLargestIndex+"="+secondLargest);

        for (int i = 0; i < contours.size(); i++) {
            if (firstLargestIndex != i && secondLargestIndex != i) {
                boolean found = false;
                for (int j = 0; j < AreaNaNIndices.size(); j++) {
                    if (i == AreaNaNIndices.get(j)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (areaArray[i] >= 725.5) { 
                        boolean found2 = false;
                        for (int k = 0; k < AvgIntensityNaNIndices.size(); k++) {
                            if (i == AvgIntensityNaNIndices.get(k)) {
                                found2 = true;
                                break;
                            }
                        }
                        if (!found2) {
                            if (avgIntensity[i] >= 12.832) { 
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 31.38) { 
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] >= -48.325) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.474) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 61.975) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 29.214) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }else if (majorArray[i] < 61.975 && majorArray[i] >= 48.049) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 29.214) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (areaArray[i] >= 83.5 && areaArray[i] < 715.5) {
                        boolean found2 = false;
                        for (int k = 0; k < AvgIntensityNaNIndices.size(); k++) {
                            if (i == AvgIntensityNaNIndices.get(k)) {
                                found2 = true;
                                break;
                            }
                        }
                        if (!found2) {
                            if (avgIntensity[i] <= 100.505 && avgIntensity[i] >= 12.035) { 
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 28.893) { 
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] <= -13.96 && eccentricity[i] >= -26.756) { 
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.642) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] <= 22.913 && majorArray[i] >= 16.66) { 
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] <= 10.25 && minorArray[i] >= 8.525) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(255, 0, 0), 2);
                                                                    } else if (minorArray[i] >= 13.422 && minorArray[i]<15.384) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            } else if (majorArray[i] >= 26.537 && majorArray[i]<34.763) {  //last edit x<44.981
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] <= 10.25 && minorArray[i] >= 8.525) {                                                                        
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }else if (minorArray[i] >=17.298) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }else if (majorArray[i] > 44.981) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] <= 10.25 && minorArray[i] >= 8.525) {                                                                        
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }else if (minorArray[i] >=17.298) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (avgIntensity[i] >= 101.158) {
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 27.494) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] >= -28.943) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.486) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 43.751) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 16.208) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }else if (majorArray[i] >= 23.228 && majorArray[i]<43.751) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 16.208) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (eccentricity[i] <= -117.569) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.886) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 27.224) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 5.67) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (areaArray[i] >= 715.5 && areaArray[i] < 725.5) {
                        boolean found2 = false;
                        for (int k = 0; k < AvgIntensityNaNIndices.size(); k++) {
                            if (i == AvgIntensityNaNIndices.get(k)) {
                                found2 = true;
                                break;
                            }
                        }
                        if (!found2) {
                            if (avgIntensity[i] <= 80.633) {
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 37.658) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] >= -32.415) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.591) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 33.929) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] <= 51.379) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(255, 0, 0), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (areaArray[i] >= 20 && areaArray[i] <= 25) {
                        boolean found2 = false;
                        for (int k = 0; k < AvgIntensityNaNIndices.size(); k++) {
                            if (i == AvgIntensityNaNIndices.get(k)) {
                                found2 = true;
                                break;
                            }
                        }
                        if (!found2) {
                            if (avgIntensity[i] <= 130.5 && avgIntensity[i] >= 101.714) {
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 35.833 && boundingBox[i] <= 47.619) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] >= -27.508 && eccentricity[i] <= -19.098) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.843 && solArray[i] <= 0.952) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] <= 9.645 && majorArray[i] >= 5.466) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 3.632 && minorArray[i] <= 5.316) {
                                                                        if (minorArray[i] >= 3.983 && minorArray[i] <= 4.0) {
                                                                        } else if (minorArray[i] >= 3.896 && minorArray[i] <= 4.0) {
                                                                        } else {
                                                                            Imgproc.drawContours(image, contours, i, new Scalar(255, 0, 0), 2);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (areaArray[i] <= 33.5) {
                        boolean found2 = false;
                        for (int k = 0; k < AvgIntensityNaNIndices.size(); k++) {
                            if (i == AvgIntensityNaNIndices.get(k)) {
                                found2 = true;
                                break;
                            }
                        }
                        if (!found2) {
                            if (avgIntensity[i] <= 111.182) {
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 52.344) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] >= -16.4) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.971) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 8.907) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 5.238) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(255, 0, 0), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (areaArray[i] <= 82.5 && areaArray[i] > 25) {
                        boolean found2 = false;
                        for (int k = 0; k < AvgIntensityNaNIndices.size(); k++) {
                            if (i == AvgIntensityNaNIndices.get(k)) {
                                found2 = true;
                                break;
                            }
                        }
                        if (!found2) {
                            if (avgIntensity[i] >= 105.146) {
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 36.83) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] >= 17.862) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.817) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 19.678) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 6.615) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(255, 0, 0), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (eccentricity[i] >= -54.571) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.96) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 9.662) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 8.525) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (avgIntensity[i] < 105.146 && avgIntensity[i] >= 99.203) {
                                boolean found3 = false;
                                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                                    if (i == boundingBoxNaNIndices.get(l)) {
                                        found3 = true;
                                        break;
                                    }
                                }
                                if (!found3) {
                                    if (boundingBox[i] >= 60.5) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] <= -54.571) { //last edit here x>=-54.571
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.96) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 9.662) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 8.525) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (boundingBox[i] < 60.5 && boundingBox[i] >= 42.092) {
                                        boolean found4 = false;
                                        for (int m = 0; m < eccentricityNaNIndices.size(); m++) {
                                            if (i == eccentricityNaNIndices.get(m)) {
                                                found4 = true;
                                                break;
                                            }
                                        }
                                        if (!found4) {
                                            if (eccentricity[i] <= -19.862) {
                                                boolean found5 = false;
                                                for (int n = 0; n < solidityNaNIndices.size(); n++) {
                                                    if (i == solidityNaNIndices.get(n)) {
                                                        found5 = true;
                                                        break;
                                                    }
                                                }
                                                if (!found5) {
                                                    if (solArray[i] >= 0.982) {
                                                        boolean found6 = false;
                                                        for (int o = 0; o < majorNaNIndices.size(); o++) {
                                                            if (i == majorNaNIndices.get(o)) {
                                                                found6 = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found6) {
                                                            if (majorArray[i] >= 16.595) {
                                                                boolean found7 = false;
                                                                for (int p = 0; p < minorNaNIndices.size(); p++) {
                                                                    if (i == minorNaNIndices.get(p)) {
                                                                        found7 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found7) {
                                                                    if (minorArray[i] >= 8.293) {
                                                                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungNodules.bmp", image);
    }

}
