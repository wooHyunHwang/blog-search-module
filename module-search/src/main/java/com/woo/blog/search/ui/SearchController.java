package com.woo.blog.search.ui;

import com.woo.blog.search.application.SearchKakaoServiceImpl;
import com.woo.blog.search.application.SearchNaverServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Search API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search/v1")
public class SearchController {

    private final SearchKakaoServiceImpl kakaoService;
    private final SearchNaverServiceImpl naverService;

    @ApiOperation(value = "Test Api")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved info") })
    @GetMapping("/test/{msg}")
    public ResponseEntity<Object> test(@PathVariable String msg) {

        return ResponseEntity.ok(kakaoService.searchBlog(msg));
    }

    @ApiOperation(value = "Test Api 2")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved info") })
    @GetMapping("/test2/{msg}")
    public ResponseEntity<Object> test2(@PathVariable String msg) {

        return ResponseEntity.ok(naverService.searchBlog(msg));
    }

}
