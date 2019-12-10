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
public class NoduleDetection {

    public void detectNodule(double[] areaArray, ArrayList<Integer> AreaNaNIndices,
            double[] avgIntensity, ArrayList<Integer> AvgIntensityNaNIndices,
            double[] boundingBox, ArrayList<Integer> boundingBoxNaNIndices,
            double[] eccentricity, ArrayList<Integer> eccentricityNaNIndices,
            double[] solArray, ArrayList<Integer> solidityNaNIndices,
            double[] majorArray, ArrayList<Integer> majorNaNIndices,
            double[] minorArray, ArrayList<Integer> minorNaNIndices,
            List<MatOfPoint> contours) {
        //System.out.println(areaArray.length+" "+avgIntensity.length+" "+boundingBox.length+" "+eccentricity.length+" "+solArray.length+" "+majorArray.length+" "+minorArray.length);

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

        ArrayList<Integer> passArea = new ArrayList<>();
        ArrayList<Integer> passAvgIntensity = new ArrayList<>();
        ArrayList<Integer> passBoundingBox = new ArrayList<>();
        ArrayList<Integer> passEccentrcity = new ArrayList<>();
        ArrayList<Integer> passSolidity = new ArrayList<>();
        ArrayList<Integer> passMajor = new ArrayList<>();
        ArrayList<Integer> passMinor = new ArrayList<>();

        if (AreaNaNIndices.isEmpty()) {
            for (int k = 0; k < areaArray.length; k++) {
                if (areaArray[k] >= 83.5) {
                    passArea.add(k);
                }
            }
        } else {
            for (int k = 0; k < areaArray.length; k++) {
                boolean found = false;
                for (int l = 0; l < AreaNaNIndices.size(); l++) {
                    if (k == AreaNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (areaArray[k] >= 83.5) {
                        passArea.add(k);
                    }
                }
            }
        }

        if (AvgIntensityNaNIndices.isEmpty()) {
            for (int k = 0; k < avgIntensity.length; k++) {
                if (avgIntensity[k] >= 18.514) {
                    passAvgIntensity.add(k);
                }
            }
        } else {
            for (int k = 0; k < avgIntensity.length; k++) {
                boolean found = false;
                for (int l = 0; l < AvgIntensityNaNIndices.size(); l++) {
                    if (k == AvgIntensityNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (avgIntensity[k] >= 18.514) {
                        passAvgIntensity.add(k);
                    }
                }
            }
        }

        if (boundingBoxNaNIndices.isEmpty()) {
            for (int k = 0; k < boundingBox.length; k++) {
                if (boundingBox[k] >= 28.893) {
                    passBoundingBox.add(k);
                }
            }
        } else {
            for (int k = 0; k < boundingBox.length; k++) {
                boolean found = false;
                for (int l = 0; l < boundingBoxNaNIndices.size(); l++) {
                    if (k == boundingBoxNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (boundingBox[k] >= 28.893) {
                        passBoundingBox.add(k);
                    }
                }
            }
        }

        if (eccentricityNaNIndices.isEmpty()) {
            for (int k = 0; k < eccentricity.length; k++) {
                if (eccentricity[k] >= -14.68) {
                    passEccentrcity.add(k);
                }
            }
        } else {
            for (int k = 0; k < eccentricity.length; k++) {
                boolean found = false;
                for (int l = 0; l < eccentricityNaNIndices.size(); l++) {
                    if (k == eccentricityNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (eccentricity[k] >= -14.68) {
                        passEccentrcity.add(k);
                    }
                }
            }
        }

        if (solidityNaNIndices.isEmpty()) {
            for (int k = 0; k < solArray.length; k++) {
                if (solArray[k] >= 0.642) {
                    passSolidity.add(k);
                }
            }
        } else {
            for (int k = 0; k < solArray.length; k++) {
                boolean found = false;
                for (int l = 0; l < solidityNaNIndices.size(); l++) {
                    if (k == solidityNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (solArray[k] >= 0.642) {
                        passSolidity.add(k);
                    }
                }
            }
        }

        if (majorNaNIndices.isEmpty()) {
            for (int k = 0; k < majorArray.length; k++) {
                if (majorArray[k] >= 21.462) {
                    passMajor.add(k);
                }
            }
        } else {
            for (int k = 0; k < majorArray.length; k++) {
                boolean found = false;
                for (int l = 0; l < majorNaNIndices.size(); l++) {
                    if (k == majorNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (majorArray[k] >= 21.462) {
                        passMajor.add(k);
                    }
                }
            }
        }

        if (minorNaNIndices.isEmpty()) {
            for (int k = 0; k < minorArray.length; k++) {
                if (minorArray[k] >= 9.41) {
                    passMinor.add(k);
                }
            }
        } else {
            for (int k = 0; k < minorArray.length; k++) {
                boolean found = false;
                for (int l = 0; l < minorNaNIndices.size(); l++) {
                    if (k == minorNaNIndices.get(l)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (minorArray[k] >= 9.41) {
                        passMinor.add(k);
                    }
                }
            }
        }
        System.out.println(passArea + "\n" + passAvgIntensity + "\n" + passBoundingBox + "\n" + passEccentrcity + "\n" + passMajor + "\n" + passMinor + "\n" + passSolidity);
        ArrayList<Integer> seletectedContours = new ArrayList<>();

        for (int m = 0; m < passArea.size(); m++) {
            for (int n = 0; n < passAvgIntensity.size(); n++) {
                if (passArea.get(m) == passAvgIntensity.get(n)) {
                    for (int o = 0; o < passBoundingBox.size(); o++) {
                        if (passAvgIntensity.get(n) == passBoundingBox.get(o)) {
                            for (int p = 0; p < passEccentrcity.size(); p++) {
                                if (passBoundingBox.get(o) == passEccentrcity.get(p)) {
                                    for (int q = 0; q < passSolidity.size(); q++) {
                                        if (passEccentrcity.get(p) == passSolidity.get(q)) {
                                            for (int r = 0; r < passMajor.size(); r++) {
                                                if (passSolidity.get(q) == passMajor.get(r)) {
                                                    for (int s = 0; s < passMinor.size(); s++) {
                                                        if (passMajor.get(r) == passMinor.get(s)) {
                                                            if (firstLargestIndex != passMinor.get(s) && secondLargestIndex != passMinor.get(s)) {
                                                                seletectedContours.add(passMinor.get(s));
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
System.out.println(seletectedContours);
        try {
            String originalImagePath = "D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\ConvertedBitMap.bmp";
            Mat image = Highgui.imread(originalImagePath);
            for (int i = 0; i < contours.size(); i++) {
                for (int j = 0; j < seletectedContours.size(); j++) {
                    if (i == seletectedContours.get(j)) {
                        Imgproc.drawContours(image, contours, i, new Scalar(0, 0, 255), 2);
                    }
                }
            }
            Highgui.imwrite("D:\\Degree Subject Materials\\Final Project\\DICOM Samples\\LungNodules.bmp", image);
        } catch (Exception e) {
        }
    }

}
