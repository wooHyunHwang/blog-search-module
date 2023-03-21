package com.woo.blog.search.ui.dto;

import com.woo.blog.search.infra.feign.dto.KakaoBlogResponse;
import com.woo.blog.search.infra.feign.dto.NaverBlogResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Schema(title = "블로그 조회 응답")
@Data
public class SearchResponse {

    @Schema(name = "조회 결과 코드", allowableValues = "200, 500", type = "int", required = true)
    private int status = HttpStatus.OK.value();
    @Schema(name = "메시지", type = "String", required = true)
    private String message = "정상 처리됐습니다.";

    @Schema(name = "조회 결과", type = "Object", required = true)
    private SearchResult result;

    public SearchResponse() {}
    public SearchResponse(SearchRequest request, KakaoBlogResponse kakao) {
        this.result = new SearchResult(request, kakao);
    }
    public SearchResponse(SearchRequest request, NaverBlogResponse naver) {
        this.result = new SearchResult(request, naver);
    }

    @Schema(title = "조회 결과")
    @Getter
    public static class SearchResult {
        @Schema(name = "페이지 번호", type = "int", required = true)
        private int page;
        @Schema(name = "한번에 조회한 건수", type = "int", required = true)
        private int size;
        @Schema(name = "전체 건수", type = "long", required = true)
        private long totalCount;
        @Schema(name = "조회된 건수", type = "int", required = true)
        private int selectCount;
        @Schema(name = "출처", allowableValues = "KAKAO, NAVER", type = "String", required = true)
        private String reference = "";

        @Schema(name = "블로그 목록", type = "List", required = true)
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

    @Schema(title = "블로그 정보")
    @Getter
    public static class BlogInfo {

        @Schema(name = "블로그 글 제목", type = "String", required = true)
        private String title;
        @Schema(name = "블로그 글 요약", type = "String", required = true)
        private String contents;
        @Schema(name = "블로그 글 URL", type = "String", required = true)
        private String url;
        @Schema(name = "블로그 이름", type = "String", required = true)
        private String blogname;
        @Schema(name = "블로그 Thumbnail", type = "String")
        private String thumbnail;
        @Schema(name = "작성 일시", type = "String", required = true)
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
