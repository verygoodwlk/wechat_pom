package com.qf.service;

import com.qf.entity.FriendRequest;

import java.util.List;

public interface IFriendRequestService {

    int insertFriendRequest(FriendRequest friendRequest);

    List<FriendRequest> queryFriendRequest(int uid);

    int friendRequestHandler(int rid, int status);
}
