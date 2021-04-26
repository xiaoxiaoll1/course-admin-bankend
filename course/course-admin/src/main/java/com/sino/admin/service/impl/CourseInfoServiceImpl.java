package com.sino.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sino.admin.dao.CourseInfoDao;
import com.sino.admin.entity.CourseInfoEntity;
import com.sino.admin.feign.OssFeignService;
import com.sino.admin.service.CourseInfoService;
import dao.BaseJpaRepository;
import dto.page.QueryPageDto;
import dto.page.ResultPageDto;
import dto.result.ResultDataDto;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.impl.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaozj
 */
@Service("courseInfo")
public class CourseInfoServiceImpl extends BaseServiceImpl<CourseInfoEntity, Long> implements CourseInfoService {

    @Resource
    private CourseInfoDao courseInfoDao;

    public CourseInfoServiceImpl(CourseInfoDao courseInfoDao) {
        super(courseInfoDao);
    }

    @Override
    public List<CourseInfoEntity> findCourseByTeacher(String teacherCode) {
//        List<CourseInfoEntity> courseInfos = courseInfoDao.findByTeacherCode(teacherCode);
//        List<CourseInfoEntity> all = findAll();
//        System.out.println(all);
//        return courseInfos;
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCourse(CourseInfoEntity course) {
        courseInfoDao.save(course);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourse(CourseInfoEntity courseInfoEntity) {
        courseInfoDao.save(courseInfoEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCourse(Long id) {
        courseInfoDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCourseBatch(List<Long> ids) {
        ids.stream().forEach(id -> {
            courseInfoDao.deleteById(id);
        });
    }

    @Override
    public ResultPageDto<CourseInfoEntity> queryPages(QueryPageDto pageDto) {
        Page<CourseInfoEntity> coursePage = null;
        if (pageDto.getSearch() != null) {
            CourseInfoEntity queryEntity = pageDto.getSearch().toJavaObject(CourseInfoEntity.class);
            coursePage = queryPageWithCondition(pageDto, queryEntity);
        } else {
            coursePage = queryPage(pageDto);
        }
        ResultPageDto<CourseInfoEntity> resultPage = new ResultPageDto<>();
        resultPage.setCurrent(pageDto.getCurrent());
        resultPage.setTotal(coursePage.getTotalElements());
        resultPage.setPageSize(pageDto.getPageSize());
        resultPage.setData(coursePage.getContent());
        return resultPage;
    }


}
