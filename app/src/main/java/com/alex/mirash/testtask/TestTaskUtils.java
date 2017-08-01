package com.alex.mirash.testtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static com.alex.mirash.testtask.tool.LogUtils.log;

public class TestTaskUtils {

    public static void performTestTask(File file) throws IOException {
        if (file == null || !file.exists()) {
            log("File should exist before performing the task");
            return;
        }
        TaskResult result = searchForLongestSequence(file);
        if (result != null) {
            log("1. result = " + result.toString());
            //retrieve sequence from file
//            retrieveSequence(file, result);
        } else {
            log("No matching sequence was found");
        }
    }

    /**
     * performs test task (=D)
     *
     * @param file
     * @throws IOException
     */
    public static TaskResult searchForLongestSequence(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));

            long currentStartIndex = 0;  //start position of currently checking sequence

            long maxStartIndex = 0;  //start position of the longest sequence
            long maxLength = 0;  //length of the longest sequence

            long index = 1;  //current position of read symbol

            long character = reader.read();
            if (character == -1) {
                return null;
            }
            char previousChar = (char) character;
            //read file symbol by symbol
            while ((character = reader.read()) != -1) {
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
                index++;
            }
            reader.close();
            //check last sequence
            long currentLength = index - currentStartIndex;
            if (currentLength > maxLength) {
                maxStartIndex = currentStartIndex;
                maxLength = currentLength;
            }
            return new TaskResult(maxStartIndex, maxLength);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * retrieve sequence from file. Was moved to another method because of memory limitations
     * that prevented storing result in memory
     */
    public static void retrieveSequence(File file, TaskResult result) throws IOException {
        System.out.println("~~~ Retrieve maximal sequence ~~~~");
        InputStream inputStream = new FileInputStream(file);
        Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        bufferedReader.skip(result.getStartIndex());
        long index = 0;
        int character;
        while (index < result.getLength() && (character = bufferedReader.read()) != -1) {
            System.out.print((char) character);
            index++;
        }
        System.out.println();
        bufferedReader.close();
    }
}