package com.alex.mirash.testtask.parallel.worker;

/**
 * @author Mirash
 */

public class WorkerResult {
    private int workerPosition;

    private long sequenceStartPosition;
    private long sequenceLength;

    private BoundParams startParams;
    private BoundParams endParams;

    private boolean whole;

    WorkerResult(int workerPosition, long startPosition, long length) {
        this.workerPosition = workerPosition;
        this.sequenceStartPosition = startPosition;
        this.sequenceLength = length;
    }

    void setStartParams(BoundParams startParams) {
        this.startParams = startParams;
    }

    void setEndParams(BoundParams endParams) {
        this.endParams = endParams;
    }

    public long getSequenceStartPosition() {
        return sequenceStartPosition;
    }

    public long getSequenceLength() {
        return sequenceLength;
    }

    public BoundParams getStartParams() {
        return startParams;
    }

    public BoundParams getEndParams() {
        return endParams;
    }

    public int getWorkerPosition() {
        return workerPosition;
    }

    //means that result of worker contains whole checked sequence from start to end
    public boolean isWhole() {
        return whole;
    }

    public void setWhole(boolean whole) {
        this.whole = whole;
    }

    @Override
    public String toString() {
        return "WorkerResult{" +
                "workerPosition=" + workerPosition +
                ", sequenceStartPosition=" + sequenceStartPosition +
                ", sequenceLength=" + sequenceLength +
                ", startParams=" + startParams +
                ", endParams=" + endParams +
                ", whole=" + whole +
                '}';
    }
}
