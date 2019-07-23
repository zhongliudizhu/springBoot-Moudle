package com.winstar;

import com.winstar.entity.Goods;
import com.winstar.repository.GoodsRepository;
import com.winstar.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SpringbootModleApplicationTests {
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    public void test1() {
        String date = "2018-10-25 23:59:59";
        List<Goods> goodsList = goodsRepository.findByStatusInAndAndCreateTimeBeforeOrderByCreateTimeDesc(Arrays.asList(1, 2), DateUtil.strToDate(date));
        log.info("====" + goodsList);
    }

}
