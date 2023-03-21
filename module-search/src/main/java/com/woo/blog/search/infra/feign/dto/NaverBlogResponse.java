package com.woo.blog.search.infra.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverBlogResponse {


    private Long total;         // 총 검색 결과 수
    private Long start;         // 검색 시작 위치
    private Long display;       // 한 번에 표시할 검색 결과 개수
    private List<Item> items;   // 검색 결과


    @Data
    public static class Item {

        private String title;           // 블로그 포스트의 제목
        private String link;            // 블로그 포스트의 URL
        private String description;     // 블로그 포스트의 내용을 요약한 패시지 정보
        private String bloggername;     // 블로그의 이름
        private String bloggerlink;     // 블로그의 주소
        private String postdate;        // 블로그 포스트가 작성된 날짜

    }
}
