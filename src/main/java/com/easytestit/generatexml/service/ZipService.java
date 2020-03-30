package com.easytestit.generatexml.service;

import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.data.ConfigDataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FileToZip used for creating ZIP archive
 */
public class ZipService {

    public boolean createZip(final String projectName) {
        boolean isZipCreated;
        if (projectName != null) {
            try {
                FileOutputStream fos = new FileOutputStream(ConfigDataProvider.REPORT_RESULTS_FOLDER.concat(projectName + ConfigDataProvider.ZIP_EXTENSION));
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(ConfigDataProvider.REPORT_RESULTS_FOLDER.concat(projectName + ConfigDataProvider.ZIP_EXTENSION));

                isZipCreated = zipFile(fileToZip, fileToZip.getName(), zipOut);
                zipOut.close();
                fos.close();
            } catch (IOException e) {
                throw new GenerateXMLReportException("ZIP wasn't created, because of: ");
            }

            return isZipCreated;
        } else {
            throw new GenerateXMLReportException("Argument projectName should be null but is null");
        }
    }

    private boolean zipFile(final File fileToZip, final String fileName, final ZipOutputStream zipOut) {
        if (fileToZip != null & fileName != null & zipOut != null) {
            try {
                if (fileToZip.isHidden()) {
                    throw new GenerateXMLReportException("File which located in ".concat(ConfigDataProvider.REPORT_RESULTS_FOLDER).concat(" is hidden"));
                }

                if (fileToZip.isDirectory()) {
                    if (fileName.endsWith(String.valueOf(File.separatorChar))) {
                        zipOut.putNextEntry(new ZipEntry(fileName));
                    } else {
                        zipOut.putNextEntry(new ZipEntry(fileName + File.separatorChar));
                    }
                    zipOut.closeEntry();
                    File[] children = fileToZip.listFiles();
                    for (File childFile : children != null ? children : new File[0]) {
                        zipFile(childFile, fileName + File.separatorChar + childFile.getName(), zipOut);
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
            } catch (IOException e) {
                throw new GenerateXMLReportException("FileInputString throw input/output exception during try to create a ZIP file");
            }

            return true;
        } else {
            throw new GenerateXMLReportException("Arguments fileToZip, fileName and zipOut should be null but are null");
        }
    }
}
