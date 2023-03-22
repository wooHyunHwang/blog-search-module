package com.woo.blog.keyword.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TopKeywordForRedis {

    private List<RedisKeywordInfo> list;


    protected TopKeywordForRedis() {
        this.list = new ArrayList<>();
    }
    public TopKeywordForRedis(List<Keyword> keywords) {
        this();

        if (keywords == null || keywords.isEmpty()) return;

        for (int i = 0; i < keywords.size(); i++) {
            this.list.add(new RedisKeywordInfo(i + 1, keywords.get(i)));
        }
    }

    @Getter
    public static class RedisKeywordInfo {
        private String word;
        private long searchCount;
        private int rank;

        protected RedisKeywordInfo() {}
        public RedisKeywordInfo(int rank, Keyword keyword) {
            this.word = keyword.getKeywordId().getId();
            this.searchCount = keyword.getSearchCount();
            this.rank = rank;
        }
    }

}
