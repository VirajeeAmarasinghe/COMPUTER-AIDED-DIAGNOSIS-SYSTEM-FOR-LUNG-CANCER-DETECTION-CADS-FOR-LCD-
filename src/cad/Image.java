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
final class Image implements Labels {
    final int[][] pixels;

    Image(int[][] pixels) {
        this.pixels = pixels.clone();
        for (int i = 0; i < this.pixels.length; i++) {
            this.pixels[i] = this.pixels[i].clone();
        }        
    }

    int getWidth() {
        return pixels[0].length;
    }

    int getHeight() {
        return pixels.length;
    }

    @Override
    public int get(int x, int y) {
        return pixels[y][x];
    }

    @Override
    public void set(int x, int y, int label) {
        pixels[y][x] = label;
    }    
}
