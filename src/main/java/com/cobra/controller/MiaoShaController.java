package com.cobra.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.cobra.service.LockService;
import com.cobra.service.RedisService;
import com.cobra.util.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
public class MiaoShaController {
  @Autowired
  private   LockService lockService;
  @Autowired
  private RedisService redisService;



  @RequestMapping("/msTest")
  public void miaoshaTest(){

      int result = RandomUtil.randomInt(0, 2);
      if(result == 0){
          HttpUtil.get("http://localhost:8001/ms?id=miaosha");
      }
      if(result == 1){
          HttpUtil.get("http://localhost:8002/ms?id=miaosha");
      }

  }




    @RequestMapping("/ms")
    public void miaosha(String id){
        boolean isLock = lockService.lock("lock"+id,5L, TimeUnit.SECONDS);
        if(isLock){
            try {
                Long decrement = redisService.decrement(id);
                if(decrement != null){
                    IOUtil.write("C://miaosha//miaosha.txt","秒杀成功:,剩余:"+decrement+"\n",true);
                }
            } catch (Exception e) {
                System.out.println("miaosha error");
            } finally {
                lockService.unlock("lock"+id);
            }
        }
    }


}
