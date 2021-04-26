package com.sino.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaozj
 */
@ApiModel
@Entity
@Table(name = "course_time")
@Data
public class CourseTimeEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(name = "课程信息id")
    @Column(name = "course_info_id")
    private Long courseInfoId;

    /**
     * 星期
     */
    @ApiModelProperty(name = "星期几")
    @Column(name = "week_day")
    private Integer weekDay;

    @ApiModelProperty(name = "开始时间")
    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @ApiModelProperty(name = "结束时间")
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;


}
