package com.woo.blog.search.infra.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class KakaoBlogResponse {

    private Meta meta;                  // 정보성 데이터
    private List<Document> documents;   // 조회 문서 결과

    @Data
    public static class Meta {

        private Long total_count;       // 전체 건수
        private Long pageable_count;    // 노출 가능 문서 수
        private Boolean is_end;         // 추가 페이징 가능 여부

    }

    @Data
    public static class Document {

        private String title;       // 블로그 글 제목
        private String contents;    // 블로그 글 요약
        private String url;         // 블로그 글 URL
        private String blogname;    // 블로그의 이름
        private String thumbnail;   // 대표 미리보기 이미지 URL
        private String datetime;    // 블로그 글 작성시간

    }
}
