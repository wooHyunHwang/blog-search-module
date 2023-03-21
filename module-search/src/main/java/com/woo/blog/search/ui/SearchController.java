package com.woo.blog.search.ui;

import com.woo.blog.search.application.KeywordService;
import com.woo.blog.search.application.SearchKakaoServiceImpl;
import com.woo.blog.search.application.SearchNaverServiceImpl;
import com.woo.blog.search.ui.dto.SearchRequest;
import com.woo.blog.search.ui.dto.SearchResponse;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Api(tags = {"Search API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search/v1")
public class SearchController {

    private final KeywordService keywordService;

    private final SearchKakaoServiceImpl kakaoService;
    private final SearchNaverServiceImpl naverService;

    @ApiOperation(value = "Search Blog Api")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "정상 처리됐습니다.") })
    @GetMapping("/blog")
    public ResponseEntity<SearchResponse> searchBlog(@Valid SearchRequest request) {

        // 키워드 등록 요청
        keywordService.produceKeyword(request.getQuery());

        try {
            // Kakao Request
            return ResponseEntity.ok(kakaoService.searchBlog(request));
        } catch (FeignException kfe) {
            log.info("Kakao Search error : {}", kfe);
            try {
                // Naver Request
                return ResponseEntity.ok(naverService.searchBlog(request));
            } catch (FeignException nfe) {
                log.info("Naver Search error : {}", nfe);

                SearchResponse dto = new SearchResponse();

                dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                dto.setMessage("플랫폼이 원할하지 않습니다.");
                dto.setResult(new SearchResponse.SearchResult(request));

                return ResponseEntity.ok(dto);
            }
        }
    }

}
