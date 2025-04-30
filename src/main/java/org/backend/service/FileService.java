package org.backend.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.tika.Tika;
import org.backend.tactics.maintain_multiple_copies.CacheTacticGrammaire;
import org.backend.tactics.onetime_password.OneTimePasswordTacticGrammar;
import org.backend.tactics.ping_echo.PingEchoTacticGrammaire;
import org.backend.tactics.ID_password_authentication.IDPasswordTacticGrammar;
import org.backend.tactics.maintain_data_confidentiality.MaintainDataConfidentialityParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.util.Date;
@Service
public class FileService {

    private final String UPLOAD_DIR = "uploads";
    private final Pattern FORMAT_PATTERN = Pattern.compile("^CALLER:.*?,METHOD:.*?,CALLEE:.*?;$");

    public List<Integer> processAndValidate(MultipartFile file, String tacticName) throws Exception {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(fileName);

        // Check extension validity
        if (!isValidExtension(extension)) {
            throw new IllegalArgumentException("Unsupported file type. Only .pdf, .docx, .txt are allowed.");
        }

        // Extract text from file
        String text = extractText(file, extension);

        // Validate format
        List<Integer> errorLineNumbers = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            boolean isInvalid = false;

            if (!line.isEmpty() && !FORMAT_PATTERN.matcher(line).matches()) {
                isInvalid = true;
            } else {
                // Check if there's anything after the single semicolon
                if (line.endsWith(";") && line.indexOf(';', line.indexOf(';') + 1) != -1) {
                    isInvalid = true;
                }
            }

            if (isInvalid) {
                errorLineNumbers.add(i + 1); // Store line number (1-based)
            }
        }

        return errorLineNumbers; // Just the problematic line numbers
    }


    public String extractText(MultipartFile file, String extension) throws Exception {
        InputStream inputStream = file.getInputStream();

        if ("pdf".equalsIgnoreCase(extension) || "docx".equalsIgnoreCase(extension)) {
            Tika tika = new Tika();
            return tika.parseToString(inputStream);
        } else if ("txt".equalsIgnoreCase(extension)) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }
        return "";
    }

    private boolean isValidExtension(String ext) {
        return "pdf".equalsIgnoreCase(ext) || "docx".equalsIgnoreCase(ext) || "txt".equalsIgnoreCase(ext);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    public String runTacticParser(String tacticName, MultipartFile file) throws Exception {
        String originalText = extractText(file, getExtension(file.getOriginalFilename()));

        // Clean input: keep only allowed characters (letters, numbers, punctuation used in your grammar)
        String cleanedText = originalText
                .replaceAll("[^a-zA-Z0-9:\\s,.;]", "") // remove weird symbols
                .replaceAll("[\\r]", "")               // remove carriage returns
                .replaceAll("\\s+", " ")               // normalize all space-like things to a single space
                .replaceAll("\\s*:\\s*", ":")          // remove spaces around colon
                .replaceAll("\\s*,\\s*", ",")          // remove spaces around comma
                .replaceAll("\\s*;\\s*", ";")          // remove spaces around semicolon
                .trim();
        InputStream inputStream = new ByteArrayInputStream(cleanedText.getBytes(StandardCharsets.UTF_8));

        return switch (tacticName.toLowerCase()) {
            case "maintain_multiple_copies" -> {
                CacheTacticGrammaire parser = new CacheTacticGrammaire(inputStream);
                yield parser.parseAndGetResult(inputStream);
            }
            case "ping_echo" -> {
                PingEchoTacticGrammaire parser = new PingEchoTacticGrammaire(inputStream);
                yield parser.parseAndGetResult(inputStream);
            }
            case "maintain_data_confidentiality" -> {
                MaintainDataConfidentialityParser parser = new MaintainDataConfidentialityParser(inputStream);
                yield parser.parseAndGetResult(inputStream);
            }
            case "id_password_authentication" -> {
                IDPasswordTacticGrammar parser = new IDPasswordTacticGrammar(inputStream);
                yield parser.parseAndGetResult(inputStream);
            }
            case "onetime_password" -> {
                OneTimePasswordTacticGrammar parser = new OneTimePasswordTacticGrammar(inputStream);
                yield parser.parseAndGetResult(inputStream);
            }
            default -> throw new IllegalArgumentException("Unknown tactic: " + tacticName);
        };


    }

    public String saveAsTxt(String content) throws IOException {
        try {
            File folder = new File("downloads");
            if (!folder.exists()) folder.mkdirs();

            String fileName = "parsed_" + UUID.randomUUID() + ".txt";
            File txtFile = new File(folder, fileName);
            Files.write(txtFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
            return fileName;
        } catch (IOException e) {
            throw new IOException("❌ Failed to save TXT file: " + e.getMessage(), e);
        }
    }
    public String saveAsPdf(String content) throws IOException {
        File folder = new File("downloads");
        if (!folder.exists()) folder.mkdirs();

        String fileName = "parsed_" + UUID.randomUUID() + ".pdf";
        File pdfFile = new File(folder, fileName);

        try (OutputStream out = new FileOutputStream(pdfFile)) {
            Document doc = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(doc, out);

            // Watermark handler
            class CenteredDiagonalLogoEvent extends PdfPageEventHelper {
                private Image background;

                public CenteredDiagonalLogoEvent() {
                    try {
                        InputStream logoStream = getClass().getClassLoader().getResourceAsStream("Screenshot 2025-04-29 112547.png");
                        if (logoStream != null) {
                            byte[] logoBytes = logoStream.readAllBytes();
                            background = Image.getInstance(logoBytes);
                            background.scaleAbsolute(800, 500); 
                            background.setRotationDegrees(65);  
                        }
                    } catch (Exception e) {
                        System.out.println("⚠️ Failed to load background: " + e.getMessage());
                    }
                }

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    if (background != null) {
                        PdfContentByte canvas = writer.getDirectContentUnder();

                        // Light opacity
                        PdfGState gState = new PdfGState();
                        gState.setFillOpacity(0.15f);
                        canvas.setGState(gState);

                        // Position in center of page
                        float centerX = (document.left() + document.right()) / 2;
                        float centerY = (document.top() + document.bottom()) / 2;
                        float x = centerX - (background.getScaledWidth() / 2);
                        float y = centerY - (background.getScaledHeight() / 2);

                        background.setAbsolutePosition(x, y);

                        try {
                            canvas.addImage(background);
                        } catch (DocumentException e) {
                            System.out.println("⚠️ Failed to add logo: " + e.getMessage());
                        }
                    }

                    // Footer
                    PdfContentByte cb = writer.getDirectContent();
                    Font footerFont = new Font(Font.FontFamily.HELVETICA, 8);
                    Phrase footer = new Phrase("Generated on: " + new Date(), footerFont);
                    ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer, document.leftMargin(), document.bottomMargin() - 10, 0);

                    Phrase pageNum = new Phrase("Page " + writer.getPageNumber(), footerFont);
                    ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, pageNum, document.right() - document.rightMargin(), document.bottomMargin() - 10, 0);
                }
            }

            writer.setPageEvent(new CenteredDiagonalLogoEvent());

            doc.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("📊 Tactic Analysis Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            doc.add(title);

            // Body
            Font monoFont = new Font(Font.FontFamily.COURIER, 12);
            for (String line : content.split("\n")) {
                line = line.trim();
                if (line.startsWith("==>")) {
                    Paragraph p = new Paragraph(line, new Font(Font.FontFamily.COURIER, 12, Font.BOLD));
                    p.setSpacingBefore(10f);
                    doc.add(p);
                } else if (line.startsWith("===")) {
                    doc.add(new Chunk(new LineSeparator()));
                } else if (line.toLowerCase().contains("tactic participating objects")) {
                    Paragraph p = new Paragraph("\n" + line, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC));
                    p.setSpacingBefore(8f);
                    doc.add(p);
                } else {
                    doc.add(new Paragraph(line, monoFont));
                }
            }

            doc.close();
            return fileName;

        } catch (Exception e) {
            throw new IOException("❌ Failed to save PDF file: " + e.getMessage(), e);
        }
    }

}
