package com.mshri.gradlelab;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties prop = new Properties();
    static String propFileName = "config.properties";
    static InputStream inputStream;

    static{
        try {
            inputStream = Config.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            inputStream.close();
            
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
