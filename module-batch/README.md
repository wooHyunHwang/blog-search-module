# Batch Module

### jar run script example

java -jar {jar file path} --spring.datasource.url=jdbc:h2:tcp://{db host}/~/{database} --spring.datasource.username={db username} --spring.datasource.password= --spring.redis.host=localhost --spring.redis.port=11000 --spring.redis.password={db password} --spring.rabbitmq.host={RabbitMQ host} --spring.rabbitmq.port={RabbitMQ port} --spring.rabbitmq.username={RabbitMQ username} --spring.rabbitmq.password={RabbitMQ password}

### Script Example

java -jar module-batch/build/libs/module-batch-1.0.0-SNAPSHOT.jar --spring.datasource.url=jdbc:h2:tcp://localhost/~/test --spring.datasource.username=sa --spring.datasource.password= --spring.redis.host=localhost --spring.redis.port=11000 --spring.redis.password=123456 --spring.rabbitmq.host=localhost --spring.rabbitmq.port=12000 --spring.rabbitmq.username=guest --spring.rabbitmq.password=guest

### Used External Module

| External Module | Role In Current Module | note                               |
|:---------------:|:----------------------:|:-----------------------------------|
|     Docker      |           -            | Module Run Management              |
|   H2 Database   |  키워드 조회 및 수신한 검색어 등록   | 요청받은 쿼리와 검색어 조회 건수 저장을 위한 영속성 컨텍스트 |
|    Rabbit MQ    |    consumer) 검색어 수신    | 동시성 방지를 위한 메시지 브로커                 |
|      Redis      |   Top 10 키워드 데이터 캐싱    | 빠른 검색어 조회를 위한 고속의 인 메모리 데이터 저장소    |