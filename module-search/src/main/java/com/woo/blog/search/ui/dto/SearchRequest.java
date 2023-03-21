package com.woo.blog.search.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Schema(title = "블로그 조회")
@Data
public class SearchRequest {
    @Schema(name = "검색어", type = "String", required = true)
    @NotBlank(message = "검색어는 필수입니다.")
    private String query;
    @Schema(name = "정렬", allowableValues = "accuracy, recency", type = "String")
    private String sort = "";
    @Schema(name = "페이지 번호", type = "int")
    @Max(value = 50, message = "최대 50 페이지 까지 조회할 수 있습니다.")
    @Min(value = 1, message = "1 페이지 미만으로 설정할 수 없습니다.")
    private Integer page = 1;
    @Schema(name = "한번에 조회할 건수", type = "int")
    @Max(value = 50, message = "최대 50 건 까지 조회할 수 있습니다.")
    @Min(value = 1, message = "1 건 미만으로 설정할 수 없습니다.")
    private Integer size = 10;


    @Schema(hidden = true)
    public String parseKakaoSort() {
        // Default accuracy
        return StringUtils.equals(this.sort, "recency") ? "recency" : "accuracy";
    }
    @Schema(hidden = true)
    public String parseNaverSort() {
        // Default sim
        return StringUtils.equals(this.sort, "recency") ? "date" : "sim";
    }

}
