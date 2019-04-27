package com.qf.controller;

import com.qf.entity.Friends;
import com.qf.entity.ResultData;
import com.qf.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private IFriendService friendService;

    @RequestMapping("/list")
    public ResultData<List<Friends>> queryFriendList(int uid){
        List<Friends> friends = friendService.queryFriendList(uid);
        return ResultData.createSuccResultData(friends);
    }
}
