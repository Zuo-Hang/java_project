package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * 构造器注入循环依赖 - 验证启动失败
 * <p>
 * 使用独立的最小化配置，仅加载 CircularFailConfig，验证构造器循环依赖无法解决。
 */
class CircularDependencyFailTest {

    @Test
    void 构造器注入循环依赖_启动抛出异常() {
        Throwable thrown = catchThrowable(() ->
                new SpringApplicationBuilder()
                        .sources(CircularFailBootstrap.class)
                        .web(WebApplicationType.NONE)
                        .profiles("circular-fail")
                        .run()
        );

        assertThat(thrown).isNotNull();

        // 异常链中包含循环依赖相关错误
        boolean hasCircularRef = false;
        Throwable t = thrown;
        while (t != null) {
            if (t.getMessage() != null && t.getMessage().contains("circular")) {
                hasCircularRef = true;
                break;
            }
            t = t.getCause();
        }
        assertThat(hasCircularRef).as("应包含循环依赖相关异常").isTrue();
    }

    @org.springframework.boot.autoconfigure.SpringBootApplication
    static class CircularFailBootstrap {
    }
}
