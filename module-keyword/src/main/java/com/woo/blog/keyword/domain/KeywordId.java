package com.woo.blog.keyword.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
public class KeywordId implements Serializable {

    @Column(name="`KEYWORD`")
    private String id;

    protected KeywordId() {}

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
