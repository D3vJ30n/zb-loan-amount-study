package com.example.demo.config;

import org.redisson.Redisson; // Redisson 라이브러리의 Redisson 클래스를 가져옴
import org.redisson.api.RedissonClient; // Redisson 라이브러리의 RedissonClient 클래스를 가져옴
import org.redisson.config.Config; // Redisson 라이브러리의 Config 클래스를 가져옴
import org.springframework.beans.factory.annotation.Value; // Spring의 Value 어노테이션을 가져옴
import org.springframework.context.annotation.Bean; // Bean 등록을 위한 어노테이션
import org.springframework.context.annotation.Configuration; // Spring의 Configuration 클래스임을 나타냄

/**
 * RedisConfig 클래스는 Redis와 연동하기 위한 설정을 정의한다.
 * 이 클래스는 Redisson 라이브러리를 사용하여 Redis 클라이언트를 생성하며,
 * Spring Context에서 관리되는 Bean으로 등록한다.
 */
@Configuration // 이 어노테이션은 해당 클래스가 Spring Configuration 파일임을 나타냄
public class RedisConfig {
    /**
     * Redis 서버의 호스트 주소를 설정 파일(application.yml)에서 가져온다.
     */

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    /**
     * RedissonClient 객체를 생성하여 Bean으로 등록한다.
     * 이 Bean은 Redis와의 상호작용을 가능하게 하는 클라이언트로 사용된다.
     *
     * @return RedissonClient 인스턴스
     */
    @Bean // 해당 메서드가 반환하는 객체를 Spring 컨텍스트에서 Bean으로 관리하도록 지정
    public RedissonClient redissonClient() {
        // Redisson의 Config 객체를 생성한다. 이는 Redisson 클라이언트 설정 정보를 포함한다.
        Config config = new Config();

        // Redis 서버를 단일 서버 모드로 설정한다.
        // setAddress 메서드는 Redis 서버의 주소를 "redis://<호스트>:<포트>" 형식으로 지정한다.
        config.useSingleServer()
            .setAddress("redis://" + redisHost + ":" + redisPort);

        // 생성된 Config 객체를 기반으로 RedissonClient 인스턴스를 생성하여 반환한다.
        // Redisson.create() 메서드는 내부적으로 연결 풀을 구성하고 Redis 서버와 통신 가능하도록 클라이언트를 초기화한다.
        return Redisson.create(config);
    }
}
