package com.skypro.recommender.utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class FileReaderUtil {

    public String readText(String filePath) {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(filePath)) {
            if (is == null) {
                return "Файл не найден по пути: " + filePath;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();
            }
        } catch (Exception e) {
            return "Ошибка чтения файла.";
        }
    }
}
