package vn.vnpay.connect;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.HostAndPort;
import vn.vnpay.common.RedisProperties;
import vn.vnpay.config.RedisConfig;

import java.time.Duration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class RedisConnectionManager {
    private static final Logger LOG = LogManager.getLogger(RedisConnectionManager.class);

    private RedisClient client;
    private RedisURI redisURI;
    private GenericObjectPool<StatefulRedisConnection<String, String>> pool;

    private static RedisConnectionManager instance;

    private RedisConnectionManager() {
    }

    public static RedisConnectionManager getInstance() {
        if (null == instance) {
            synchronized (RedisConnectionManager.class) {
                if (null == instance) {
                    instance = new RedisConnectionManager();
                }
            }
        }
        return instance;
    }

    public boolean start() {
        RedisURI.Builder builder = null;
        final Properties properties = RedisProperties.getProperties();
        String[] sentinelConfigs = properties.getProperty(RedisConfig.REDIS_MASTER).split(",");
        if (sentinelConfigs.length <= 0) {
            LOG.warn("Please valid sentinel config");
            return false;
        }
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        try {
            for (String config : sentinelConfigs) {
                String host = config.split(":")[0];
                int port = Integer.parseInt(config.split(":")[1].trim());
                hostAndPorts.add(new HostAndPort(host, port));
            }
        } catch (NumberFormatException e) {
            LOG.error("Please valid sentinel config. ", e);
            return false;
        }

        if (hostAndPorts.isEmpty()) {
            LOG.warn("Please valid sentinel config.");
            return false;
        }
        for (HostAndPort hostAndPort : hostAndPorts) {
            if (builder == null) {
                builder = RedisURI.Builder.sentinel(hostAndPort.getHost(),
                        hostAndPort.getPort(), properties.getProperty(RedisConfig.REDIS_MASTER_NAME));
            } else {
                builder.withSentinel(hostAndPort.getHost(), hostAndPort.getPort());
            }
        }
        if (builder == null) {
            LOG.warn("Please valid sentinel config.");
            return false;
        }
        builder.withPassword(properties.getProperty(RedisConfig.REDIS_PASSWORD)).withDatabase(Integer.parseInt(properties.getProperty(RedisConfig.MAX_DATABASE).trim()))
                .withSentinelMasterId(properties.getProperty(RedisConfig.REDIS_MASTER_NAME));
        redisURI = builder.build();
        client = RedisClient.create(redisURI);
        client.setOptions(ClusterClientOptions.builder()
                .autoReconnect(Boolean.parseBoolean(properties.getProperty(RedisConfig.RECONNECT).trim()))
                .pingBeforeActivateConnection(Boolean.parseBoolean(properties.getProperty(RedisConfig.PING_BEFORE_ACTIVE).trim())).build());
        client.setDefaultTimeout(Duration.ofMinutes(Integer.parseInt(properties.getProperty(RedisConfig.TIME_OUT).trim())));
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(properties.getProperty(RedisConfig.MAX_TOTAL).trim()));
        poolConfig.setMaxIdle(Integer.parseInt(properties.getProperty(RedisConfig.MAX_IDLE).trim()));
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty(RedisConfig.TEST_ON_BORROW).trim()));
        pool = ConnectionPoolSupport.createGenericObjectPool(
                () -> client.connect(), poolConfig);
        LOG.info("Redis: Start connection pool successful.");
        return true;
    }

    public StatefulRedisConnection<String, String> getConnection() {
        try {
            return pool.borrowObject();
        } catch (Exception ex) {
            LOG.error("Get connection from pool fail.", ex);
            return null;
        }
    }

    public void shutdown() {
        try {
            pool.clear();
            pool.close();
            client.shutdown();
        } catch (Exception ex) {
            LOG.error("Shutdown Redis connection pool fail", ex);
        }
    }
}
