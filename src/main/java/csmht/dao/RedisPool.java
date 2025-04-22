package csmht.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class RedisPool {
        private static  final JedisPool jedisPool;

        static {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

            jedisPoolConfig.setMaxTotal(5);
            jedisPoolConfig.setMaxIdle(5);
            jedisPoolConfig.setMinIdle(1);
            jedisPoolConfig.setMaxWait(Duration.ofSeconds(3));
            jedisPool = new JedisPool(jedisPoolConfig,"10.44.252.185",6379,1000);
        }

        public static Jedis getJedis() {
            return jedisPool.getResource();
        }
}
