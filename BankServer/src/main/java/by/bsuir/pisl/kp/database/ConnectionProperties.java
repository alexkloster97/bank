package by.bsuir.pisl.kp.database;

import java.io.IOException;
import java.util.Properties;

final class ConnectionProperties {

    private static final String DEFAULT_SOURCE_CONFIG = "dbConnect.properties";

    private Properties properties;


    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    ConnectionProperties() {
        this(DEFAULT_SOURCE_CONFIG);
    }

    ConnectionProperties(String pathToProperties) {
        try {
            properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(pathToProperties));
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException("Unable to find db properties config file.");
        }
    }
}
