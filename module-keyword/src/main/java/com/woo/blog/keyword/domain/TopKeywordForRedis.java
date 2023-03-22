package com.woo.blog.keyword.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class TopKeywordForRedis {

    private List<RedisKeywordInfo> list;


    @Getter
    public static class RedisKeywordInfo {
        private String word;
        private long searchCount;
        private int rank;
    }

}
