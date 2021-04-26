package com.sino.thirdParty.controller;

import com.sino.thirdParty.service.OssService;
import dto.result.ResultDataDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author xiaozj
 */
@RestController
@RequestMapping("/thirdParty/oss")
public class OssController {

    @Resource
    private OssService ossService;

    @RequestMapping(value = "/upload", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResultDataDto uploadOSS(@RequestBody MultipartFile file) throws Exception {
        return ossService.uploadOss(file);

    }

    @RequestMapping("/delete")
    public ResultDataDto deleteOSS(String objectName){
        return ossService.deleteOss(objectName);

    }
}
