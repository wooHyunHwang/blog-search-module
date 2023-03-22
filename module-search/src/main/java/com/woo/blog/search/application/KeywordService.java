package com.woo.blog.search.application;

import com.woo.blog.search.domain.QueryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitMQ.exchange}")
    private String exchange;
    @Value("${app.rabbitMQ.routing}")
    private String routing;

    /**
     * Producer) Rabbit MQ 큐에 검색어 등록
     *  - 멀티 쓰레드를 이용하여 이 기능이 기존 조회 로직에 영향이 가지 않도록 병렬 처리
     *
     * @param query 검색어
     */
    public void produceKeyword(String query) {

        // 키워드 없는 경우 return
        if (StringUtils.isBlank(query)) return;

        // 멀티 쓰레드 처리
        String originalThread = Thread.currentThread().getName();
        ExecutorService executor = Executors.newFixedThreadPool(1);

        executor.execute(() -> {
            log.debug("[Executor] - Original Thread : {}", originalThread);

            // Produce Rabbit MQ
            rabbitTemplate.convertAndSend(exchange, routing, new QueryMessage(query));

            executor.shutdown();
        });
    }
}
