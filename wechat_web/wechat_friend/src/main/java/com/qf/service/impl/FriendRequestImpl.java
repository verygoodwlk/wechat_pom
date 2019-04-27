package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.FriendRequestMapper;
import com.qf.entity.FriendRequest;
import com.qf.entity.User;
import com.qf.service.IFriendRequestService;
import com.qf.service.IFriendService;
import com.qf.service.UserFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendRequestImpl implements IFriendRequestService {

    @Autowired
    private FriendRequestMapper friendRequestMapper;

    @Autowired
    private IFriendService friendService;

    @Autowired
    private UserFegin userFegin;

    //添加申请
    //-1 已经发生过好友申请 -2 已经是好友
    @Override
    public int insertFriendRequest(FriendRequest friendRequest) {

        //原来是否已经发送过申请
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("fromid", friendRequest.getFromid());
        queryWrapper.eq("toid", friendRequest.getToid());

        FriendRequest fr = friendRequestMapper.selectOne(queryWrapper);
        if(fr != null && fr.getStatus() == 0){
            //已经发送过好友申请
            return -1;
        }

        //是否已经是好友
        if(friendService.isFriends(friendRequest.getFromid(), friendRequest.getToid())){
            //已经是好友
            return -2;
        }

        return friendRequestMapper.insert(friendRequest);
    }

    @Override
    public List<FriendRequest> queryFriendRequest(int uid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("toid", uid);
        queryWrapper.orderByDesc("createtime");

        List<FriendRequest> requestList = friendRequestMapper.selectList(queryWrapper);

        for (FriendRequest friendRequest : requestList) {
            User user = userFegin.queryById(friendRequest.getFromid());
            friendRequest.setFriend(user);
        }

        return requestList;
    }

    @Override
    @Transactional
    public int friendRequestHandler(int rid, int status) {

        //修改申请记录的状态
        FriendRequest friendRequest = friendRequestMapper.selectById(rid);
        friendRequest.setStatus(status);
        friendRequestMapper.updateById(friendRequest);

        if(status == 1){
            //互相添加好友
            friendService.addFriend(friendRequest.getToid(), friendRequest.getFromid());
        }

        return 1;
    }
}
