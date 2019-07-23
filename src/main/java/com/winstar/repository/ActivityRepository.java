package com.winstar.repository;

import com.winstar.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;


public interface ActivityRepository extends JpaSpecificationExecutor<Activity>, JpaRepository<Activity, String> {
    List<Activity> findByTypeIn(Collection types);

    Activity findByType(Integer type);
}
