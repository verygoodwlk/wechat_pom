package com.qf.wechat_netty.util;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理当前的Channel连接
 */
public class ChannelGroupUtil {

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    //添加channel
    public static void add(String deviceid, Channel channel){
        channelMap.put(deviceid, channel);
    }

    //查询
    public static Channel getChannel(String deviceid){
        return channelMap.get(deviceid);
    }

    //删除
    public static void removeChannel(String deviceid){
        channelMap.remove(deviceid);
    }

    //删除根据Channel删除
    public static void removeChannel(Channel channel){
        Set<Map.Entry<String, Channel>> entries = channelMap.entrySet();
        for (Map.Entry<String, Channel> entry : entries) {
            if(entry.getValue() == channel){
                channelMap.remove(entry.getKey());
                break;
            }
        }
    }
}
