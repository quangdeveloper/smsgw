package vn.vnpay.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiProperties {
    private static final Logger LOG = LogManager.getLogger(ApiProperties.class);

    private static Properties properties;

    private ApiProperties() {
    }

    public static Properties getProperties() {
        if (null == properties) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("D:/Project/SMS-CLONE/SMSBrandName/src/main/resources/dev.properties"));
                LOG.info("Load file ./config/dev.properties success");
            } catch (IOException e) {
                LOG.error("Error load file ./config/dev.properties: ", e);
            }
        }
        return properties;
    }
}
