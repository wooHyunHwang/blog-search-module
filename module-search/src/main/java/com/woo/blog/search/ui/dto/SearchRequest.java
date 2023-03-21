package com.woo.blog.search.ui.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "블로그 조회")
@Data
public class SearchRequest {
    @ApiModelProperty(value = "검색어", position = 1, dataType = "String")
    @NotBlank(message = "검색어는 필수입니다.")
    private String query;
    @ApiModelProperty(value = "정렬", allowableValues = "accuracy, recency", position = 2, dataType = "String")
    private String sort = "";
    @ApiModelProperty(value = "페이지 번호", position = 3, dataType = "int")
    @Max(value = 50, message = "최대 50 페이지 까지 조회할 수 있습니다.")
    @Min(value = 1, message = "1 페이지 미만으로 설정할 수 없습니다.")
    private Integer page = 1;
    @ApiModelProperty(value = "한번에 조회할 건수", position = 4, dataType = "int")
    @Max(value = 50, message = "최대 50 건 까지 조회할 수 있습니다.")
    @Min(value = 1, message = "1 건 미만으로 설정할 수 없습니다.")
    private Integer size = 10;


    @ApiModelProperty(hidden = true)
    public String parseKakaoSort() {
        // Default accuracy
        return StringUtils.equals(this.sort, "recency") ? "recency" : "accuracy";
    }
    @ApiModelProperty(hidden = true)
    public String parseNaverSort() {
        // Default sim
        return StringUtils.equals(this.sort, "recency") ? "date" : "sim";
    }

}
