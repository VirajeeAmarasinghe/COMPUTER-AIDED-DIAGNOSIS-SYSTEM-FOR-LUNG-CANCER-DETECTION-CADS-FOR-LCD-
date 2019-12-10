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
public interface Labels {
    
    /**
     * Returns the label at the [x, y] location
     * @param x column index
     * @param y row index
     * @return the label at the specified location
     */
    int get(int x, int y);
    
    /**
     * Updates the label at the [x, y] location
     * @param x column index
     * @param y row index
     * @param label the new label for the specified location
     */
    void set(int x, int y, int label);
}
