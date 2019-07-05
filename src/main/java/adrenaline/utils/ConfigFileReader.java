package adrenaline.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class to read config files.
 */

public class ConfigFileReader {
    private static final String PATH1 = "src" + File.separatorChar + "Resources" + File.separatorChar + "config.properties";

    public ConfigFileReader(){}

    /**
     * Method to read from the Configuration File
     * @param key is the name of the value you want to read
     * @return a int type
     */
    public static int readConfigFile(String key) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(PATH1);
            prop.load(in);
            int i = Integer.parseInt(prop.getProperty(key));
            return i;
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return -1;
    }

    public static String readConfigFileString(String key){
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(PATH1);
            prop.load(in);
            String s = prop.getProperty(key);
            return s;
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return null;
    }
}
