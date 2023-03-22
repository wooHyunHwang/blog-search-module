# Search Module

### jar run script example

java -jar {jar file path} --spring.datasource.url=jdbc:h2:tcp://localhost/~/test --spring.datasource.username=sa --spring.datasource.password= --spring.redis.host=localhost --spring.redis.port=11000 --spring.redis.password=123456

### Used External Module

| External Module | Role In Current Module | note                               |
|:---------------:|:----------------------:|:-----------------------------------|
|     Docker      |           -            | Module Run Management              |
|   H2 Database   |    Top 10 키워드 쿼리 조회    | 요청받은 쿼리와 검색어 조회 건수 저장을 위한 영속성 컨텍스트 |
|    Rabbit MQ    |           X            | 동시성 방지를 위한 메시지 브로커                 |
|      Redis      |   캐싱된 Top 10 키워드 조회    | 빠른 검색어 조회를 위한 고속의 인 메모리 데이터 저장소    |