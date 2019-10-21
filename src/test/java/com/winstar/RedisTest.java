package com.winstar;

import com.winstar.entity.Customer;
import com.winstar.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CustomerRepository customerRepository;

    Set<Customer> customerSet;

    private static final String key = "customers";

    @PostConstruct
    public void init() {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        List<Customer> customerList = customerRepository.findAll(new PageRequest(1, 10, Sort.Direction.DESC, "createdAt")).getContent();
        for (Customer customer : customerList) {
            redisTemplate.opsForSet().add(key, customer);
        }
    }

    @Test
    public void test1() {
        SetOperations setOperations = redisTemplate.opsForSet();
        Set<Customer> members = (Set<Customer>) setOperations.members(key);
        if (CollectionUtils.isEmpty(members)) {
            Long add = setOperations.add(key, customerSet);
            log.info("写进 redis 的数量 num is {}", add);
        } else {
            Customer customer;
            if (!ObjectUtils.isEmpty(setOperations.pop(key))) {
                customer = (Customer) setOperations.pop(key);
                log.info("customer is{}", customer);
            }

        }


    }


}
