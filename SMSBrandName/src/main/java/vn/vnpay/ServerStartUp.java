package vn.vnpay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.vnpay.common.ApiProperties;
import vn.vnpay.manager.CommandManager;
import vn.vnpay.shutdown.ShutDownHook;

@SpringBootApplication
public class ServerStartUp implements DisposableBean, CommandLineRunner {
//    static {
//        System.setProperty("log4j.configurationFile", "./config/log4j2.xml");
//    }

    private static final Logger LOG = LogManager.getLogger(ServerStartUp.class);

    public static void main(String[] args) {
        try {
            SpringApplication application = new SpringApplication(ServerStartUp.class);
            application.setDefaultProperties(ApiProperties.getProperties());
            application.run(args);
        } catch (Exception e) {
            LOG.error("Error start server ", e);
        }
    }

    @Override
    public void run(String... args) {
        if (!CommandManager.getInstance().start()) {
            LOG.warn("Cannot connect Redis");
            return;
        }
        ShutDownHook.shutdownHook();
    }

    @Override
    public void destroy() {

    }
}

