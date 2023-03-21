package com.woo.blog.search.infra.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverBlogResponse {


    private Long total;
    private Long start;
    private Long display;
    private List<Item> items;


    @Data
    public static class Item {

        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private String postdate;

    }
}
