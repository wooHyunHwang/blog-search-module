package com.woo.blog.search.infra.feign;

import com.woo.blog.search.infra.feign.dto.KakaoBlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-client", url = "${ex.kakao.host}")
public interface KakaoClient {

    @GetMapping(value = "/v2/search/blog")
    KakaoBlogResponse searchBlog(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("query") String query,
            @RequestParam("sort") String sort, // accuracy(정확도순) 또는 recency(최신순)
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

}
