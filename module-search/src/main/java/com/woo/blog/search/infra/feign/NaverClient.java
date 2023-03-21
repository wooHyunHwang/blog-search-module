package com.woo.blog.search.infra.feign;

import com.woo.blog.search.infra.feign.dto.NaverBlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-client", url = "${ex.naver.host}")
public interface NaverClient {

    @GetMapping(value = "/v1/search/blog.json")
    NaverBlogResponse searchBlog(
            @RequestHeader("X-Naver-Client-Id") String clientId,
            @RequestHeader("X-Naver-Client-Secret") String clientSecret,
            @RequestParam("query") String query,
            @RequestParam("sort") String sort, // sim(정확도순) 또는 date(최신순)
            @RequestParam("start") int start,
            @RequestParam("display") int display
    );

}
