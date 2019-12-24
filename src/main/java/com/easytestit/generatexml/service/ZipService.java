package com.easytestit.generatexml.service;

import com.easytestit.generatexml.data.ConfigDataProvider;
import lombok.NonNull;
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
public class ZipService {

    private static final Logger LOGGER = LogManager.getLogger(ZipService.class.getName());

    public boolean createZip() {
        LOGGER.log(Level.INFO, "Start to create new ZIP archive in project root directory");
        boolean isZipCreated = false;
        try {
            FileOutputStream fos = new FileOutputStream(ConfigDataProvider.ZIP_NAME);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(ConfigDataProvider.REPORT_RESULTS_FOLDER.concat(ConfigDataProvider.FILE_NAME));

            isZipCreated = zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            LOGGER.log(Level.INFO, "ZIP was created");
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }

        return isZipCreated;
    }

    private boolean zipFile(
            @NonNull final File fileToZip,
            @NonNull final String fileName,
            @NonNull final ZipOutputStream zipOut
    ) {
        try {
            if (fileToZip.isHidden()) {
                LOGGER.log(Level.INFO, "File which located in ".concat(ConfigDataProvider.REPORT_RESULTS_FOLDER).concat(" is hidden"));
                return false;
            }
            if (fileToZip.isDirectory()) {
                LOGGER.log(Level.INFO, "File which located in ".concat(ConfigDataProvider.REPORT_RESULTS_FOLDER).concat(" is directory"));

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
                return true; //TODO: is it true?
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
            LOGGER.log(Level.ERROR, e.getMessage());
        }

        return true;
    }
}
