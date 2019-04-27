package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends implements Serializable {

    private int uid;
    private int fid;
    private String backname;
    private Date createtime;
    private int status = 0;//0 - 正常 1 - 黑名单 2 - 删除

    @TableField(exist = false)
    private User friend;
}
