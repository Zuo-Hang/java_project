package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean 生命周期 - 单元测试
 * <p>
 * 销毁回调（@PreDestroy、DisposableBean.destroy）在容器关闭时执行，可通过启动应用后正常退出观察。
 */
@SpringBootTest
class BeanLifecycleDemoTest {

    @Autowired
    private PostConstructBean postConstructBean;

    @Autowired
    private SpringLifecycleBean springLifecycleBean;

    @Autowired
    private CustomLifecycleBean customLifecycleBean;

    @Autowired
    private FullLifecycleBean fullLifecycleBean;

    @Test
    void postConstructBean_初始化时已执行PostConstruct() {
        assertThat(postConstructBean.getLifecycleLog()).containsExactly("PostConstruct");
    }

    @Test
    void springLifecycleBean_初始化时已执行afterPropertiesSet() {
        assertThat(springLifecycleBean.getLifecycleLog()).containsExactly("InitializingBean.afterPropertiesSet");
    }

    @Test
    void customLifecycleBean_初始化时已执行initMethod() {
        assertThat(customLifecycleBean.getLifecycleLog()).containsExactly("initMethod");
    }

    @Test
    void fullLifecycleBean_初始化顺序正确() {
        assertThat(fullLifecycleBean.getLifecycleLog())
                .containsExactly("1-PostConstruct", "2-afterPropertiesSet");
    }
}
