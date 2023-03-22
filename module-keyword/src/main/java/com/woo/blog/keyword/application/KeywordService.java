package com.woo.blog.keyword.application;

import com.woo.blog.keyword.domain.TopKeywordForRedis;
import com.woo.blog.keyword.infra.redis.RedisRepository;
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

    /**
     * Top 10 검색어 조회
     *  1. Redis에서 Top 10 키워드 조회 시도
     *  2. Redis 이용 불가할 경우 DB에서 Top 10 키워드 조회
     * 
     * @return KeywordResponse
     */
    public KeywordResponse selectTop10Keyword() {

        // 1. Redis 조회
        try {
            // Redis 연동
            Optional<Object> topOp = Optional.ofNullable(redisRepository.get(redisKey));
            if (topOp.isPresent()) {
                TopKeywordForRedis topKeyword = (TopKeywordForRedis) topOp.get();
                log.debug("reference is redis");

                return new KeywordResponse(topKeyword);
            }

            // 아직 Redis에 저장된 데이터가 없거나 배치가 실행되지 않음

        } catch (Exception e) {
            log.error("Redis 조회 처리 에러 : {}", e);
        }

        // 2. Redis 조회 실패시 DB 에서 조회
        try {
            log.debug("reference is database");
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
