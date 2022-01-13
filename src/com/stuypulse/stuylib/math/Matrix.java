/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

/**
 * Multiplication-supporting matrix class containing doubles.
 *
 * @author Ivan Wei (ivanw8288@gmail.com)
 */
public final class Matrix {
    private double[][] data;

    /**
     * Constructs a square matrix with the given size, where all values are initialized to 0.0.
     * 
     * @param size the size of the square matrix
     */
    public Matrix(int size) {
        this.data = new double[size][size];
    }

    /**
     * Constructs a matrix with the given number of rows and columns, where all values are initialized to 0.0.
     * 
     * @param rows the number of rows in the matrix
     * @param cols the number of cols in the matrix
     */
    public Matrix(int rows, int cols) {
        this.data = new double[rows][cols];
    }
    
    /**
     * Constructs a matrix with the given array of doubles. Throws an exception if the rows are not uniform in length. 
     * 
     * @param data 
     */
    public Matrix(double[][] data) {
        if (data.length <= 0) {
            this.data = new double[0][0];
            return;
        }

        this.data = new double[data.length][data[0].length];

        int columns = data[0].length;
        for (int r = 0; r < data.length; ++r) {
            if (data[r].length != columns) {
                throw new IllegalArgumentException("Could not construct matrix; given matrix data is not of uniform length");
            }
            
            for (int c = 0; c < data[r].length; ++c) {
                this.data[r][c] = data[r][c];
            }
        }
    }

}