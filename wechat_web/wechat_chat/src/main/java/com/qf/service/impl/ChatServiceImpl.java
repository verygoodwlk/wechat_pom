package com.qf.service.impl;

import com.qf.dao.ChatMapper;
import com.qf.entity.Msg;
import com.qf.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public int insertMsg(Msg msg) {
        msg.setCreatetime(new Date());
        msg.setStatus(0);
        return chatMapper.insert(msg);
    }
}
