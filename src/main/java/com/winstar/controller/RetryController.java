package com.winstar.controller;

import com.winstar.common.Result;
import com.winstar.service.RetryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/retry")
@AllArgsConstructor
public class RetryController {

    RetryService retryService;

    @GetMapping("/doRetry")
    public Result doRetry() {
        Map map = retryService.doRetry();
        return Result.success(map);
    }


}
