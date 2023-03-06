package akhid.development.service;

import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.quarkus.scheduler.Scheduled;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RedisPublisher {
    @Inject
    ReactiveRedisClient redisClient;

    @Scheduled(every = "PT5S")
    public void publishRedis() {
        redisClient.publish("day", "friday night");
        redisClient.publish("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
    }
}
