package com.woo.blog.keyword.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Rabbit MQ에서 사용할 모델
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueryMessage {

    private String query;

}
