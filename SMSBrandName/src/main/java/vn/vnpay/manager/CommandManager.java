package vn.vnpay.manager;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.vnpay.connect.RedisConnectionManager;

import java.util.concurrent.ExecutionException;

public class CommandManager {
    private static final Logger LOG = LogManager.getLogger(CommandManager.class);
    private static final int NUMBER_RETRY = 3;
    private static CommandManager instance;

    public static CommandManager getInstance() {
        if (null == instance) {
            synchronized (CommandManager.class) {
                if (null == instance) {
                    instance = new CommandManager();
                }
            }
        }
        return instance;
    }

    private CommandManager() {
    }

    public boolean start() {
        return RedisConnectionManager.getInstance().start();
    }

    public void shutdown() {
        RedisConnectionManager.getInstance().shutdown();
    }

    public String set(String key, String value) {
        String valReturn = "";
        int retry = 0;
        while (retry < NUMBER_RETRY) {
            try (StatefulRedisConnection<String, String> connection = RedisConnectionManager.getInstance().getConnection()) {
                if (connection == null || !connection.isOpen()) {
                    LOG.warn("Set value from key: {} value: {} fail because connection to CacheServer fail.", key, value);
                    retry++;
                    continue;
                }
                LOG.info("Set key: {}, value: {}", key, value);
                RedisFuture<String> setFuture = connection.async().set(key, value);
                valReturn = setFuture.get();
                break;
            } catch (InterruptedException | ExecutionException ex) {
                LOG.error("Set value from key: {} fail.", key, ex);
                retry++;
            }
        }
        return valReturn;
    }

    public String get(String key) {
        String valReturn = "";
        try (StatefulRedisConnection<String, String> connection = RedisConnectionManager.getInstance().getConnection()) {
            if (connection == null || !connection.isOpen()) {
                LOG.warn("Get value from key: {} fail because connection to CacheServer fail.", key);
                return valReturn;
            }
            RedisFuture<String> setFuture = connection.async().get(key);
            valReturn = setFuture.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOG.error("Get value from key: {} fail.", key, ex);
        }
        return valReturn;
    }

    public void remove(String key) {
        try (StatefulRedisConnection<String, String> connection = RedisConnectionManager.getInstance().getConnection()) {
            if (connection == null || !connection.isOpen()) {
                LOG.warn("Remove value from key {} fail because connection to CacheServer fail.", key);
                return;
            }
            connection.async().del(key);
        } catch (Exception ex) {
            LOG.error("Remove value from key: {} fail.", key, ex);
        }
    }

    public boolean setnx(String key, String value, long timeout) {
        boolean valReturn = false;
        try (StatefulRedisConnection<String, String> connection = RedisConnectionManager.getInstance().getConnection()) {
            if (connection == null || !connection.isOpen()) {
                LOG.warn("Setnx value to key {} fail because connection to CacheServer fail.", key);
                return false;
            }
            RedisFuture<Boolean> setnxFuture = connection.async().setnx(key, value);
            valReturn = setnxFuture.get();
            if (valReturn && timeout > 0) {
                connection.async().expire(key, timeout);
            }
        } catch (Exception ex) {
            LOG.error("Setnx value to key {} fail: ", key, ex);
        }
        return valReturn;
    }
}
