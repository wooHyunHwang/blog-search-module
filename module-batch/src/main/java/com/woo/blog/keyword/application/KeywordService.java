package com.woo.blog.keyword.application;

import com.woo.blog.keyword.domain.Keyword;
import com.woo.blog.keyword.domain.KeywordId;
import com.woo.blog.keyword.domain.QueryMessage;
import com.woo.blog.keyword.domain.TopKeywordForRedis;
import com.woo.blog.keyword.infra.redis.RedisRepository;
import com.woo.blog.keyword.infra.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {

	private final KeywordRepository keywordRepository;
	private final RedisRepository redisRepository;

	@Value("${app.redis.key}")
	private String redisKey;

	/**
	 * 5초마다 Redis 에 Top Keyword 반영 Update
	 */
	@Scheduled(cron = "0/30 * * * * ?")
	@Async("taskExecutor")
	public void updateTopKeyword() throws InterruptedException {
		log.info("########################################################");
		log.debug("Update Top Keyword in Redis ... ");
		this.updateRedisData();
		log.info("########################################################");
	}

	/**
	 * Redis 에 Top Keyword 최신화
	 */
	private void updateRedisData() {
		log.info("Redis 정보를 최신화 합니다.");
		List<Keyword> keywords = keywordRepository.findTop10ByOrderBySearchCountDesc();
		redisRepository.set(
				redisKey,
				new TopKeywordForRedis(keywords)
		);
	}

	/**
	 * RabbitMQ Consumer
	 * 	- 큐에서 전달받은 쿼리를 DB에 반영
	 *
	 * @param message 검색 query
	 */
	@RabbitListener(queues = "${app.rabbitMQ.queue}")
	@Transactional
	public void consumeKeyword(QueryMessage message) {
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		try {
			List<String> keywords = this.createKeywordListByQuery(message.getQuery());

			log.debug("Apply DataBase ... ");
			for (String keywordStr : keywords) {
				// 15자 보다 긴 단어는 생략
				if(StringUtils.length(keywordStr) > 15) break;

				// DB 적용
				Optional<Keyword> row = keywordRepository.findById(new KeywordId(keywordStr));
				if (row.isPresent()) {
					// 기존 키워드, 건수 증가
					row.get().addSearchCount();
				} else {
					// 새 키워드 생성
					keywordRepository.save(Keyword.of(keywordStr));
				}
			}
		} catch (Exception e) {
			log.error("Rabbit MQ Processing Error : ", e);
		}

		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}

	/**
	 * Query String의 좌우 여백을 제거하고, 공백을 기준으로 split 처리
	 *
	 * @param query 검색어
	 * @return List<String>
	 */
	private List<String> createKeywordListByQuery(String query) {
		if ( StringUtils.isNotBlank(query) ) {
			String trim = RegExUtils.replaceAll(StringUtils.trim(query), " +", " ");
			return Arrays.asList(StringUtils.split(trim, " "));
		} else {
			return Collections.emptyList();
		}
	}

}
