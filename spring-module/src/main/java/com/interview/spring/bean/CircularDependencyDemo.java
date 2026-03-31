package com.interview.spring.bean;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 循环依赖及解决方案回顾
 * <p>
 * 循环依赖：Bean A 依赖 B，Bean B 依赖 A。
 * <p>
 * Spring 通过三级缓存解决单例 Bean 的循环依赖（仅限 setter/字段注入）：
 * 1. singletonObjects：一级缓存，已完成 Bean
 * 2. earlySingletonObjects：二级缓存，早期暴露的 Bean
 * 3. singletonFactories：三级缓存，Bean 工厂
 * <p>
 * 构造器注入无法解决循环依赖：双方都需完全构造才能注入。
 */
public class CircularDependencyDemo {
}

// ==================== 方式一：Setter 注入（Spring 可自动解决）====================

@Component
class ServiceA {
    private ServiceB serviceB;

    @Autowired
    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }

    public String callB() {
        return serviceB != null ? serviceB.getName() : "B-not-injected";
    }

    public String getName() {
        return "ServiceA";
    }
}

@Component
class ServiceB {
    private ServiceA serviceA;

    @Autowired
    public void setServiceA(ServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public String callA() {
        return serviceA != null ? serviceA.getName() : "A-not-injected";
    }

    public String getName() {
        return "ServiceB";
    }
}

// ==================== 方式二：@Lazy 打破循环（构造器注入场景）====================

@Component
class LazyServiceA {
    private final LazyServiceB serviceB;

    public LazyServiceA(@Lazy LazyServiceB serviceB) {
        this.serviceB = serviceB;
    }

    public String callB() {
        return serviceB.getName();
    }

    public String getName() {
        return "LazyServiceA";
    }
}

@Component
class LazyServiceB {
    private final LazyServiceA serviceA;

    public LazyServiceB(LazyServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public String callA() {
        return serviceA.getName();
    }

    public String getName() {
        return "LazyServiceB";
    }
}

// ==================== 方式三：ObjectProvider 延迟获取 ====================

@Component
class ProviderServiceA {
    private final ObjectProvider<ProviderServiceB> serviceBProvider;

    public ProviderServiceA(ObjectProvider<ProviderServiceB> serviceBProvider) {
        this.serviceBProvider = serviceBProvider;
    }

    public String callB() {
        return serviceBProvider.getObject().getName();
    }

    public String getName() {
        return "ProviderServiceA";
    }
}

@Component
class ProviderServiceB {
    private final ProviderServiceA serviceA;

    public ProviderServiceB(ProviderServiceA serviceA) {
        this.serviceA = serviceA;
    }

    public String callA() {
        return serviceA.getName();
    }

    public String getName() {
        return "ProviderServiceB";
    }
}

// ==================== 构造器注入循环依赖（会失败，仅作演示）====================
// 启用 profile "circular-fail" 时启动会抛出 BeanCurrentlyInCreationException

@Configuration
@Profile("circular-fail")
class CircularFailConfig {

    @Bean
    public ConstructorCircularA constructorCircularA(ConstructorCircularB b) {
        return new ConstructorCircularA(b);
    }

    @Bean
    public ConstructorCircularB constructorCircularB(ConstructorCircularA a) {
        return new ConstructorCircularB(a);
    }
}

class ConstructorCircularA {
    private final ConstructorCircularB b;

    ConstructorCircularA(ConstructorCircularB b) {
        this.b = b;
    }
}

class ConstructorCircularB {
    private final ConstructorCircularA a;

    ConstructorCircularB(ConstructorCircularA a) {
        this.a = a;
    }
}
