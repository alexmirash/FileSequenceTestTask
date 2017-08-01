package com.alex.mirash.testtask.parallel.worker;

/**
 * @author Mirash
 */
public class BoundParams {
    private long startIndex;
    private long length;
    private boolean isCheckBorderElement;

    public BoundParams(long length, boolean isCheckBorderElement) {
        this.length = length;
        this.isCheckBorderElement = isCheckBorderElement;
    }

    public BoundParams(long startIndex, long length, boolean isCheckBorderElement) {
        this.startIndex = startIndex;
        this.length = length;
        this.isCheckBorderElement = isCheckBorderElement;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public long getLength() {
        return length;
    }

    public boolean isCheckBorderElement() {
        return isCheckBorderElement;
    }

    @Override
    public String toString() {
        return "BoundParams{" +
                "startIndex=" + startIndex +
                ", length=" + length +
                ", isCheckBorderElement=" + isCheckBorderElement +
                '}';
    }
}