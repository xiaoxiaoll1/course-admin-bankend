package com.sino.thirdParty.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.ObjectMetadata;
import com.sino.thirdParty.config.OssConfig;
import dto.result.ResultDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author xiaozj
 */
@Component
@Slf4j
public class OssBootUtil {

    private OssBootUtil() {
    }

    /**
     * oss 工具客户端
     */
    private volatile static OSSClient ossClient = null;

    @Resource
    private SnowFlakeIdWorker idWorker;

    /**
     * 上传文件至阿里云 OSS
     * 文件上传成功,返回文件完整访问路径
     * 文件上传失败,返回 null
     *
     * @param ossConfig oss 配置信息
     * @param file      待上传文件
     * @param fileDir   文件保存目录
     * @return oss 中的相对文件路径
     * @author xiaozj
     */

    public String upload(OssConfig ossConfig, MultipartFile file, String fileDir) {
        initOSS(ossConfig);
        StringBuilder fileUrl = new StringBuilder();
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            String fileName = System.currentTimeMillis() + "-" + idWorker.nextId() + suffix;
            if (!fileDir.endsWith("/")) {
                fileDir = fileDir.concat("/");
            }
            fileUrl = fileUrl.append(fileDir + fileName);
            log.info(fileUrl + "-----------------");
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");

            ossClient.putObject(ossConfig.getBucketName(), fileUrl.toString(), file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
        fileUrl = fileUrl.insert(0, ossConfig.getUrl());
        return fileUrl.toString();
    }

    /**
     * 初始化 oss 客户端
     *
     * @param ossConfig
     * @return
     */
    private static OSSClient initOSS(OssConfig ossConfig) {
        if (ossClient == null) {
            synchronized (OssBootUtil.class) {
                if (ossClient == null) {
                    ossClient = new OSSClient(ossConfig.getEndpoint(),
                            new DefaultCredentialProvider(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()),
                            new ClientConfiguration());
                }
            }
        }
        return ossClient;
    }

    /**
     * 根据前台传过来的文件地址 删除文件
     *
     * @param objectName
     * @param ossConfig
     * @return
     * @author jiangpengcheng
     */
    public ResultDataDto delete(String objectName, OssConfig ossConfig) {
        initOSS(ossConfig);
        //将完整路径替换成 文件地址 因为yml里的url有了地址链接https: //oos-all.oss-cn-shenzhen.aliyuncs.com/
        // 如果再加上地址 那么又拼接了 所以删除不了 要先把地址替换
        String fileName = objectName.replace("https://oos-all.oss-cn-shenzhen.aliyuncs.com/", "");
        log.info(fileName + "******************************");
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(ossConfig.getBucketName(), fileName);

        return ResultDataDto.deleteSuccess();
    }
}
