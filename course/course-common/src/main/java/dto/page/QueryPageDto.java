package dto.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import constant.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.io.Serializable;


/**
 * 前端查询页面数据dto
 * @author xiaozj
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageDto implements Serializable {

    /**
     * 当前页码数，默认为1
     */
    @ApiModelProperty(name = "当前页码")
    private Integer current = 1;

    /**
     * 一页显示的数量，默认为10
     */
    @ApiModelProperty(name = "显示数量")
    private Integer pageSize = 10;

    /**
     * 排序字段，约定为：排序属性_ascend 或 排序属性_descend
     */
    @ApiModelProperty(name = "排序字段", notes = "排序字段，约定为：排序属性_ascend 或 排序属性_descend")
    private String orderByStr;

    private Sort orderBy;

    @ApiModelProperty(name = "查询参数")
    private JSONObject search;

    public void parseOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (StringUtils.isNotBlank(orderByStr)) {
            if (orderByStr.split(CommonConstants.UNDERLINE)[1].equalsIgnoreCase(CommonConstants.DESCEND)) {
                orderByStr = orderByStr.split(CommonConstants.UNDERLINE)[0].trim();
                sort = Sort.by(Sort.Direction.DESC, orderByStr);
            } else {
                orderByStr = orderByStr.split(CommonConstants.UNDERLINE)[0].trim();
                sort = Sort.by(Sort.Direction.ASC, orderByStr);
            }
        }
        this.orderBy = sort;
    }



}
