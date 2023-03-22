package com.woo.blog.keyword.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordService {


	/**
	 * 30초마다 Queue 확인
	 */
	@Scheduled(cron = "30 * * * * ?")
	@Async("taskExecutor")
	public void checkQueue() throws InterruptedException {
		log.info("########################################################");
		log.info("실행...");
		Thread.sleep(1000 * 40);
		log.info("실행 끝");
		log.info("########################################################");
	}

}
