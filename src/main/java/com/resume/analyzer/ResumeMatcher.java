package com.resume.analyzer;

import java.util.*;
import java.util.stream.Collectors;

public class ResumeMatcher {

    public double match(String resumeText, String jobDescription) {
        List<String> resumeWords = tokenize(resumeText);
        List<String> jobDescWords = tokenize(jobDescription);

        Set<String> allWords = new HashSet<>();
        allWords.addAll(resumeWords);
        allWords.addAll(jobDescWords);

        Map<String, Integer> resumeVector = getVector(allWords, resumeWords);
        Map<String, Integer> jobDescVector = getVector(allWords, jobDescWords);

        return cosineSimilarity(resumeVector, jobDescVector);
    }

    private List<String> tokenize(String text) {
        return Arrays.stream(text.toLowerCase().split("\\W+"))
                .filter(word -> word.length() > 1)
                .collect(Collectors.toList());
    }

    private Map<String, Integer> getVector(Set<String> allWords, List<String> words) {
        Map<String, Integer> vector = new HashMap<>();
        for (String word : allWords) {
            vector.put(word, Collections.frequency(words, word));
        }
        return vector;
    }

    private double cosineSimilarity(Map<String, Integer> vec1, Map<String, Integer> vec2) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String key : vec1.keySet()) {
            int x = vec1.get(key);
            int y = vec2.get(key);
            dotProduct += x * y;
            normA += Math.pow(x, 2);
            normB += Math.pow(y, 2);
        }

        if (normA == 0 || normB == 0) return 0.0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
