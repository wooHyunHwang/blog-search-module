package com.woo.blog.keyword.application;

import com.woo.blog.keyword.domain.Keyword;
import com.woo.blog.keyword.infra.KeywordRepository;
import com.woo.blog.keyword.ui.dto.KeywordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordResponse selectTop10Keyword() {

        // 1. Redis 조회
        try {
            // TODO Redis 연동
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
