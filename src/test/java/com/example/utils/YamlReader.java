package com.example.utils;

import com.example.model.Config;
import com.example.model.TestData;
import org.yaml.snakeyaml.Yaml;

public class YamlReader {
    private String filePath;

    public YamlReader(String filePath) {
        this.filePath = filePath;
    }

    public Config readConfig() {
        return new Yaml().loadAs(FileReader.read(filePath), Config.class);
    }

    public TestData readTestData() {
        return new Yaml().loadAs(FileReader.read(filePath), TestData.class);
    }

}
