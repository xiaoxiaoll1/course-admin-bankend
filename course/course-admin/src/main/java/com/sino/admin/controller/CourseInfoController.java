package com.sino.admin.controller;

import com.sino.admin.entity.CourseInfoEntity;
import com.sino.admin.service.CourseInfoService;
import dto.page.QueryPageDto;
import dto.page.ResultPageDto;
import dto.result.ResultDataDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author xiaozj
 */
@Api(value = "/courseInfo", tags = "CourseInfo Swagger")
@RestController
@RequestMapping("/courseInfo")
public class CourseInfoController {

    @Resource
    private CourseInfoService courseInfoService;

    @GetMapping("/findAllCoursesByTeacher")
    public ResultDataDto<List<CourseInfoEntity>> findCourseByTeacher(@RequestParam("teacherCode") String teacherCode) {
        List<CourseInfoEntity> courses = null;
        try {
            courses = courseInfoService.findCourseByTeacher(teacherCode);
        } catch (Exception e) {
            return ResultDataDto.newException(e);
        }
        return ResultDataDto.operateSuccess(courses);
    }

    /**
     * 查询课程信息，并返回分页结果
     * @param pageDto
     * @return
     */
    @ApiOperation("分页查询课程信息")
    @PostMapping(value = "/query/coursePage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDataDto<ResultPageDto<CourseInfoEntity>> queryCoursePages
            (@RequestBody @Valid QueryPageDto pageDto) {
        try {
            ResultPageDto<CourseInfoEntity> resultPage = courseInfoService.queryPages(pageDto);
            return ResultDataDto.operateSuccess(resultPage);
        } catch (Exception e) {
            return ResultDataDto.newException(e);
        }
    }

    /**
     * TODO 等待前端实现
     * @param entity
     * @return
     */
    @ApiOperation("新增课程")
    @PostMapping(value = "/createCourse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDataDto createCourse(@RequestBody @Valid CourseInfoEntity entity) {
        try {
            courseInfoService.addCourse(entity);
            return ResultDataDto.addSuccess();
        } catch (Exception e) {
            return ResultDataDto.newException(e);
        }
    }

    /**
     * TODO 等待前端实现
     * @param entity
     * @return
     */
    @ApiOperation("更新课程")
    @PostMapping(value = "/updateCourse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDataDto updateCourse(@RequestBody @Valid CourseInfoEntity entity) {
        try {
            courseInfoService.updateCourse(entity);
            return ResultDataDto.updateSuccess();
        } catch (Exception e) {
            return ResultDataDto.newException(e);
        }
    }

    @ApiOperation("分页查询测试")
    @PostMapping(value = "/queryTest", produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryTest
            (@RequestBody QueryPageDto pageDto) {
        return "测试成功";

    }

    @GetMapping("/test")
    public String test() {
        return "sdosdi";
    }

}
