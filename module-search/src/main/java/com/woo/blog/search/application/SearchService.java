package com.woo.blog.search.application;

import com.woo.blog.search.ui.dto.SearchRequest;
import com.woo.blog.search.ui.dto.SearchResponse;

/**
 * 카카오 API, 네이버 API 이외의
 * 검색 소스 추가를 고려하여 DI 적용
 */
public interface SearchService {

    public SearchResponse searchBlog(SearchRequest request);
}
