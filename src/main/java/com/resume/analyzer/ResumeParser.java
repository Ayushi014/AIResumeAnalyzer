package com.resume.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ResumeParser {

    // Extract text from PDF
    public static String extractTextFromPDF(String filePath) {
        String text = "";
        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
            document.close();
        } catch (IOException e) {
            System.out.println("Error reading PDF: " + e.getMessage());
        }
        return text;
    }

    public static void main(String[] args) {
        // Read resume file
        String resumePath = "src/main/resources/sample_resume.pdf";
        String resumeText = extractTextFromPDF(resumePath);
        System.out.println("Resume Content:\n" + resumeText);

        // Read JD file
        String jdPath = "src/main/resources/job_description.txt";
        String jdText = JobDescription.readJD(jdPath);
        System.out.println("\n--- Job Description ---\n" + jdText);

        // Extract skills from resume and JD
        List<String> resumeSkills = SkillMatcher.extractSkills(resumeText);
        List<String> jdSkills = SkillMatcher.extractSkills(jdText);

        // Match skills
        List<String> matchedSkills = new ArrayList<>();
        for (String skill : jdSkills) {
            if (resumeSkills.contains(skill)) {
                matchedSkills.add(skill);
            }
        }

        // Output results
        System.out.println("\n--- Matching Skills ---");
        System.out.println("Matched Skills: " + matchedSkills);

        int score = SkillMatcher.calculateMatchScore(matchedSkills);
        System.out.println("Match Score: " + score + "%");
        // Save to DB
        DatabaseManager.insertResult("sample_resume.pdf", matchedSkills, score);

    }
}