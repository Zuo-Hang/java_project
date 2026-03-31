package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean 依赖注入方式 - 单元测试
 */
@SpringBootTest
class BeanDependencyInjectionDemoTest {

    @Autowired
    private ConstructorInjectionService constructorInjectionService;

    @Autowired
    private FieldInjectionService fieldInjectionService;

    @Autowired
    private SetterInjectionService setterInjectionService;

    @Autowired
    private ResourceInjectionService resourceInjectionService;

    @Autowired
    private ObjectProviderService objectProviderService;

    @Test
    void 构造器注入_应正确工作() {
        assertThat(constructorInjectionService.getOrder(1L)).isEqualTo("order-1");
    }

    @Test
    void 字段注入_应正确工作() {
        assertThat(fieldInjectionService.getOrder(2L)).isEqualTo("order-2");
    }

    @Test
    void Setter注入_应正确工作() {
        assertThat(setterInjectionService.getOrder(3L)).isEqualTo("order-3");
    }

    @Test
    void Resource注入_应正确工作() {
        assertThat(resourceInjectionService.getOrder(4L)).isEqualTo("order-4");
    }

    @Test
    void ObjectProvider_每次getObject应获取新原型实例() {
        int c1 = objectProviderService.getFreshCount();
        int c2 = objectProviderService.getFreshCount();
        assertThat(c1).isEqualTo(1);
        assertThat(c2).isEqualTo(1);  // 每次新实例，各自从 0 开始 increment
    }
}
