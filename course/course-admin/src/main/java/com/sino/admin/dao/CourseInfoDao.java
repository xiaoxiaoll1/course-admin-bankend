package com.sino.admin.dao;


import com.sino.admin.entity.CourseInfoEntity;
import dao.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaozj
 */
@Repository
public interface CourseInfoDao extends BaseJpaRepository<CourseInfoEntity, Long> {

    /**
     * 通过老师代码寻找对应的课程
     * @param code
     * @return
     */
    List<CourseInfoEntity> findByTeacherCode(String code);




}
