package com.woo.blog.keyword.infra.redis;

import java.util.List;

public class TopKeyword {

    private List<Keyword> list;


    public static class Keyword {
        private String word;
        private long searchCount;
        private int rank;
    }

}
