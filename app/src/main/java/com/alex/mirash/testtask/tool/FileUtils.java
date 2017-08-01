package com.alex.mirash.testtask.tool;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * @author Mirash
 */

public class FileUtils {

    public static void createFile(String fileName, long size) throws Exception {
        long time = System.currentTimeMillis();
        LogUtils.log("createFile: " + fileName + ", " + size);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName + ".txt");
        if (file.exists()) {
            file.delete();
        }
        file.setWritable(true);
        file.setReadable(true, false);
        FileOutputStream fileOutput = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutput);
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (long i = 0; i < size; i++) {
            builder.append(random.nextBoolean() ? '0' : '1');
        }
        outputStreamWriter.write(builder.toString());
        outputStreamWriter.flush();
        fileOutput.getFD().sync();
        outputStreamWriter.close();
        LogUtils.log("File successfully created: " + (System.currentTimeMillis() - time));
    }

    public static File getFile(String fileNme) throws Exception {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileNme + ".txt");
        return file;
    }
}
