package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean 懒加载 - 单元测试
 */
@SpringBootTest
class BeanLazyDemoTest {

    @Autowired
    private LazyConsumer lazyConsumer;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void lazyConsumer_首次调用时才初始化LazyHeavyService() {
        assertThat(lazyConsumer.use()).isEqualTo("lazy-initialized");
    }

    @Test
    void lazyConfigBean_首次getBean时才创建() {
        LazyConfigBean bean = applicationContext.getBean(LazyConfigBean.class);
        assertThat(bean.getValue()).isEqualTo("created-on-first-use");
    }
}
