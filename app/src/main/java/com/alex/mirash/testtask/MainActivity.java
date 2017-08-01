package com.alex.mirash.testtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alex.mirash.testtask.parallel.TestTaskPerformer;
import com.alex.mirash.testtask.tool.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.alex.mirash.testtask.tool.LogUtils.log;

public class MainActivity extends AppCompatActivity {
    public static final String FILE_NAME = "test_50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_create_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long size = 10000000L;
                try {
                    FileUtils.createFile("test10xy7", size);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAlgorithm();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performParallel();
            }
        });
    }

    private void performParallel() {
        File inputFile = null;
        try {
            inputFile = FileUtils.getFile(FILE_NAME);
            log("file size = " + inputFile.length());
        } catch (Exception e) {
            log("e = " + e);
            return;
        }

        TestTaskPerformer performer = new TestTaskPerformer();
        long startTime = System.currentTimeMillis();
        try {
            TaskResult result = performer.performTestTask(inputFile);
            log("2. result = " + result);
        } catch (IOException | ExecutionException | InterruptedException e) {
            log("e = " + e);
        }
        log("parallel time passed = " + (System.currentTimeMillis() - startTime));
    }

    private void performAlgorithm() {
        File inputFile = null;
        try {
            inputFile = FileUtils.getFile(FILE_NAME);
            log("file size = " + inputFile.length());
        } catch (Exception e) {
            log("e = " + e);
        }

        long startTime = System.currentTimeMillis();
        try {
            TestTaskUtils.performTestTask(inputFile);
        } catch (IOException e) {
            log("e = " + e);
        }
        log("default alg = " + (System.currentTimeMillis() - startTime));
    }
}
