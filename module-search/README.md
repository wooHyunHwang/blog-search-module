# Search Module

### Script

- 명령어 사용시 {}, 중괄호에 알맞은 값으로 변경해주세요.

java -jar {jar file path} --ex.kakao.apiKey="KakaoAK {카카오 Authorization}" --ex.naver.clientId="{네이버 client id}" --ex.naver.clientSecret="{네이버 client secret}" --spring.rabbitmq.host={RabbitMQ host} --spring.rabbitmq.port={RabbitMQ port} --spring.rabbitmq.username={RabbitMQ username} --spring.rabbitmq.password={RabbitMQ password}

### Script Example

java -jar module-search/build/libs/module-search-1.0.0-SNAPSHOT.jar --ex.kakao.apiKey="KakaoAK {카카오 Authorization}" --ex.naver.clientId="{네이버 client id}" --ex.naver.clientSecret="{네이버 client secret}" --spring.rabbitmq.host=localhost --spring.rabbitmq.port=12000 --spring.rabbitmq.username=guest --spring.rabbitmq.password=guest

### Used External Module

| External Module | Role In Current Module | note                               |
|:---------------:|:----------------------:|:-----------------------------------|
|     Docker      |           -            | Module Run Management              |
|   H2 Database   |           X            | 요청받은 쿼리와 검색어 조회 건수 저장을 위한 영속성 컨텍스트 |
|    Rabbit MQ    |    producer) 검색어 전달    | 동시성 방지를 위한 메시지 브로커                 |
|      Redis      |           X            | 빠른 검색어 조회를 위한 고속의 인 메모리 데이터 저장소    |