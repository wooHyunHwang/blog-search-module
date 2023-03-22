package com.woo.blog.keyword.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Root Aggregate
@Table(name = "`TB_KEYWORD`")
@Entity
@Getter
public class Keyword {

    @EmbeddedId
    private KeywordId keywordId;

    @Column(name = "`SEARCH_COUNT`", nullable = false)
    private Long searchCount;

    /**
     * 기본 생성자
     */
    protected Keyword() {}
    public static Keyword of(String keywordStr) {
        Keyword keyword = new Keyword();
        keyword.keywordId = new KeywordId(keywordStr);
        keyword.searchCount = 1L;
        return keyword;
    }


    public void addSearchCount() {
        this.searchCount++;
    }
}
