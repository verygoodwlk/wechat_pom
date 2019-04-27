package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;
    private int fromid;
    private int toid;
    private Date createtime = new Date();
    private String content;
    private int status;//0 - 待处理 1 - 已通过 2 - 已忽略

    @TableField(exist = false)
    private User friend;
}
