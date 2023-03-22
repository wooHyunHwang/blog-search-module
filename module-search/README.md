# Search Module

### jar run script example

```
// jar 실행
java -jar {jar file path} --ex.kakao.apiKey="KakaoAK {카카오 Authorization}" --ex.naver.clientId="{네이버 client id}" --ex.naver.clientSecret="{네이버 client secret}"
```

### Used External Module

| External Module | Role In Current Module | note                               |
|:---------------:|:----------------------:|:-----------------------------------|
|     Docker      |           -            | Module Run Management              |
|   H2 Database   |                        | 요청받은 쿼리와 검색어 조회 건수 저장을 위한 영속성 컨텍스트 |
|    Rabbit MQ    |                        | Keyword 저장 처리와 동시성 방지를 위한 메시지 브로커  |
|      Redis      |                        | 빠른 검색어 조회를 위한 고속의 인 메모리 데이터 저장소    |