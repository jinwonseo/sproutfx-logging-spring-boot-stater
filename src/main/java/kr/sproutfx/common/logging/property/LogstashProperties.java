package kr.sproutfx.common.logging.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sproutfx.logging.logstash")
public class LogstashProperties {
    private String appenderName;
    private String asyncAppenderName;
    private String host;
    private Integer port;
    private Integer queueSize;

    public String getAppenderName() {
        return appenderName;
    }
    public void setAppenderName(String appenderName) {
        this.appenderName = appenderName;
    }

    public String getAsyncAppenderName() {
        return asyncAppenderName;
    }
    public void setAsyncAppenderName(String asyncAppenderName) {
        this.asyncAppenderName = asyncAppenderName;
    }

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getQueueSize() {
        return queueSize;
    }
    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }
}
