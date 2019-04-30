package com.qf.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下线消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsShutDownMsg extends WsMsg {

    private String username;
}
