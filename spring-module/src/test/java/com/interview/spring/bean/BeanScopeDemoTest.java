package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean 作用域 - 单元测试
 */
@SpringBootTest
class BeanScopeDemoTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SingletonScopeBean singleton1;

    @Autowired
    private SingletonScopeBean singleton2;

    @Test
    void singleton_多次注入应为同一实例() {
        assertThat(singleton1).isSameAs(singleton2);
        singleton1.increment();
        assertThat(singleton2.increment()).isEqualTo(2);
    }

    @Test
    void prototype_每次getBean应为新实例() {
        PrototypeScopeBean p1 = applicationContext.getBean(PrototypeScopeBean.class);
        PrototypeScopeBean p2 = applicationContext.getBean(PrototypeScopeBean.class);

        assertThat(p1).isNotSameAs(p2);
        assertThat(p1.increment()).isEqualTo(1);
        assertThat(p2.increment()).isEqualTo(1);
    }

    @Test
    void uniqueIdGenerator_原型Bean_每次获取不同id() {
        UniqueIdGenerator g1 = applicationContext.getBean(UniqueIdGenerator.class);
        UniqueIdGenerator g2 = applicationContext.getBean(UniqueIdGenerator.class);

        assertThat(g1).isNotSameAs(g2);
        assertThat(g1.getId()).isNotEqualTo(g2.getId());
    }

    @Test
    void applicationScope_单例行为() {
        ApplicationScopeBean app1 = applicationContext.getBean(ApplicationScopeBean.class);
        ApplicationScopeBean app2 = applicationContext.getBean(ApplicationScopeBean.class);

        assertThat(app1).isSameAs(app2);
        assertThat(app1.incrementVisit()).isEqualTo(1);
        assertThat(app2.incrementVisit()).isEqualTo(2);
    }
}
