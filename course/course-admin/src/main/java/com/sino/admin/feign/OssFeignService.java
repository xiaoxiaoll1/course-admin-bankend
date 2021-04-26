package com.sino.admin.feign;

import dto.result.ResultDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * 远程调用第三方Oss文件上传服务
 * @author xiaozj
 */
@FeignClient("course-thirdParty")
public interface OssFeignService {

    /**
     * 远程调用文件上传接口
     * @param file
     * @return
     */
    @RequestMapping("/thirdParty/oss/upload")
    ResultDataDto uploadOss(@RequestBody MultipartFile file);

    /**
     * 远程调用文件删除接口
     * @param objectName
     * @return
     */
    @RequestMapping("/thirdParty/oss/delete")
    ResultDataDto deleteOss(String objectName);


}
