package com.alex.mirash.testtask.tool;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Mirash
 */

public class FileUtils {

    public static void createFile(String fileNme) throws Exception {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileNme + ".txt");
        FileOutputStream fileOutput = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutput);
        outputStreamWriter.write("01001111010011111011");
        outputStreamWriter.flush();
        fileOutput.getFD().sync();
        outputStreamWriter.close();
    }

    public static File getFile(String fileNme) throws Exception {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileNme + ".txt");
        return file;
    }
}
