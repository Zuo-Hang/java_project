package com.interview.spring.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Bean 懒加载（@Lazy）回顾
 * <p>
 * 默认单例 Bean 在容器启动时创建，@Lazy 使 Bean 延迟到首次使用时才创建。
 * 适用场景：启动慢的重型 Bean、不一定会用到的 Bean。
 */
public class BeanLazyDemo {
}

// ==================== 类级别 @Lazy ====================
// 该 Bean 首次被注入或 getBean 时才创建

@Component
@Lazy
class LazyHeavyService {
    public LazyHeavyService() {
        // 模拟初始化耗时
    }

    public String process() {
        return "lazy-initialized";
    }
}

// ==================== 注入点 @Lazy ====================
// 注入的是代理，首次调用方法时才初始化真实 Bean

@Component
class LazyConsumer {
    private final LazyHeavyService lazyHeavyService;

    public LazyConsumer(@Lazy LazyHeavyService lazyHeavyService) {
        this.lazyHeavyService = lazyHeavyService;
    }

    public String use() {
        return lazyHeavyService.process();
    }
}

// ==================== @Bean + @Lazy ====================

@Configuration
class LazyBeanConfig {

    @Bean
    @Lazy
    public LazyConfigBean lazyConfigBean() {
        return new LazyConfigBean("created-on-first-use");
    }
}

class LazyConfigBean {
    private final String value;

    LazyConfigBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
