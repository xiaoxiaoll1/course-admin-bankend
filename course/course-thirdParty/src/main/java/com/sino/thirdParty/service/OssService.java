package com.sino.thirdParty.service;

import dto.result.ResultDataDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaozj
 */
public interface OssService {

    /**
     * 上传文件至阿里云 oss
     *
     * @param file
     * @param
     * @return
     * @throws Exception
     */
    ResultDataDto uploadOss(MultipartFile file) throws Exception;

    /**
     * 删除阿里云 oss的文件
     *
     * @param objectName
     * @param
     * @return
     * @throws Exception
     */
    ResultDataDto deleteOss(String objectName);
}
