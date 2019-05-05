package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qf.msg.WsMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_msg")
public class Msg extends WsMsg implements Serializable {

    private int fromid;
    private int toid;
    private String content;
    private int msgType;//0 - 文本 1 - 图片 2 - 音频
    private Date createtime;
    private int status;//0 - 未读 - 已读

    @TableField(exist = false)
    private int type = 4;//单聊消息
}
