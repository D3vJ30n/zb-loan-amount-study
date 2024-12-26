package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// Lombok을 사용하여 생성자 자동 생성
@RequiredArgsConstructor
// 스프링 설정 클래스임을 나타내는 어노테이션
@Configuration
public class CacheConfig {

    // application.properties/yml에서 redis 호스트 주소를 가져옴
    @Value("${spring.redis.host}")
    private String host;

    // application.properties/yml에서 redis 포트 번호를 가져옴
    @Value("${spring.redis.port}")
    private int port;

    // Redis 캐시 매니저 설정
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        // Redis 캐시 기본 설정
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
            // 캐시 키는 문자열로 직렬화
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            // 캐시 값은 JSON 형태로 직렬화
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // Redis 캐시 매니저 생성 및 반환
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(conf)
            .build();
    }

    // Redis 연결 설정
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 단독 서버 설정
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
        // Redis 서버 호스트 설정
        conf.setHostName(this.host);
        // Redis 서버 포트 설정
        conf.setPort(this.port);
        // Lettuce 라이브러리를 사용하여 Redis 연결 팩토리 생성 및 반환
        return new LettuceConnectionFactory(conf);
    }
}