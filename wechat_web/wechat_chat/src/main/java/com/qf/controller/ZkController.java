package com.qf.controller;

import com.qf.entity.ResultData;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/zk")
public class ZkController {

    @Autowired
    private ZkClient zkClient;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 获得netty的地址
     * @return
     */
    @RequestMapping("/gethost")
    public ResultData<String> getNettyServer(){

        //[127.0.0.1:10086, 127.0.0.2:10086]
        List<String> children = zkClient.getChildren("/netty");
        System.out.println("获得服务器的地址：" + children);

        if(children == null || children.size() == 0){
            return ResultData.createErrorResultData("1008", "未找到服务器！");
        }

        //获得地址列表
        String server = children.get(atomicInteger.getAndIncrement() % children.size());
        return ResultData.createSuccResultData(server);
    }
}
