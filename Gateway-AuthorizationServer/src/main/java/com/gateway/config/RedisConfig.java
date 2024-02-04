package com.gateway.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisInfo redisInfo;

    @Value("${token.timer}")
    private long tokenTimer;

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate() {

        ReactiveRedisConnectionFactory connectionFactory = connectionFactory();

        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
                .<String, Object>newSerializationContext(new GenericJackson2JsonRedisSerializer())
                .key(stringRedisSerializer)
                .value(serializer)
                .hashKey(serializer)
                .hashValue(serializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }
    @Bean
    @Primary //
    public ReactiveRedisConnectionFactory connectionFactory() {
        // 클러스터 호스트 세팅
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisInfo.getNodes());

        // topology 자동 업데이트 옵션 추가
        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAllAdaptiveRefreshTriggers()  // MOVED, ASK, PERSISTENT_RECONNECTS, UNCOVERED_SLOT, UNKOWN_NODE trigger시 refresh 진행
                .enablePeriodicRefresh(Duration.ofHours(1L)) //1시간 마다 해당 Refresh 설정 사용
                .build();
        //clientOption 추가
        ClientOptions clientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
                .build();

        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .clientOptions(clientOptions)
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .build();

        redisClusterConfiguration.setMaxRedirects(redisInfo.getMaxRedirects());
//        redisClusterConfiguration.setPassword(redisInfo.getPassword());
        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
    }
}

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.data.redis.cluster")
@Configuration
class RedisInfo {
    private int maxRedirects;
//    private String password;
    private List<String> nodes;
}