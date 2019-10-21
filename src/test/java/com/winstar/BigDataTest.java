package com.winstar;

import com.winstar.entity.Customer;
import com.winstar.entity.NewCustomer;
import com.winstar.repository.CustomerRepository;
import com.winstar.repository.NewCustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BigDataTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    NewCustomerRepository newCustomerRepository;

    private ExecutorService pool = Executors.newCachedThreadPool();
    private CountDownLatch cd = new CountDownLatch(10);


    @Test
    public void test1() throws InterruptedException {
        long start = System.currentTimeMillis();
        int perThreadNum = 1000;
        int count = (int) customerRepository.count();
        int thread = count % perThreadNum == 0 ? count / perThreadNum : (count / perThreadNum + 1);
        //List<Customer> subList = all.subList(i*perThreadNum, (perThreadNum * (i+1))>all.size()?all.size():(perThreadNum*(i+1)));
        IntStream.range(0, thread).mapToObj(i -> new PageRequest(i, perThreadNum)).map(pageable -> customerRepository.findAll(pageable).getContent()).forEach(content -> {
            List<NewCustomer> newList = getNewCustomerList(content);
            log.info("sublist size is" + content.size());
            pool.submit(() -> {
                log.info(Thread.currentThread().getName() + "====来进行操作=====");
                newCustomerRepository.save(newList);
                cd.countDown();
            });
        });
        cd.await();
        log.info("总消耗的时间为" + (System.currentTimeMillis() - start));
        log.info("=======执行完成=========");
    }

    private List<NewCustomer> getNewCustomerList(List<Customer> subList) {
        List<NewCustomer> newCustomers = new ArrayList<>();
        for (Customer c : subList) {
            NewCustomer newCustomer = new NewCustomer();
            BeanUtils.copyProperties(c, newCustomer);
            newCustomers.add(newCustomer);
        }
        return newCustomers;

    }


}
