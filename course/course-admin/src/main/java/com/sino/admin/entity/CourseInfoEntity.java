package com.sino.admin.entity;

import config.JpaConverterListJson;
import entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 课程信息的实体类
 * @author xiaozj
 */
@ApiModel
@Entity
@Table(name = "course_info")
@Data
public class CourseInfoEntity extends BaseEntity {

    @ApiModelProperty(name = "id")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(name = "课程名")
    @Column(name = "course_name")
    private String courseName;

    @ApiModelProperty(name = "课程描述")
    @Column(name = "course_desc")
    private String courseDesc;

    @ApiModelProperty(name = "第几学期")
    @Column(name = "semester_no")
    private Integer semesterNo;

    @ApiModelProperty(name = "状态:取值为{0:停用, 1:正在编辑, 2:启用}")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(name = "学分")
    @Column(name = "credit")
    private Integer credit;

    @ApiModelProperty(name = "教师编号")
    @Column(name = "teacher_code")
    private String teacherCode;

    @ApiModelProperty(name = "时间安排")
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}
            , fetch = FetchType.EAGER
            , targetEntity = CourseTimeEntity.class)
    @JoinColumn(name = "course_info_id")
    private List<CourseTimeEntity> courseTimeList;

    @ApiModelProperty(name = "文件url")
    @Convert(converter = JpaConverterListJson.class)
    @Column(name = "file_urls", columnDefinition = "TEXT")
    private List<String> fileUrls;


}
