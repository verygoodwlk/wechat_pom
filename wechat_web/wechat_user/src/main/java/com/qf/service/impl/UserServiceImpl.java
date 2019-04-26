package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public int register(User user) {

        //判断用户名是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        User u = userMapper.selectOne(queryWrapper);

        if(u != null){
            //用户存在
            return -1;
        }

        //TODO 根据用户生成二维码名片

        File file = null;
        try {
            //创建一个临时文件
            file = File.createTempFile(user.getUsername() + "_qrcode", "png");

            //设置生成二维码的内容
            String qrcode = "txbb:" + user.getUsername();

            //创建二维码
            QRCodeUtils.createQRCode(file, qrcode);

            //将二维码的图片数据上传到FastDFS
            StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    new FileInputStream(file),
                    file.length(),
                    "PNG",
                    null);

            //二维码的访问路径
            String qrcodePath = result.getFullPath();
            user.setCard(qrcodePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除临时文件
            if(file != null && file.exists()){
                file.delete();
            }
        }

        //TODO 根据昵称生成拼音

        //TODO 密码MD5加密

        return userMapper.insert(user);
    }

    @Override
    public User queryByUserName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateHeaderByUid(int uid, String header, String headerCrm) {
        User user = userMapper.selectById(uid);
        if(user != null){
            user.setHeader(header);
            user.setHeaderCrm(headerCrm);
            return userMapper.updateById(user);
        }
        return 0;
    }
}
