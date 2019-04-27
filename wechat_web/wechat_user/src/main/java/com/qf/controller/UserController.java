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

        System.out.println("-->" + user);

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

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public ResultData<User> login(User user){
        User u = userService.queryByUserName(user.getUsername());
        if(u != null && u.getPassword().equals(user.getPassword())){

            //登录信息放入redis

            //登录成功
            return ResultData.createSuccResultData(u);

        } else if(u == null){
            //账号不存在
            return ResultData.createErrorResultData("1002", "账号不存在！");

        } else {
            //密码错误
            return ResultData.createErrorResultData("1003", "密码错误！");

        }
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @RequestMapping("/queryByUserName")
    public ResultData<User> query(String username){
        User user = userService.queryByUserName(username);
        if(user != null){
            //查询成功
            return ResultData.createSuccResultData(user);
        }

        return ResultData.createErrorResultData("1007", "该账号信息不存在！");
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @RequestMapping("/queryById")
    public User queryById(int id){
        return userService.queryById(id);
    }
}
