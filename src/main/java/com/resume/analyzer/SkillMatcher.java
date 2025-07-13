package com.resume.analyzer;

import java.util.*;

public class SkillMatcher {

    // Predefined list of expected skills
    private static final List<String> knownSkills = Arrays.asList(
            "java", "python", "c++", "sql", "ds", "dsa", "oops", "cloud", "html", "css", "javascript", "react", "node", "machine learning", "ai", "git", "github"
    );

    public static List<String> extractSkills(String resumeText) {
        List<String> matchedSkills = new ArrayList<>();
        String lowerText = resumeText.toLowerCase();

        for (String skill : knownSkills) {
            if (lowerText.contains(skill)) {
                matchedSkills.add(skill);
            }
        }

        return matchedSkills;
    }

    public static int calculateMatchScore(List<String> matchedSkills) {
        int totalSkills = knownSkills.size();
        return (int) ((matchedSkills.size() * 100.0) / totalSkills); // percentage
    }
}
