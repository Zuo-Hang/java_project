package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean 创建方式 - 单元测试验证
 */
@SpringBootTest
class BeanCreationDemoTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ComponentScanBean componentScanBean;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("customBeanName")
    private String customStringBean;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private Tool tool;

    @Test
    void componentScanBean_应被正确创建() {
        assertThat(componentScanBean).isNotNull();
        assertThat(componentScanBean.getName()).isEqualTo("component-scan-bean");
    }

    @Test
    void orderService_应被正确创建() {
        assertThat(orderService).isNotNull();
        assertThat(orderService.getOrder()).isEqualTo("order-from-service");
    }

    @Test
    void orderRepository_应被正确创建() {
        assertThat(orderRepository).isNotNull();
        assertThat(orderRepository.findById(1L)).isEqualTo("order-1");
    }

    @Test
    void userService_通过Bean方法创建_应能注入OrderRepository() {
        assertThat(userService).isNotNull();
        assertThat(userService.getUserOrder(99L)).isEqualTo("order-99");
    }

    @Test
    void customStringBean_通过Bean指定名称_应能正确获取() {
        assertThat(customStringBean).isEqualTo("custom-bean-value");
    }

    @Test
    void externalService_通过Import导入_应被正确创建() {
        assertThat(externalService).isNotNull();
        assertThat(externalService.process()).isEqualTo("external-processed");
    }

    @Test
    void tool_通过FactoryBean创建_应获取getObject返回值() {
        assertThat(tool).isNotNull();
        assertThat(tool.getName()).isEqualTo("factory-created-tool");
    }

    @Test
    void factoryBean_加ampersand前缀_应获取FactoryBean本身() {
        Object factoryBean = applicationContext.getBean("&toolFactoryBean");
        assertThat(factoryBean).isInstanceOf(ToolFactoryBean.class);
    }
}
