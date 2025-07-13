package com.resume.analyzer;

import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/resume_analyzer";
    private static final String USER = "root"; // change if different
    private static final String PASSWORD = "Ayushi@9190"; // replace this

    public static void insertResult(String resumeName, List<String> matchedSkills, int score) {
        String sql = "INSERT INTO analysis_results (resume_name, matched_skills, match_score) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resumeName);
            stmt.setString(2, String.join(", ", matchedSkills));
            stmt.setInt(3, score);

            stmt.executeUpdate();
            System.out.println("✅ Analysis result saved to database.");
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
    }
}
