package org.backend.controller;

import org.backend.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class FileUploadController {

    private final FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    // Endpoint to handle file upload along with the tactic name
    @PostMapping("/upload/{tacticName}")
    public ResponseEntity<?> handleFileUpload(@PathVariable("tacticName") String tacticName,
                                              @RequestParam("file") MultipartFile file) {
        try {
            var errorLines = fileService.processAndValidate(file, tacticName);

            if (!errorLines.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", true,
                        "message", "❌ Format errors found.",
                        "invalidLines", errorLines  // <--- clearer to frontend
                ));
            }

            if ("maintain_multiple_copies".equalsIgnoreCase(tacticName)
                    || "ping_echo".equalsIgnoreCase(tacticName)
                    || "maintain_data_confidentiality".equalsIgnoreCase(tacticName)
                    || "id_password_authentication".equalsIgnoreCase(tacticName)
                    || "onetime_password".equalsIgnoreCase(tacticName)) {

                String result = fileService.runTacticParser(tacticName, file);
                String txtFileName = fileService.saveAsTxt(result);
                String pdfFileName = fileService.saveAsPdf(result);

                return ResponseEntity.ok(Map.of(
                        "message", "✅ File is valid.",
                        "parsedResult", result,
                        "txtDownloadUrl", "/api/files/download/" + txtFileName,
                        "pdfDownloadUrl", "/api/files/download/" + pdfFileName
                ));
            }

            return ResponseEntity.ok("✅ File processed and validated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", true, "message", "❌ Error: " + e.getMessage()));
        }
    }


    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) throws IOException {
        File file = new File("downloads/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] content = Files.readAllBytes(file.toPath());
        String contentType = filename.endsWith(".pdf") ? "application/pdf" : "text/plain";

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .header("Content-Type", contentType)
                .body(content);
    }
}


