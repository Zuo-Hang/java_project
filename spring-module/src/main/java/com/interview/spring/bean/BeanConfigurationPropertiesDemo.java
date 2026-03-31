package com.interview.spring.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 配置属性 Bean（@ConfigurationProperties）回顾
 * <p>
 * 将 application.yml/properties 中的配置绑定到 Java 对象，
 * 支持嵌套、校验、类型安全。
 */
public class BeanConfigurationPropertiesDemo {
}

// ==================== 类级别 @ConfigurationProperties ====================

@Component
@ConfigurationProperties(prefix = "app.demo")
class AppDemoProperties {
    private String name = "default";
    private int timeout = 30;
    private NestedConfig nested = new NestedConfig();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public NestedConfig getNested() {
        return nested;
    }

    public void setNested(NestedConfig nested) {
        this.nested = nested;
    }

    static class NestedConfig {
        private boolean enabled = false;
        private String path = "/api";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

// ==================== @Bean + @ConfigurationProperties ====================
// 适用于第三方类或需要单独命名的配置

@Configuration
class ConfigPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "custom.service")
    public CustomServiceProperties customServiceProperties() {
        return new CustomServiceProperties();
    }
}

class CustomServiceProperties {
    private String endpoint = "http://localhost";
    private int retries = 3;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
