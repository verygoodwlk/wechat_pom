package com.qf.controller;


import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    
    @RequestMapping("/register")
    public ResultData<Integer> register(User user){

        int result = userService.register(user);

        if(result > 0){
            //注册成功
            return ResultData.createSuccResultData(null);
        } else if(result == -1){
            //用户名存在
            return ResultData.createErrorResultData("1001", "用户名已经存在！");
        } else {
            return ResultData.createErrorResultData("5001", "服务器异常，请联系管理员");
        }
    }
}
