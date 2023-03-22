package com.woo.blog.keyword.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

// Root Aggregate
@Table(name = "`TB_KEYWORD`")
@Entity
@Getter
public class Keyword {

    @EmbeddedId
    private KeywordId orderId;

    @Column(name = "`SEARCH_COUNT`", nullable = false)
    private Long searchCount;

    /**
     * 기본 생성자
     */
    protected Keyword() {}

}
