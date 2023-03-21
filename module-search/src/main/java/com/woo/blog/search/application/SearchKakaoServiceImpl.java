package com.woo.blog.search.application;

import com.woo.blog.search.infra.feign.KakaoClient;
import com.woo.blog.search.infra.feign.dto.KakaoBlogResponse;
import com.woo.blog.search.ui.dto.SearchRequest;
import com.woo.blog.search.ui.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchKakaoServiceImpl implements SearchService {

    private final KakaoClient kakaoClient;

    @Value("${ex.kakao.apiKey}")
    private String authorization;


    @Override
    public SearchResponse searchBlog(SearchRequest request) {

        KakaoBlogResponse response = kakaoClient.searchBlog(
                authorization,
                request.getQuery(),
                request.parseKakaoSort(),
                request.getPage(),
                request.getSize()
        );

        log.debug("Response : {}", response);

        return new SearchResponse(request, response);
    }
}
