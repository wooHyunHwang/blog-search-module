package com.woo.blog.keyword.application;

import com.woo.blog.keyword.infra.redis.RedisRepository;
import com.woo.blog.keyword.infra.redis.TopKeyword;
import com.woo.blog.keyword.infra.repository.KeywordRepository;
import com.woo.blog.keyword.ui.dto.KeywordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final RedisRepository redisRepository;
    private final KeywordRepository keywordRepository;

    @Value("${app.redis.key}")
    private String redisKey;

    public KeywordResponse selectTop10Keyword() {

        // 1. Redis 조회
        try {
            // TODO Redis 연동
            Optional<Object> topOp = Optional.ofNullable(redisRepository.get(redisKey));
            if (topOp.isPresent()) {
                TopKeyword topKeyword = (TopKeyword) topOp.get();
                log.info("topKeyword : {}", topKeyword);
            }
        } catch (Exception e) {
            throw e;
        }

        // 2. 실패시 DB에서 조회
        try {
            return new KeywordResponse(
                    keywordRepository.findTop10ByOrderBySearchCountDesc()
            );
        } catch (Exception e) {
            log.error("DB 조회 실패 : {}", e);
            KeywordResponse dto = new KeywordResponse();
            dto.setErrorData();
            return dto;
        }
    }
}
