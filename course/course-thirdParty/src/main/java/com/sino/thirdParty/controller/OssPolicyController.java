package com.sino.thirdParty.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.sino.thirdParty.config.OssConfig;
import dto.result.ResultDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 获取服务器获取policy
 * @author xiaozj
 */
@RestController
@RequestMapping("/thirdParty/oss")
public class OssPolicyController {

    @Resource
    private OSS ossClient;

    @Resource
    private OssConfig ossConfig;

    @RequestMapping("/policy")
    public ResultDataDto policy(){

        // host的格式为 bucketname.endpoint
        String host = "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentTime = now.format(format);
        // 用户上传文件时指定的前缀。
        String dir = currentTime;

        Map<String, String> respMap=null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap= new LinkedHashMap<String, String>();
            respMap.put("accessid", ossConfig.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {

            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        ResultDataDto resultDataDto = ResultDataDto.uploadSuccess();
        resultDataDto.setData(respMap);
        return resultDataDto;
    }
}
