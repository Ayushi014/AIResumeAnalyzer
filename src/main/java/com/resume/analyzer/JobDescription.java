package com.resume.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JobDescription {

    public static String readJD(String path) {
        String jdText = "";
        try {
            jdText = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            System.out.println("Error reading JD file: " + e.getMessage());
        }
        return jdText;
    }
}
