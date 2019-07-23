package com.winstar.repository;

import com.alibaba.fastjson.JSONArray;
import com.winstar.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * 名称： GoodsRepository
 * 作者： sky
 * 日期： 2017-12-12 9:41
 * 描述：
 **/
public interface GoodsRepository extends JpaRepository<Goods, String>, JpaSpecificationExecutor<Goods> {

    List<Goods> findByStatusAndIdInOrderByPriceAsc(Integer status, JSONArray array);

    List<Goods> findByStatusInAndAndCreateTimeBeforeOrderByCreateTimeDesc(List<Integer> status, Date createTime);

}
