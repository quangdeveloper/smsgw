package vn.vnpay.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RedisProperties {
    private static final Logger LOG = LogManager.getLogger(RedisProperties.class);

    private static Properties properties;

    private RedisProperties() {
    }

    public static Properties getProperties() {
        if (null == properties) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("D:/Project/SMS-CLONE/SMSBrandName/src/main/resources/redis.properties"));
                LOG.info("Load file ./config/redis.properties success");
            } catch (IOException e) {
                LOG.error("Error load file ./config/redis.properties: ", e);
            }
        }
        return properties;
    }
}
