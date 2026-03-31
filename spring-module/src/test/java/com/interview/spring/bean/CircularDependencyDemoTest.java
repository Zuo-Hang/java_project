package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 循环依赖 - 单元测试
 */
@SpringBootTest(classes = com.interview.spring.SpringModuleApplication.class)
class CircularDependencyDemoTest {

    @Autowired
    private ServiceA serviceA;

    @Autowired
    private ServiceB serviceB;

    @Autowired
    private LazyServiceA lazyServiceA;

    @Autowired
    private LazyServiceB lazyServiceB;

    @Autowired
    private ProviderServiceA providerServiceA;

    @Test
    void setter注入_循环依赖可被Spring自动解决() {
        assertThat(serviceA.callB()).isEqualTo("ServiceB");
        assertThat(serviceB.callA()).isEqualTo("ServiceA");
    }

    @Test
    void lazy注入_可打破构造器循环依赖() {
        assertThat(lazyServiceA.callB()).isEqualTo("LazyServiceB");
        assertThat(lazyServiceB.callA()).isEqualTo("LazyServiceA");
    }

    @Test
    void objectProvider_可打破循环依赖() {
        assertThat(providerServiceA.callB()).isEqualTo("ProviderServiceB");
    }
}
