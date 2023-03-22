package com.woo.blog.keyword.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class KeywordId implements Serializable {

    @Column(name="`KEYWORD`", length = 15)
    private String id;

    protected KeywordId() {}
    protected KeywordId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KeywordId) return StringUtils.equals(this.id, ((KeywordId) obj).getId());
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
