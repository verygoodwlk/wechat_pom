package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.FriendMapper;
import com.qf.entity.Friends;
import com.qf.entity.User;
import com.qf.service.IFriendService;
import com.qf.service.UserFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FriendServiceImpl implements IFriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserFegin userFegin;

    @Override
    public boolean isFriends(int uid, int fid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("fid", fid);
//        queryWrapper.eq("status", 0);

        Friends friends = friendMapper.selectOne(queryWrapper);

        return friends != null;
    }

    /**
     * 将uid和fid互相添加好友
     * @param uid
     * @param fid
     * @return
     */
    @Override
    @Transactional
    public int addFriend(int uid, int fid) {

        Friends friends1 = new Friends(uid, fid, null, new Date(), 0, null);
        Friends friends2 = new Friends(fid, uid, null, new Date(), 0, null);

        if(!isFriends(uid, fid)){
            friendMapper.insert(friends1);
        }

        if (!isFriends(fid, uid)){
            friendMapper.insert(friends2);
        }

        return 1;
    }

    /**
     * 根据用户id查询好友列表
     * @param uid
     * @return
     */
    @Override
    public List<Friends> queryFriendList(int uid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<Friends> list = friendMapper.selectList(queryWrapper);

        for (Friends friend : list) {
            User user = userFegin.queryById(friend.getFid());
            friend.setFriend(user);
        }

        return list;
    }
}
