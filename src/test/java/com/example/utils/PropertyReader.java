package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
		private String fileName;
		public PropertyReader(String fileName){
				this.fileName = fileName;
		}
		public Properties readPropertyFile() throws IOException {
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				Properties props = new Properties();
				InputStream resourceStream = loader.getResourceAsStream(this.fileName);
				props.load(resourceStream);
				return props;
		}
}
