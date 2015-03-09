/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.uci.uengine.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Writes a String to a file creating the file if it does not exist using the
 * default encoding for the VM.
 *
 * @author lan
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static File forceMkdir(File parent, String dirName) throws IOException {

        File newDirectory = new File(parent, dirName);
        forceMkdir(newDirectory);
        return newDirectory;
    }

    public static String writeStringToFile(String fileName, File parent, String data) throws IOException {

        File sourceFile = new File(parent, fileName);

        writeStringToFile(sourceFile, data);

        return sourceFile.getAbsolutePath();
    }

    public static String writeStringToFile(String fileName, File parent, String data, boolean applyDos2unixFileFixer) throws IOException, InterruptedException {
        String sourceFile = writeStringToFile(fileName, parent, data);

        if (applyDos2unixFileFixer) {
            dos2unixFileFixer(sourceFile);
        }

        return sourceFile;
    }

    public static boolean dos2unixFileFixer(String filePath) throws IOException, InterruptedException {
        // bash es muy sensible a los cambios de linea \r\n de Windows. Para
        // prevenir que esto cause Runtime Errors, es necesario convertirlos a
        // un sistema comprensible por ubuntu: \n normal en linux. El comando
        // dos2unix hace esto.
        // se lo dejamos a todos los codigos para evitar que algun otro lenguaje
        // tambien padezca de esto

        Properties langProps = new Properties();
        langProps.load(ClassLoader
                .getSystemResourceAsStream("uengine.properties"));

        String dos2unixPath = langProps.getProperty("dos2unix.path");

        ProcessBuilder pb = new ProcessBuilder(dos2unixPath, filePath);
        Process process = pb.start();
        process.waitFor();

        return process.exitValue() == 0;
    }
}
