package com.cobra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LockService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    public Boolean lock(String key, Long expire, TimeUnit unit){
        Boolean result = null;
        try {
            result = redisTemplate.opsForValue().setIfAbsent(key, Thread.currentThread().getName(), expire, unit);
        } catch (Exception e) {
            System.out.println("lock error");
            result = false;
        }
        return  result;
    }


    public void unlock(String key){
        redisTemplate.delete(key);
    }
}
