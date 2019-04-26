package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.ResultData;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/res")
public class ResController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private IUserService userService;

    /**
     * 上传图片
     * @return
     */
    @RequestMapping("/uploadImg")
    public ResultData<Map> uploadImg(MultipartFile file, int uid){

        //上传图片到fastdfs
        try {
            StorePath path = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "PNG",
                    null);

            //上传成功
            //同步更新数据

            //xxxxxxxx.png
            //xxxxxxxx_80x80.png


            //获得上传后的大图路径
            String header = path.getFullPath();
            //获得缩略图的路径
            String headerCrm = header.replace(".", "_80x80.");

            //调用服务更新头像
            userService.updateHeaderByUid(uid, header, headerCrm);

            Map<String, String> map = new HashMap<>();
            map.put("header", header);
            map.put("headerCrm", headerCrm);
            return ResultData.createSuccResultData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultData.createErrorResultData("1006", "图片上传失败！");
    }
}
