package com.interview.spring.bean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 条件 Bean（@Conditional、@Profile）回顾
 * <p>
 * 满足条件时才注册 Bean，常用于：
 * - 不同环境启用不同实现（dev/test/prod）
 * - 根据配置开关启用功能
 */
public class BeanConditionalDemo {
}

// ==================== @Profile ====================
// 根据激活的 profile 决定是否注册

@Component
@Profile("dev")
class DevDataSource {
    public String getUrl() {
        return "jdbc:h2:mem:dev";
    }
}

@Component
@Profile("prod")
class ProdDataSource {
    public String getUrl() {
        return "jdbc:mysql://prod-db:3306/app";
    }
}

@Component
@Profile({"dev", "test"})
class DevTestFeature {
    public String getFeature() {
        return "dev-test-only";
    }
}

// ==================== @ConditionalOnProperty ====================
// 根据配置属性决定是否注册

@Configuration
class ConditionalBeanConfig {

    @Bean
    @ConditionalOnProperty(name = "feature.cache.enabled", havingValue = "true")
    public CacheService cacheService() {
        return new CacheService();
    }

    @Bean
    @ConditionalOnProperty(name = "feature.audit.enabled", havingValue = "true", matchIfMissing = true)
    public AuditService auditService() {
        return new AuditService();
    }
}

class CacheService {
    public String get(String key) {
        return "cached-" + key;
    }
}

class AuditService {
    public void log(String action) {
        // 审计日志
    }
}
