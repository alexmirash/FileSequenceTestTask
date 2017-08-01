package com.alex.mirash.testtask.parallel.worker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Callable;

import static com.alex.mirash.testtask.tool.LogUtils.log;

/**
 * @author Mirash
 */

public class SequenceSearchWorker implements Callable<WorkerResult> {
    private int position;
    private long sleepTime;

    private File file;
    private long startFileScanIndex;
    private long itemsToScan;

    private WorkerResult result;

    public SequenceSearchWorker(int position, long startFileScanIndex, long itemsToScan, File file) {
        this.position = position;
        this.startFileScanIndex = startFileScanIndex;
        this.file = file;
        log("New Worker: [" + position + "] " + startFileScanIndex + "; " + itemsToScan);
    }

    @Override
    public WorkerResult call() throws Exception {
        try {
            InputStream inputStream = new FileInputStream(file);
            Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.skip(startFileScanIndex);
            long index = 0;
            int character;
            while (index < itemsToScan && (character = bufferedReader.read()) != -1) {
                index++;
            }
            bufferedReader.close();
        } catch (Exception ignored) {
        }
        result = new WorkerResult(position, 0, 10);
        result.setStartParams(new BoundParams(5, true));
        result.setEndParams(new BoundParams(5, false));
        log("run finished [" + position + "]");
        return result;
    }
}
