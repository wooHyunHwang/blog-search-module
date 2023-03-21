package com.woo.blog.search.application;

import com.woo.blog.search.infra.feign.KakaoClient;
import com.woo.blog.search.infra.feign.dto.KakaoBlogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public interface SearchService {

    public Object searchBlog(String msg);
}
