package com.qf.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 初始化连接的消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsConnMsg extends WsMsg {

    private String username;
    private String deviceid;
}
