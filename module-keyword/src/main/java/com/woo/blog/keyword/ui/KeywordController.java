package com.woo.blog.keyword.ui;

import com.woo.blog.keyword.ui.dto.KeywordResponse;
import com.woo.blog.keyword.application.KeywordService;
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

@Api(tags = {"Keyword API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword/v1")
public class KeywordController {

    private final KeywordService keywordService;


    @ApiOperation(value = "Keyword Api")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "정상 처리됐습니다.") })
    @GetMapping("/top")
    public ResponseEntity<KeywordResponse> searchKeywordTop() {

        KeywordResponse dto = keywordService.selectTop10Keyword();

        if (dto.getStatus() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(keywordService.selectTop10Keyword());
        } else {
            return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
