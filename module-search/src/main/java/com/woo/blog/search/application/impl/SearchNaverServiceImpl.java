package com.woo.blog.search.application.impl;

import com.woo.blog.search.application.SearchService;
import com.woo.blog.search.infra.feign.NaverClient;
import com.woo.blog.search.infra.feign.dto.NaverBlogResponse;
import com.woo.blog.search.ui.dto.SearchRequest;
import com.woo.blog.search.ui.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchNaverServiceImpl implements SearchService {

    private final NaverClient naverClient;

    @Value("${ex.naver.clientId}")
    private String clientId;
    @Value("${ex.naver.clientSecret}")
    private String clientSecret;

    @Override
    public SearchResponse searchBlog(SearchRequest request) {

        NaverBlogResponse response = naverClient.searchBlog(
                clientId,
                clientSecret,
                request.getQuery(),
                request.parseNaverSort(),
                request.getPage(),
                request.getSize()
        );

        log.debug("Response : {}", response);

        return new SearchResponse(request, response);

    }
}
