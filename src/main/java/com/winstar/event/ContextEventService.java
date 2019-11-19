package com.winstar.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContextEventService {


    public void showEvent(Object source) {
        log.info("{Thread} thread==" + Thread.currentThread().getName());
        log.info("{source}事件源" + source);
        if (source instanceof ContextRefreshedEvent) {
            ContextRefreshedEvent startedEvent = (ContextRefreshedEvent) source;
            ApplicationContext applicationContext = startedEvent.getApplicationContext();
            log.info("{source} 容器启动事件 applicationContext" + applicationContext.getBeanDefinitionCount());
        } else {
            log.info("{source} applicationContext==" + source);

        }

    }


}
