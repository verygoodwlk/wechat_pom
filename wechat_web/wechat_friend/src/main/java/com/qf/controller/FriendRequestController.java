package com.qf.controller;

import com.qf.entity.FriendRequest;
import com.qf.entity.ResultData;
import com.qf.service.IFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
public class FriendRequestController {

    @Autowired
    private IFriendRequestService friendRequestService;

    /**
     * 添加一个好友申请
     * @return
     */
    @RequestMapping("/insert")
    public ResultData<Boolean> insert(FriendRequest friendRequest){

        int result = friendRequestService.insertFriendRequest(friendRequest);
        switch (result){
            case -1:
                //已经发送过
                return ResultData.createErrorResultData("1008", "已经发送过添加申请！");
            case -2:
                //已经是好友
                return ResultData.createErrorResultData("1009", "已经是好友关系！");
            case 1:
                //成功
                return ResultData.createSuccResultData(true);
            default:
                //失败
                return ResultData.createErrorResultData("5001", "发送申请失败，请稍后再试！");
        }
    }


    /**
     * 根据用户id查询添加好友申请
     * @param uid
     * @return
     */
    @RequestMapping("/list")
    public ResultData queryList(int uid){
        List<FriendRequest> friendRequests = friendRequestService.queryFriendRequest(uid);
        return ResultData.createSuccResultData(friendRequests);
    }

    /**
     * 处理好友添加的申请
     * @return
     */
    @RequestMapping("/requestHandler")
    public ResultData<Boolean> friendRequestHandler(int rid, int status){
        int result = friendRequestService.friendRequestHandler(rid, status);
        return ResultData.createSuccResultData(true);
    }
}
