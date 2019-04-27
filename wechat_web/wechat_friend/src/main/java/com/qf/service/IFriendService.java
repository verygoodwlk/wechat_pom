package com.qf.service;

import com.qf.entity.Friends;

import java.util.List;

public interface IFriendService {

    boolean isFriends(int fromid, int toid);

    int addFriend(int uid, int fid);

    List<Friends> queryFriendList(int uid);
}
