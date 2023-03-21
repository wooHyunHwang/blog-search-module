package com.woo.blog.search.application;

import com.woo.blog.search.ui.dto.SearchRequest;
import com.woo.blog.search.ui.dto.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class KeywordService {

    public void registKeyword(String query) {

        // 키워드 없는 경우 skip
        if (StringUtils.isBlank(query)) return;

        // 멀티 쓰레드 처리, 조회 Thread 와 분리하여 rabbit mq Produce 처리가
        // 조회에 영향을 끼치지 않도록 새 쓰레드 생성
        String originalThread = Thread.currentThread().getName();
        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.execute(() -> {
            log.debug("[Executor] - Original Thread : {}", originalThread);

            // TODO Rabbit MQ

            executor.shutdown();
        });
    }
}
