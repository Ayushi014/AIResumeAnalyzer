package com.resume.analyzer;

import javax.swing.*;

public class ResumeAnalyzerGUI {
    private String resumePath = null;  // <-- OUTSIDE constructor
    JFrame frame;
    JTextArea jobDescriptionArea, resultArea;
    JButton uploadButton, analyzeButton;

    public ResumeAnalyzerGUI() {
        frame = new JFrame("AI Resume Analyzer");
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jobDescLabel = new JLabel("Job Description:");
        jobDescLabel.setBounds(30, 20, 150, 30);
        frame.add(jobDescLabel);

        jobDescriptionArea = new JTextArea();
        jobDescriptionArea.setBounds(30, 50, 500, 100);
        frame.add(jobDescriptionArea);

        uploadButton = new JButton("Upload Resume");
        uploadButton.setBounds(30, 160, 150, 30);
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);

            if (option == JFileChooser.APPROVE_OPTION) {
                resumePath = fileChooser.getSelectedFile().getAbsolutePath();
                resultArea.setText("✅ Resume Uploaded:\n" + resumePath);
            } else {
                resultArea.setText("⚠️ Resume upload cancelled.");
            }
        });

        frame.add(uploadButton);

        analyzeButton = new JButton("Analyze");
        analyzeButton.setBounds(200, 160, 100, 30);

        analyzeButton.addActionListener(e -> {
            if (resumePath == null) {
                resultArea.setText("⚠️ Please upload a resume first.");
                return;
            }

            String jobDescription = jobDescriptionArea.getText().trim();
            if (jobDescription.isEmpty()) {
                resultArea.setText("⚠️ Please enter the job description.");
                return;
            }

            String result = analyzeResume(resumePath, jobDescription);
            resultArea.setText(result);
        });

        frame.add(analyzeButton);

        resultArea = new JTextArea();
        resultArea.setBounds(30, 210, 500, 300);
        resultArea.setEditable(false);
        frame.add(resultArea);

        frame.setVisible(true);
    }
    private String analyzeResume(String resumePath, String jobDescription) {
        try {
            ResumeParser parser = new ResumeParser();  // your existing class
            String resumeText = parser.extractTextFromPDF(resumePath);

            ResumeMatcher matcher = new ResumeMatcher();  // new class you just created
            double score = matcher.match(resumeText, jobDescription);

            return "✅ Match Score: " + String.format("%.2f", score * 100) + "%";
        } catch (Exception e) {
            return "❌ Error analyzing resume: " + e.getMessage();
        }
    }


    public static void main(String[] args) {
        new ResumeAnalyzerGUI();
    }
}

