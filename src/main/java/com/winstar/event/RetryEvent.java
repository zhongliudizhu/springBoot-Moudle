package com.winstar.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

public class RetryEvent extends ApplicationEvent {
    private Object data;
    private long timeStrap;

    public RetryEvent(Object source, Date time) {
        super(source);
        this.data = source;
        this.timeStrap = time.getTime();
    }
}
