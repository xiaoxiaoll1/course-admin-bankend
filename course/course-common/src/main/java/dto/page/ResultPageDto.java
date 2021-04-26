package dto.page;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 返回给前端的页面数据
 * @author xiaozj
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultPageDto<D> implements Serializable {

    @ApiModelProperty(value = "返回数据集合")
    private List<D> data;

    @ApiModelProperty(value = "数据总数")
    private Long total;

    @ApiModelProperty(value = "总页数")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页码")
    private Integer current;

    @ApiModelProperty(value = "是否成功")
    private boolean success = true;

}
