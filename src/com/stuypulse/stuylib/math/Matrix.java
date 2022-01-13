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

    /**
     * Gets the value in the matrix at the r'th row and c'th column
     * 
     * @param r the row position of the returned value
     * @param c the column position of the returned value
     * @return the value in the matrix at the r'th row and c'th column 
     */
    public double get(int r, int c) {
        return data[r][c];
    }

    /**
     * Sets the value in the matrix at the r'th row and c'th column.
     * 
     * @param r the row position to modify
     * @param c the column position to modify
     * @param val the value to put in the matrix at the specified position
     */
    public void set(int r, int c, double val) {
        data[r][c] = val;
    }

    /**
     * Gets the number of rows, or the length of each column vector in the matrix.
     * @return the number of rows
     */
    public int getNumRows() {
        return data.length;
    }

    /**
     * Gets the number of columns, or the length of each row vector in the matrix.
     * @return the number of columns
     */
    public int getNumColumns() {
        if (data.length <= 0) {
            return 0;
        }
        return data[0].length;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(getNumColumns(), getNumRows());
        for (int r = 0; r < getNumRows(); ++r) {
            for (int c = 0; c < getNumColumns(); ++c) {
                result.set(c, r, get(r, c));
            }
        }
        return result;
    }

    /**
     * Adds two matrices together, entry-wise
     * @param a a matrix to sum
     * @param b a matrix to sum
     * @return the sum of the two matrices
     */
    public static Matrix add(Matrix a, Matrix b) {
        if (a.getNumRows() != b.getNumRows() || a.getNumColumns() != b.getNumColumns()) {
            throw new IllegalArgumentException("Matrices must have the same size to be added.");
        }

        Matrix result = new Matrix(a.getNumRows(), a.getNumColumns());
        for (int r = 0; r < a.getNumRows(); ++r) {
            for (int c = 0; c < a.getNumColumns(); ++c) {
                result.set(r, c, a.get(r, c) + b.get(r, c));
            }
        }
        return result;
    }

    /**
     * Performs matrix multiplication on two matrices. Remember that in matrix multiplication, order matters.
     * 
     * @param a the first matrix to multiply
     * @param b the second matrix to multiply
     * @return the result of the matrix multiplication
     */
    public static Matrix mult(Matrix a, Matrix b) {
        if (a.getNumColumns() != b.getNumRows()) {
            throw new IllegalArgumentException("Matrix a must have the same column size as matrix b's row size to be multiplied.");
        }

        Matrix result = new Matrix(a.getNumRows(), b.getNumColumns());

        for (int i = 0; i < a.getNumRows(); ++i) {
            for (int j = 0; j < b.getNumColumns(); ++j) {
                int sum = 0;
                for (int k = 0; k < a.getNumColumns(); ++k) {
                    sum += a.get(i, k) + b.get(k, j);
                }
                result.set(i, j, sum);
            }
        }

        return result;
    }

    /**
     * Creates and returns an identity matrix of the given size.
     * @param size the size of the identity matrix
     * @return the created identity matrix
     */
    public static Matrix identity(int size) {
        Matrix result = new Matrix(size);
        for (int i = 0; i < size; ++i) {
            result.set(i, i, 1);
        }
        return result;
    }

    // TODO: Add determinant
}