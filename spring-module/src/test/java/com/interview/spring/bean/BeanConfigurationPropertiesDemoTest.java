package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 配置属性 Bean - 单元测试
 */
@SpringBootTest
class BeanConfigurationPropertiesDemoTest {

    @Autowired
    private AppDemoProperties appDemoProperties;

    @Autowired
    private CustomServiceProperties customServiceProperties;

    @Test
    void appDemoProperties_应从配置文件绑定() {
        assertThat(appDemoProperties.getName()).isEqualTo("demo-app");
        assertThat(appDemoProperties.getTimeout()).isEqualTo(60);
        assertThat(appDemoProperties.getNested().isEnabled()).isTrue();
        assertThat(appDemoProperties.getNested().getPath()).isEqualTo("/v1/api");
    }

    @Test
    void customServiceProperties_应从配置文件绑定() {
        assertThat(customServiceProperties.getEndpoint()).isEqualTo("https://api.example.com");
        assertThat(customServiceProperties.getRetries()).isEqualTo(5);
    }
}
