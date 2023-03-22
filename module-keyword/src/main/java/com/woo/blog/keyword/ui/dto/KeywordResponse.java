package com.woo.blog.keyword.ui.dto;

import com.woo.blog.keyword.domain.Keyword;
import com.woo.blog.keyword.domain.TopKeywordForRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Schema(title = "키워드 조회 응답")
@Data
public class KeywordResponse {

    @Schema(name = "조회 결과 코드", allowableValues = "200, 500", type = "int", required = true)
    private int status = HttpStatus.OK.value();
    @Schema(name = "메시지", type = "String", required = true)
    private String message = "정상 처리됐습니다.";

    @Schema(name = "조회 결과", type = "Object", required = true)
    private List<KeywordInfo> result;

    public KeywordResponse() {
        this.result = new ArrayList<>();
    }
    public KeywordResponse(List<Keyword> keywords) {
        this();
        if (keywords == null || keywords.isEmpty()) return;
        for (int i = 0; i < keywords.size(); i++) {
            this.result.add(
                    new KeywordInfo(i + 1, keywords.get(i))
            );
        }
    }
    public KeywordResponse(TopKeywordForRedis topKeyword) {
        this();
        if (topKeyword == null || topKeyword.getList() == null || topKeyword.getList().isEmpty()) return;
        for (TopKeywordForRedis.RedisKeywordInfo redisKeywordInfo : topKeyword.getList()) {
            this.result.add(new KeywordInfo(redisKeywordInfo));
        }
    }

    /**
     * 에러 데이터 설정
     */
    public void setErrorData() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = "처리 중 에러가 발생했습니다.";
    }


    @Schema(title = "키워드 내용")
    @Getter
    public static class KeywordInfo {
        @Schema(name = "단어", type = "String", required = true)
        private String word;
        @Schema(name = "검색 건수", type = "long", required = true)
        private long searchCount;
        @Schema(name = "순위", type = "int", required = true)
        private int rank;


        protected KeywordInfo() {}
        public KeywordInfo(int rank, Keyword keyword) {
            this.word = keyword.getKeywordId().getId();
            this.searchCount = keyword.getSearchCount();
            this.rank = rank;
        }
        public KeywordInfo(TopKeywordForRedis.RedisKeywordInfo keyword) {
            this.word = keyword.getWord();
            this.searchCount = keyword.getSearchCount();
            this.rank = keyword.getRank();
        }

    }

}
