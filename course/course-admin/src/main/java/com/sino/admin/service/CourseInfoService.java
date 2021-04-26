package com.sino.admin.service;

import com.sino.admin.entity.CourseInfoEntity;
import dto.page.QueryPageDto;
import dto.page.ResultPageDto;
import dto.result.ResultDataDto;

import java.util.List;

/**
 * @author xiaozj
 */
public interface CourseInfoService {

    /**
     * 根据教师编码查询课程列表
     * @param teacherCode
     * @return
     */
    List<CourseInfoEntity> findCourseByTeacher(String teacherCode);

    /**
     * 新增课程信息
     * @param course
     */
    void addCourse(CourseInfoEntity course);

    /**
     * 更新课程
     * @param courseInfoEntity
     * @return
     */
    void updateCourse(CourseInfoEntity courseInfoEntity);

    /**
     * 删除课程信息
     * @param id
     */
    void deleteCourse(Long id);

    /**
     * 批量删除课程信息
     * @param ids
     */
    void deleteCourseBatch(List<Long> ids);


    /**
     * 分页搜索
     * @param pageDto
     * @return
     */
    ResultPageDto<CourseInfoEntity> queryPages(QueryPageDto pageDto);
}
