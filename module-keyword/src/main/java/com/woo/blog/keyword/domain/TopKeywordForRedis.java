package com.woo.blog.keyword.domain;

import java.util.List;

public class TopKeywordForRedis {

    private List<Keyword> list;


    public static class Keyword {
        private String word;
        private long searchCount;
        private int rank;
    }

}
