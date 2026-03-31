package com.interview.spring.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Bean 作用域（Scope）回顾
 * <p>
 * Spring 支持的 Bean 作用域：
 * 1. singleton：单例（默认），容器中仅一个实例
 * 2. prototype：原型/多例，每次获取创建新实例
 * 3. request：每个 HTTP 请求一个实例（仅 Web）
 * 4. session：每个 HTTP 会话一个实例（仅 Web）
 * 5. application：每个 ServletContext 一个实例（仅 Web）
 */
public class BeanScopeDemo {
}

// ==================== singleton（默认）====================

@Component
class SingletonScopeBean {
    private int count = 0;

    public int increment() {
        return ++count;
    }
}

// ==================== prototype ====================

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PrototypeScopeBean {
    private int count = 0;

    public int increment() {
        return ++count;
    }
}

// ==================== request（需 Web 环境）====================

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
class RequestScopeBean {
    private final String id = java.util.UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}

// ==================== session（需 Web 环境）====================

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
class SessionScopeBean {
    private final String id = java.util.UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}

// ==================== application ====================

@Component
@Scope(WebApplicationContext.SCOPE_APPLICATION)
class ApplicationScopeBean {
    private int visitCount = 0;

    public int incrementVisit() {
        return ++visitCount;
    }
}

// ==================== 通过 @Bean 声明作用域 ====================

@Configuration
class ScopeBeanConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UniqueIdGenerator uniqueIdGenerator() {
        return new UniqueIdGenerator(java.util.UUID.randomUUID().toString());
    }
}

class UniqueIdGenerator {
    private final String id;

    UniqueIdGenerator(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
