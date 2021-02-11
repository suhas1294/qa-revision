package com.example.utils;

import java.io.InputStream;

public class FileReader {
    public static InputStream read(String filePath) {
        ClassLoader classLoader = FileReader.class.getClassLoader();
        return classLoader.getResourceAsStream(filePath);
    }
}