package kr.sproutfx.common.logging.configuration;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import kr.sproutfx.common.logging.property.LoggingProperties;
import kr.sproutfx.common.logging.property.LogstashProperties;

import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;

@Configuration
@EnableConfigurationProperties
public class LoggingConfiguration {

    private final LoggingProperties loggingProperties;
    private final LogstashProperties logstashProperties;

    private final Logger logger = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Autowired
    public LoggingConfiguration(LoggingProperties loggingProperties, LogstashProperties logstashProperties) {
        this.loggingProperties = loggingProperties;
        this.logstashProperties = logstashProperties;

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        addLogstashAppender(loggerContext);
        
        logger.info("Initializing sproutfx.common.auto-configuration.logging");
    }

    private void addLogstashAppender(LoggerContext context) {
        LogstashTcpSocketAppender logstashAppender = new LogstashTcpSocketAppender();
        logstashAppender.setName(this.logstashProperties.getAppenderName());
        logstashAppender.setContext(context);
        String customFields = "{\"identifier\":\"" + this.loggingProperties.getIdentifier() + "\"}";
        
        // More documentation is available at:
        // https://github.com/logstash/logstash-logback-encoder
        LogstashEncoder logstashEncoder = new LogstashEncoder();
        // Set the Logstash appender config
        logstashEncoder.setCustomFields(customFields);
        logstashAppender.addDestinations(new InetSocketAddress(this.logstashProperties.getHost(), this.logstashProperties.getPort()));
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        logstashEncoder.setThrowableConverter(throwableConverter);
        logstashEncoder.setCustomFields(customFields);
        logstashAppender.setEncoder(logstashEncoder);

        logstashAppender.start();
        // Wrap the appender in an Async appender for performance
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName(this.logstashProperties.getAsyncAppenderName());
        asyncLogstashAppender.setQueueSize(this.logstashProperties.getQueueSize());
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();
        
        context.getLogger("ROOT").addAppender(asyncLogstashAppender);
    }
}
