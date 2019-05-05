package com.qf.controller;

import com.qf.entity.Msg;
import com.qf.entity.ResultData;
import com.qf.service.IChatService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息的请求
     * @param msg
     * @return
     */
    @RequestMapping("/send")
    public ResultData sendMsg(Msg msg){

        //保存到数据库
        int result = chatService.insertMsg(msg);
        if(result > 0){
            //发送出去
            rabbitTemplate.convertAndSend("netty_exchange", "", msg);

            return ResultData.createSuccResultData(null);
        }

        return ResultData.createErrorResultData("1009", "消息发送失败！");
    }
}
