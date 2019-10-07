package com.easytestit.generatexml.utils;

import com.easytestit.generatexml.data.DefaultData;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FileToZip used for creating ZIP archive
 */
public class FileToZip {

    private static final Logger LOGGER = LogManager.getLogger(FileToZip.class.getName());

    public FileToZip() {

    }

    public void createZip() {
        try {
            LOGGER.log(Level.INFO, "Start to create new ZIP archive in project root directory");

            String sourceFile = DefaultData.reportResultsFolder.concat(DefaultData.fileName);
            FileOutputStream fos = new FileOutputStream(DefaultData.reportFolderZipName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            LOGGER.log(Level.INFO, "ZIP was created");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) {
        try {
            if (fileToZip.isHidden()) {
                LOGGER.log(Level.INFO, "File which located in ".concat(DefaultData.reportResultsFolder).concat(" is hidden"));
                return;
            }
            if (fileToZip.isDirectory()) {
                LOGGER.log(Level.INFO, "File which located in ".concat(DefaultData.reportResultsFolder).concat(" is direction"));
                if (fileName.endsWith("/")) {
                    zipOut.putNextEntry(new ZipEntry(fileName));
                    zipOut.closeEntry();
                } else {
                    zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                    zipOut.closeEntry();
                }
                File[] children = fileToZip.listFiles();
                for (File childFile : children != null ? children : new File[0]) {
                    zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                }
                return;
            }
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        } catch (Exception e) {
            LOGGER.log(Level.DEBUG, e.getMessage());
        }
    }
}
