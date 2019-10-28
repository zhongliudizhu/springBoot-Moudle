package com.winstar.schedule;

import com.alibaba.fastjson.JSON;
import com.winstar.entity.Goods;
import com.winstar.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RedisScheduleTask {

    private RedisTemplate<String, Object> redisTemplate;
    private GoodsRepository goodsRepository;
    private static final String goodsKeyPrefix = "goods_";

    @Autowired
    public RedisScheduleTask(RedisTemplate redisTemplate, GoodsRepository goodsRepository) {
        this.redisTemplate = redisTemplate;
        this.goodsRepository = goodsRepository;
    }

    public void writeToRedis() {
        List<Goods> goodsList = goodsRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
        if (CollectionUtils.isEmpty(goodsList)) {
            log.info("{goods} 无数据需要备份");
        }
        log.info("{Thread} " + Thread.currentThread().getName() + "==执行任务==");
        for (Goods g : goodsList) {
            String id = g.getId();
            redisTemplate.opsForHash().put(goodsKeyPrefix + id, id, JSON.toJSONString(g));
        }

    }


}
