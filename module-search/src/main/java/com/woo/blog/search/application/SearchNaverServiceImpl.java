package com.woo.blog.search.application;

import com.woo.blog.search.infra.feign.NaverClient;
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
    public Object searchBlog(String msg) {

        return naverClient.searchBlog(
                clientId,
                clientSecret,
                "김치",
                "sim",
                1L,
                10L
        );
    }
}
