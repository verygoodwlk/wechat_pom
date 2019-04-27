package com.qf.service;

import com.qf.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER")
public interface UserFegin {

    @RequestMapping("/user/queryById")
    User queryById(@RequestParam("id") int id);

}
