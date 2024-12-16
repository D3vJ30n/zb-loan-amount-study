package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTestService {
    private final RedissonClient redissonClient;

    public void lock(String accountNumber) {
        RLock lock = redissonClient.getLock(getLockKey(accountNumber));
        try {
            boolean isLock = lock.tryLock(1, 15, TimeUnit.SECONDS);
            if (!isLock) {
                throw new RuntimeException("Lock acquisition failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Redis lock failed");
        }
    }

    public void unlock(String accountNumber) {
        RLock lock = redissonClient.getLock(getLockKey(accountNumber));
        try {
            lock.unlock();
        } catch (Exception e) {
            throw new RuntimeException("Redis unlock failed");
        }
    }

    private String getLockKey(String accountNumber) {
        return "ACLK:" + accountNumber;
    }
}
