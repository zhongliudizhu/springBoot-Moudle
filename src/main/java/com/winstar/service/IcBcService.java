package com.winstar.service;

import com.icbc.api.IcbcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IcBcService {

    @Autowired
    IcbcClient icbcClient;


}
