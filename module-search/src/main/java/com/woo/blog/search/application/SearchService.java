package com.woo.blog.search.application;

import com.woo.blog.search.ui.dto.SearchRequest;
import com.woo.blog.search.ui.dto.SearchResponse;

public interface SearchService {

    public SearchResponse searchBlog(SearchRequest request);
}
