package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public int register(User user) {

        //判断用户名是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        User u = userMapper.selectOne(queryWrapper);

        if(u != null){
            //用户存在
            return -1;
        }

        //TODO 根据用户生成二维码名片

        //TODO 根据昵称生成拼音

        //TODO 密码MD5加密

        return userMapper.insert(user);
    }
}
