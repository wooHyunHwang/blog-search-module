package com.woo.blog.search.infra.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class KakaoBlogResponse {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {

        private Long total_count;
        private Long pageable_count;
        private Boolean is_end;

    }

    @Data
    public static class Document {

        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private String datetime;

    }
}
