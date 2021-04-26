package dto.result;

import constant.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 实体类返回
 *
 * @author xiaozj
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class ResultDataDto<D> implements Serializable {

    @ApiModelProperty(value = "返回状态码")
    private int code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回状态")
    private String status;
    @ApiModelProperty(value = "返回数据")
    private D data;

    public ResultDataDto(int code, String message, String status) {
        super();
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public static ResultDataDto addSuccess() {
        return new ResultDataDto(CommonConstants.SUCCESS_CODE, "新增成功", CommonConstants.SUCCESS_STATUS);
    }

    public static ResultDataDto uploadSuccess() {
        return new ResultDataDto(CommonConstants.SUCCESS_CODE, "获取阿里云Oss上传policy成功", CommonConstants.SUCCESS_STATUS);
    }

    public static ResultDataDto operateSuccess(Object data) {
        return new ResultDataDto(CommonConstants.SUCCESS_CODE, "操作成功", CommonConstants.SUCCESS_STATUS, data);
    }

    public static ResultDataDto updateSuccess() {
        return new ResultDataDto(CommonConstants.SUCCESS_CODE, "更新成功", CommonConstants.SUCCESS_STATUS);
    }


    public static ResultDataDto deleteSuccess() {
        return new ResultDataDto(CommonConstants.SUCCESS_CODE, "删除成功", CommonConstants.SUCCESS_STATUS);
    }

    public static ResultDataDto newException(Object data) {
        return new ResultDataDto(CommonConstants.EXCEPTION_CODE, "出现异常", CommonConstants.EXCEPTION_STATUS, data);
    }
}
