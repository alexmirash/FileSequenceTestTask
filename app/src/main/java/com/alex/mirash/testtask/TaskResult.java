package com.alex.mirash.testtask;

/**
 * @author Mirash
 */

public class TaskResult {
    private long startIndex;
    private long length;

    public TaskResult(long startIndex, long length) {
        this.startIndex = startIndex;
        this.length = length;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "WorkerResult{" +
                "startIndex=" + startIndex +
                ", length=" + length +
                '}';
    }
}
