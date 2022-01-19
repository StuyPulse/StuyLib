/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.util;

/**
 * A class used to generate HashCodes by combining multiple different objects / types. The combining
 * function is taken from boost, as it is a great hash combiner
 *
 * <p>It also has static methods available if creating a new class is undesirable.
 *
 * @author Sam B. (sam.belliveau@gmail.com)
 */
public final class HashBuilder {

    /*******************************************/
    /*** Static HashBuilder Helper Functions ***/
    /*******************************************/

    /** Default Value the internal hashpool will have if no value is given */
    public static final int DEFAULT_INITIALER = 0x9e3779b9;

    /** Prime Numbers used in combination process */
    private static final int PRIME_C1 = 0xcc9e2d51;

    private static final int PRIME_C2 = 0x1b873593;

    /**
     * Implementation of Hash Combine from the Boost Library
     *
     * @see <a
     *     href="https://github.com/boostorg/container_hash/blob/171c012d4723c5e93cc7cffe42919afdf8b27dfa/include/boost/container_hash/hash.hpp#L316-L329">Boost
     *     Implementation</a>
     * @param a the first of the two values to combine into a hash
     * @param b the second of the two values to combine into a hash
     * @return the result of the hash combining function
     */
    public static int combineHash(int a, int b) {
        b *= PRIME_C1;
        b = Integer.rotateLeft(b, 15);
        b *= PRIME_C2;

        a ^= b;
        a = Integer.rotateLeft(a, 13);
        a = a * 5 + 0xe6546b64;

        return a;
    }

    /**
     * Implementation of Hash Combine from the Boost Library
     *
     * <p>This will not return the same value as {@link #combineHash(Object, int)} if you swap the
     * object and integers. This function is dependent on the order
     *
     * @see #combineHash(int,int)
     * @param a the first of the two values to combine into a hash
     * @param b the second of the two values to combine into a hash
     * @return the result of the hash combining function
     */
    public static int combineHash(int a, Object b) {
        return combineHash(a, b.hashCode());
    }

    /**
     * Implementation of Hash Combine from the Boost Library
     *
     * <p>This will not return the same value as {@link #combineHash(int, Object)} if you swap the
     * object and integers. This function is dependent on the order
     *
     * @see #combineHash(int,int)
     * @param a the first of the two values to combine into a hash
     * @param b the second of the two values to combine into a hash
     * @return the result of the hash combining function
     */
    public static int combineHash(Object a, int b) {
        return combineHash(a.hashCode(), b);
    }

    /**
     * Implementation of Hash Combine from the Boost Library
     *
     * @see #combineHash(int,int)
     * @param a the first of the two values to combine into a hash
     * @param b the second of the two values to combine into a hash
     * @return the result of the hash combining function
     */
    public static int combineHash(Object a, Object b) {
        return combineHash(a.hashCode(), b.hashCode());
    }

    /****************************/
    /*** Class Implementation ***/
    /****************************/

    // Internal Pool to store hash
    private int mHashPool;

    /**
     * Create a HashBuilder with a custom initial state, usually unnecessary
     *
     * @param initial initial starting value for the HashBuilder
     */
    public HashBuilder(int initial) {
        this.mHashPool = initial;
    }

    /** Create a HashBuilder with the default initialized state */
    public HashBuilder() {
        this(DEFAULT_INITIALER);
    }

    /*** Getting the HashCode Back ***/

    /** @return the digested hashCode of everything that was added */
    @Override
    public int hashCode() {
        return mHashPool;
    }

    /** @return the digested hashCode of everything that was added */
    public int toHashCode() {
        return this.hashCode();
    }

    /*** Appending single variables ***/

    /**
     * @param value integer to append to hashCode
     * @return this
     */
    public HashBuilder append(int value) {
        mHashPool = combineHash(mHashPool, value);
        return this;
    }

    /**
     * @param value boolean to append to hashCode
     * @return this
     */
    public HashBuilder append(boolean value) {
        return append(Boolean.hashCode(value));
    }

    /**
     * @param value byte to append to hashCode
     * @return this
     */
    public HashBuilder append(byte value) {
        return append(Byte.hashCode(value));
    }

    /**
     * @param value char to append to hashCode
     * @return this
     */
    public HashBuilder append(char value) {
        return append(Character.hashCode(value));
    }

    /**
     * @param value short to append to hashCode
     * @return this
     */
    public HashBuilder append(short value) {
        return append(Short.hashCode(value));
    }

    /**
     * @param value long to append to hashCode
     * @return this
     */
    public HashBuilder append(long value) {
        return append(Long.hashCode(value));
    }

    /**
     * @param value float to append to hashCode
     * @return this
     */
    public HashBuilder append(float value) {
        return append(Float.hashCode(value));
    }

    /**
     * @param value double to append to hashCode
     * @return this
     */
    public HashBuilder append(double value) {
        return append(Double.hashCode(value));
    }

    /**
     * @param value object to append to hashCode
     * @return this
     */
    public HashBuilder append(Object value) {
        return append(value.hashCode());
    }

    /*** Appending multiple variables ***/

    /**
     * @param values integers to append to hashCode
     * @return this
     */
    public HashBuilder append(int... values) {
        for (int v : values) append(v);
        return this;
    }

    /**
     * @param values booleans to append to hashCode
     * @return this
     */
    public HashBuilder append(boolean... values) {
        for (boolean v : values) append(v);
        return this;
    }

    /**
     * @param values bytes to append to hashCode
     * @return this
     */
    public HashBuilder append(byte... values) {
        for (byte v : values) append(v);
        return this;
    }

    /**
     * @param values chars to append to hashCode
     * @return this
     */
    public HashBuilder append(char... values) {
        for (char v : values) append(v);
        return this;
    }

    /**
     * @param values shorts to append to hashCode
     * @return this
     */
    public HashBuilder append(short... values) {
        for (short v : values) append(v);
        return this;
    }

    /**
     * @param values longs to append to hashCode
     * @return this
     */
    public HashBuilder append(long... values) {
        for (long v : values) append(v);
        return this;
    }

    /**
     * @param values floats to append to hashCode
     * @return this
     */
    public HashBuilder append(float... values) {
        for (float v : values) append(v);
        return this;
    }

    /**
     * @param values doubles to append to hashCode
     * @return this
     */
    public HashBuilder append(double... values) {
        for (double v : values) append(v);
        return this;
    }

    /**
     * @param values objects to append to hashCode
     * @return this
     */
    public HashBuilder append(Object... values) {
        for (Object v : values) append(v);
        return this;
    }
}
