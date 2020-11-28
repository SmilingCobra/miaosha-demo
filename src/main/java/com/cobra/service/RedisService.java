package com.cobra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    public Long decrement(String key){
        Long result = null;
        result =Long.valueOf(redisTemplate.opsForValue().get(key).toString());
        if(result <= 0 ){
            return null;
        }
        try {

            result = redisTemplate.opsForValue().decrement(key);
            System.out.println(String.format("decrement  key is %s,result is %s ",key,result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
