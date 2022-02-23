# sproutfx-logstash-appender-spring-boot-stater

* Deploy maven repository

  ```bash
  $ mvn clean package deploy
  ```

* Import auto-configuration

  ```xml
  <!-- Logging -->
  <dependency>
    <groupId>kr.sproutfx.common</groupId>
    <artifactId>sproutfx-common-logging-spring-boot-stater</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>
  ```

* Add spring boot property

  ```yml
  sproutfx:
    logging:
      identifier: identifier (eg. ${spring.application.name}-${spring.profiles.active}) 
      logstash:
        appender-name: Name of logstash appender
        async-appender-name: Name of logstash async appender
        host: logstash host (eg. 127.0.0.1)
        port: logstash port (eg. 5045)
        queue-size: logstash queue size (eg. 512)
  ```