package com.woo.blog.search.ui.dto;

import com.woo.blog.search.infra.feign.dto.KakaoBlogResponse;
import com.woo.blog.search.infra.feign.dto.NaverBlogResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "블로그 조회 응답")
@Data
public class SearchResponse {

    @ApiModelProperty(value = "조회 결과 코드", allowableValues = "200, 500", position = 1, dataType = "int", required = true)
    private int status = HttpStatus.OK.value();
    @ApiModelProperty(value = "메시지", position = 2, dataType = "String", required = true)
    private String message = "정상 처리됐습니다.";

    @ApiModelProperty(value = "조회 결과", position = 3, dataType = "Object", required = true)
    private SearchResult result;

    public SearchResponse() {}
    public SearchResponse(SearchRequest request, KakaoBlogResponse kakao) {
        this.result = new SearchResult(request, kakao);
    }
    public SearchResponse(SearchRequest request, NaverBlogResponse naver) {
        this.result = new SearchResult(request, naver);
    }

    @ApiModel(value = "조회 결과")
    @Getter
    public static class SearchResult {
        @ApiModelProperty(value = "페이지 번호", position = 1, dataType = "int", required = true)
        private int page;
        @ApiModelProperty(value = "한번에 조회한 건수", position = 2, dataType = "int", required = true)
        private int size;
        @ApiModelProperty(value = "전체 건수", position = 3, dataType = "long", required = true)
        private long totalCount;
        @ApiModelProperty(value = "조회된 건수", position = 4, dataType = "int", required = true)
        private int selectCount;
        @ApiModelProperty(value = "출처", allowableValues = "KAKAO, NAVER", position = 5, dataType = "String", required = true)
        private String reference = "";

        @ApiModelProperty(value = "블로그 목록", position = 6, dataType = "List", required = true)
        private List<BlogInfo> blogList;


        protected SearchResult() {}
        protected SearchResult(int page, int size) {
            this.page = page;
            this.size = size;
        }
        public SearchResult(SearchRequest request) {
            this(request.getPage(), request.getSize());
            this.blogList = Collections.emptyList();
        }
        public SearchResult(SearchRequest request, KakaoBlogResponse kakao) {
            this(request.getPage(), request.getSize());
            this.reference = "KAKAO";
            if(kakao.getMeta() != null) this.totalCount = kakao.getMeta().getTotal_count();
            if(kakao.getDocuments() != null) {
                this.selectCount = kakao.getDocuments().size();
                this.blogList = kakao.getDocuments().stream()
                        .map(BlogInfo::new)
                        .collect(Collectors.toList());
            } else {
                this.blogList = Collections.emptyList();
            }
        }
        public SearchResult(SearchRequest request, NaverBlogResponse naver) {
            this(request.getPage(), request.getSize());
            this.reference = "NAVER";
            this.totalCount = naver.getTotal();
            if(naver.getItems() != null) {
                this.selectCount = naver.getItems().size();
                this.blogList = naver.getItems().stream()
                        .map(BlogInfo::new)
                        .collect(Collectors.toList());
            } else {
                this.blogList = Collections.emptyList();
            }
        }

    }

    @ApiModel(value = "블로그 정보")
    @Getter
    public static class BlogInfo {

        @ApiModelProperty(value = "블로그 글 제목", position = 1, dataType = "String", required = true)
        private String title;
        @ApiModelProperty(value = "블로그 글 요약", position = 2, dataType = "String", required = true)
        private String contents;
        @ApiModelProperty(value = "블로그 글 URL", position = 3, dataType = "String", required = true)
        private String url;
        @ApiModelProperty(value = "블로그 이름", position = 4, dataType = "String", required = true)
        private String blogname;
        @ApiModelProperty(value = "블로그 Thumbnail",position = 5, dataType = "String")
        private String thumbnail;
        @ApiModelProperty(value = "작성 일시", position = 6, dataType = "String", required = true)
        private String datetime;

        protected BlogInfo() {}
        public BlogInfo(KakaoBlogResponse.Document info) {
            this.title = info.getTitle();
            this.contents = info.getContents();
            this.url = info.getUrl();
            this.blogname = info.getBlogname();
            this.thumbnail = info.getThumbnail();
            this.datetime = info.getDatetime();
        }
        public BlogInfo(NaverBlogResponse.Item info) {
            this.title = info.getTitle();
            this.contents = info.getDescription();
            this.url = info.getLink();
            this.blogname = info.getBloggername();
            this.thumbnail = "";
            this.datetime = info.getPostdate();
        }
    }


}
