package com.xhc.sbm.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestAsyncServiceImpl implements TestAsyncService {

    @Override
    @Async
    public void doWork() {
        try {
            System.out.println("===>" + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
