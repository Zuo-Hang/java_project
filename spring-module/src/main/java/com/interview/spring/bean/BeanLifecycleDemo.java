package com.interview.spring.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean 生命周期回顾
 * <p>
 * Spring Bean 生命周期：实例化 → 属性注入 → 初始化 → 使用 → 销毁
 * <p>
 * 初始化回调（按执行顺序）：
 * 1. @PostConstruct
 * 2. InitializingBean.afterPropertiesSet()
 * 3. @Bean(initMethod = "xxx")
 * <p>
 * 销毁回调（按执行顺序）：
 * 1. @PreDestroy
 * 2. DisposableBean.destroy()
 * 3. @Bean(destroyMethod = "xxx")
 */
public class BeanLifecycleDemo {
}

// ==================== 1. @PostConstruct / @PreDestroy（JSR-250）====================

@Component
class PostConstructBean {
    private final List<String> lifecycleLog = new ArrayList<>();

    @PostConstruct
    public void init() {
        lifecycleLog.add("PostConstruct");
    }

    @PreDestroy
    public void cleanup() {
        lifecycleLog.add("PreDestroy");
    }

    public List<String> getLifecycleLog() {
        return lifecycleLog;
    }
}

// ==================== 2. InitializingBean / DisposableBean（Spring 接口）====================

@Component
class SpringLifecycleBean implements InitializingBean, DisposableBean {
    private final List<String> lifecycleLog = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        lifecycleLog.add("InitializingBean.afterPropertiesSet");
    }

    @Override
    public void destroy() {
        lifecycleLog.add("DisposableBean.destroy");
    }

    public List<String> getLifecycleLog() {
        return lifecycleLog;
    }
}

// ==================== 3. @Bean initMethod / destroyMethod ====================

@Configuration
class LifecycleBeanConfig {

    @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
    public CustomLifecycleBean customLifecycleBean() {
        return new CustomLifecycleBean();
    }
}

class CustomLifecycleBean {
    private final List<String> lifecycleLog = new ArrayList<>();

    public void customInit() {
        lifecycleLog.add("initMethod");
    }

    public void customDestroy() {
        lifecycleLog.add("destroyMethod");
    }

    public List<String> getLifecycleLog() {
        return lifecycleLog;
    }
}

// ==================== 4. 组合使用（展示执行顺序）====================

@Component
class FullLifecycleBean implements InitializingBean, DisposableBean {
    private final List<String> lifecycleLog = new ArrayList<>();

    @PostConstruct
    public void postConstruct() {
        lifecycleLog.add("1-PostConstruct");
    }

    @Override
    public void afterPropertiesSet() {
        lifecycleLog.add("2-afterPropertiesSet");
    }

    @PreDestroy
    public void preDestroy() {
        lifecycleLog.add("4-PreDestroy");
    }

    @Override
    public void destroy() {
        lifecycleLog.add("5-DisposableBean.destroy");
    }

    public List<String> getLifecycleLog() {
        return lifecycleLog;
    }
}
