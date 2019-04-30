package com.qf.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一的消息父类
 */
@Data
public class WsMsg implements Serializable {

    /**
     * 消息的类型
     * 1 - 初始化连接
     * 2 - 心跳
     * 3 - 下线
     * 4 - 对方正在输入...
     * 5 - 对方停止输入...
     * ...
     */
    private int type;

}
