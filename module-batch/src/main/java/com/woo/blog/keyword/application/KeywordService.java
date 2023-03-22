package com.woo.blog.keyword.application;

import com.woo.blog.keyword.domain.Keyword;
import com.woo.blog.keyword.domain.TopKeywordForRedis;
import com.woo.blog.keyword.infra.redis.RedisRepository;
import com.woo.blog.keyword.infra.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
	 * 10초마다 Redis 에 Top Keyword 반영 Update
	 */
	@Scheduled(cron = "0/20 * * * * ?")
	@Async("taskExecutor")
	public void updateTopKeyword() throws InterruptedException {
		log.info("########################################################");
		log.debug("Update Top Keyword in Redis ... ");
		this.updateRedisData();
		log.info("########################################################");
	}

	/**
	 * 레디스에 Top Keyword 최신화
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
	 *
	 * @param query 검색 query
	 */
	@RabbitListener(queues = "${app.rabbitMQ.queue}")
	@Transactional
	public void receiveMessage(String query) {
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		List<String> keywords = Keyword.createKeywordListByQuery(query);

		log.debug("Apply DataBase ... ");
		for (String keywordStr : keywords) {
			// 15자 보다 긴 단어는 생략
			if(StringUtils.length(keywordStr) > 15) break;
			
			// DB 적용
			Optional<Keyword> row = keywordRepository.findById(keywordStr);
			if (row.isPresent()) {
				// 기존 키워드, 건수 증가
				row.get().addSearchCount();
			} else {
				// 새 키워드 생성
				keywordRepository.save(new Keyword(keywordStr));
			}
		}

		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}

}
