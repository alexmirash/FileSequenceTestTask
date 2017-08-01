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
    private long endScanIndex;

    private WorkerResult result;

    public SequenceSearchWorker(int position, long startFileScanIndex, long itemsToScan, File file) {
        this.position = position;
        this.startFileScanIndex = startFileScanIndex;
        this.endScanIndex = startFileScanIndex + itemsToScan;
        this.file = file;
        log("New Worker: [" + position + "] " + startFileScanIndex + "; " + itemsToScan);
    }

    @Override
    public WorkerResult call() throws Exception {
        try {
            InputStream inputStream = new FileInputStream(file);
            Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.skip(startFileScanIndex);
            long currentStartIndex = startFileScanIndex;  //start position of currently checking sequence

            long maxStartIndex = 0;  //start position of the longest sequence
            long maxLength = 0;  //length of the longest sequence

            long index = startFileScanIndex + 1;  //current position of read symbol
            long character = bufferedReader.read();
            if (character == -1) {
                return null;
            }
            char previousChar = (char) character;
            //read file symbol by symbol
            while (index < endScanIndex && (character = bufferedReader.read()) != -1) {
                //run alg here
                char currentChar = (char) character;
                //check condition of sequence end
                if (previousChar == '0' && currentChar == '0') {
                    long currentLength = index - currentStartIndex;  //length of current sequence
                    //update maximal sequence if necessary
                    if (currentLength > maxLength) {
                        maxStartIndex = currentStartIndex;
                        maxLength = currentLength;
                    }
                    //start check next sequence from current index
                    currentStartIndex = index;
                }
                previousChar = currentChar;
                //
                index++;
            }
            //check last sequence
            long currentLength = index - currentStartIndex;
            if (currentLength > maxLength) {
                maxStartIndex = currentStartIndex;
                maxLength = currentLength;
            }
            bufferedReader.close();
            result = new WorkerResult(position, maxStartIndex, maxLength);
            result.setStartParams(new BoundParams(5, true));
            result.setEndParams(new BoundParams(5, false));
            log("run finished [" + position + "] " + result);
            return result;
        } catch (Exception ignored) {
            return null;
        }
    }
}
