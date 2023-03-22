package com.woo.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket defaultApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("default")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/*/v1/**"))
				.build();
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Blog Keyword Module API")
			.description("keyword 정보 조회")
			.version("1.0")
			.license("woo")
			.build();
	}

}
