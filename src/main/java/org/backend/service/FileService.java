package org.backend.service;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "uploads";

    public String saveAndConvertFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File outputFile = new File(UPLOAD_DIR + "/" + UUID.randomUUID() + ".txt");

        String extension = getExtension(fileName);

        InputStream inputStream = file.getInputStream();
        String extractedText;

        if ("pdf".equalsIgnoreCase(extension) || "docx".equalsIgnoreCase(extension)) {
            Tika tika = new Tika();
            extractedText = tika.parseToString(inputStream);
        } else if ("txt".equalsIgnoreCase(extension)) {
            extractedText = new String(file.getBytes(), StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + extension);
        }

        Files.write(outputFile.toPath(), extractedText.getBytes(StandardCharsets.UTF_8));
        return outputFile.getAbsolutePath();
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}