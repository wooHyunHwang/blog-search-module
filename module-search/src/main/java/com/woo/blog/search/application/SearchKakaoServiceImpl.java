package com.woo.blog.search.application;

import com.woo.blog.search.infra.feign.KakaoClient;
import com.woo.blog.search.infra.feign.dto.KakaoBlogResponse;
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
    public Object searchBlog(String msg) {
        log.info("authorization : {}", authorization);

        KakaoBlogResponse res = kakaoClient.searchBlog(
                authorization,
                msg,
                "accuracy",
                1L,
                10L
        );

        log.info("Response : {}", res);

        return res;
    }
}
