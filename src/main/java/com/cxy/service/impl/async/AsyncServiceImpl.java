package com.cxy.service.impl.async;

import com.cxy.service.async.IAsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @program: template_project_mybatis_plus
 * @description:
 * @author: 陈翔宇
 * @create_time: 2020-11-21 10:18:45
 */
@Service
public class AsyncServiceImpl implements IAsyncService {

    @Async
    @Override
    public void generateReport(){
        System.out.println("报表线程名称:" + Thread.currentThread().getName());
    }
}
