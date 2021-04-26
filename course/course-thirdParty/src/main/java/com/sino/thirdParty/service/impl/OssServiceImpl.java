package com.sino.thirdParty.service.impl;

import com.sino.thirdParty.config.OssConfig;
import com.sino.thirdParty.service.OssService;
import com.sino.thirdParty.util.OssBootUtil;
import dto.result.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaozj
 */
@Service("ossService")
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private OssBootUtil ossBootUtil;


    @Override
    public ResultDataDto uploadOss(MultipartFile file) throws Exception {
        // 格式化时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentTime = now.format(format);
        // 高依赖版本 oss 上传工具
        String ossFileUrlBoot = null;
        /**
         * ossConfig 配置类
         * file 文件
         * "jpc/"+currentTime 上传文件地址 加时间戳
         */
        ossFileUrlBoot = ossBootUtil.upload(ossConfig, file, "jpc/" + currentTime);
        log.info(ossFileUrlBoot);

        ResultDataDto<String> resultDataDto = ResultDataDto.uploadSuccess();
        resultDataDto.setData(ossFileUrlBoot);
        return resultDataDto;
    }

    @Override
    public ResultDataDto deleteOss(String objectName) {
        ResultDataDto deleteResult = ossBootUtil.delete(objectName, ossConfig);
        return deleteResult;
    }
}
