package com.qf.service;

import com.qf.entity.User;

public interface IUserService {

    int register(User user);

    User queryByUserName(String username);

    User queryById(int id);

    int updateHeaderByUid(int uid, String header, String headerCrm);
}
