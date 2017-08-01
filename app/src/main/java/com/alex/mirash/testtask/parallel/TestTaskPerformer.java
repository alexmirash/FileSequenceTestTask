package com.alex.mirash.testtask.parallel;

import com.alex.mirash.testtask.TaskResult;
import com.alex.mirash.testtask.parallel.worker.BoundParams;
import com.alex.mirash.testtask.parallel.worker.SequenceSearchWorker;
import com.alex.mirash.testtask.parallel.worker.WorkerResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.alex.mirash.testtask.tool.LogUtils.log;

/**
 * @author Mirash
 */

public class TestTaskPerformer {
    private final long BUCKET_SIZE = 10;

    public TestTaskPerformer() {
    }

    public TaskResult performTestTask(File file) throws IOException, ExecutionException, InterruptedException {
        //decide count of workers to perform the task
        long fileSize = file.length();
        int count = (int) (fileSize / BUCKET_SIZE);
        long symbolsLeft = fileSize - count * BUCKET_SIZE;
        int workersCount;
        //just decide if should we add new worker for last symbols
        if (symbolsLeft > BUCKET_SIZE * 0.25) {
            workersCount = count + 1;
        } else {
            workersCount = count;
        }
        log("workersCount = " + workersCount);
        ExecutorService service = Executors.newFixedThreadPool(workersCount);
        long startTime = System.currentTimeMillis();
        List<SequenceSearchWorker> workers = new ArrayList<>(workersCount);
        for (int i = 0; i < workersCount; i++) {
            long startPosition = i * BUCKET_SIZE;
            long itemsToScan = i == workersCount - 1 ? fileSize - BUCKET_SIZE * (workersCount - 1) : BUCKET_SIZE;
            workers.add(new SequenceSearchWorker(i, startPosition, itemsToScan, file));
        }
        List<Future<WorkerResult>> futures = service.invokeAll(workers);
        WorkerResult[] results = new WorkerResult[workersCount];
        // wait for all tasks to complete before continuing
        for (Future<WorkerResult> f : futures) {
            WorkerResult workerResult = f.get();
            log("worker[" + workerResult.getWorkerPosition() + "] finished " + (System.currentTimeMillis() - startTime));
            results[workerResult.getWorkerPosition()] = workerResult;
        }
        log("all tasks completed " + (System.currentTimeMillis() - startTime));
        //analise thread results and find the best one
        long maxStartPosition = -1;
        long maxLength = 0;
        for (int i = 0; i < workersCount; i++) {
            WorkerResult currentResult = results[i];
            long currentLength = currentResult.getSequenceLength();
            long currentStartPosition = currentResult.getSequenceStartPosition();
            //concat end of current result and start of next
            if (i != workersCount - 1) {
                BoundParams startParams = currentResult.getEndParams();
                BoundParams nextParams = results[i + 1].getEndParams();
                //check if border sequences may be concat
                if (!startParams.isCheckBorderElement() || !nextParams.isCheckBorderElement()) {
                    long borderSequenceLength = startParams.getLength() + nextParams.getLength();
                    //check if border is bigger than current length
                    if (borderSequenceLength > currentLength) {
                        currentLength = borderSequenceLength;
                        currentStartPosition = startParams.getStartIndex();
                    }
                }
            }
            //compare current results with max
            if (currentLength > maxLength) {
                maxLength = currentLength;
                maxStartPosition = currentStartPosition;
            }
        }
        service.shutdownNow();
        return new TaskResult(maxStartPosition, maxLength);
    }
}
