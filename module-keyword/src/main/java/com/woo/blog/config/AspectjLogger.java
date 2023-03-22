package com.woo.blog.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AspectjLogger {

	/**
	 * Rest API Around Logger
	 */
	@Around("execution(* com.woo.blog.*.ui.*Controller.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		long startTime = System.currentTimeMillis();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		String reqUri = request.getRequestURI();
		String method = request.getMethod();
		String contentType = request.getHeader("Content-Type");

		log.info("########################################################");
		log.info("Request Uri : {}", reqUri);
		log.info("ContentType : {}", contentType);
		log.info("Method	  : {}", method);
		log.info("########################################################");

		Object[] args = joinPoint.getArgs();
		if (args != null) {
			for (Object arg : args) {
				log.info("args   : {}", arg);
			}
		}

		log.info("########################################################");

		// Controller
		Object result = joinPoint.proceed();

		if (result != null) {
			log.info("response   : {}", result);
		}

		log.info("It Takes Time [{}]", System.currentTimeMillis() - startTime);

		return result;
	}

}
