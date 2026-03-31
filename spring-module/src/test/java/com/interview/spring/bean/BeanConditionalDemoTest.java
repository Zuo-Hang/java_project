package com.interview.spring.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 条件 Bean - 单元测试
 */
@SpringBootTest
@ActiveProfiles("dev")
class BeanConditionalDemoTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void devProfile_应加载DevDataSource() {
        DevDataSource ds = applicationContext.getBean(DevDataSource.class);
        assertThat(ds.getUrl()).isEqualTo("jdbc:h2:mem:dev");
    }

    @Test
    void devProfile_应加载DevTestFeature() {
        DevTestFeature feature = applicationContext.getBean(DevTestFeature.class);
        assertThat(feature.getFeature()).isEqualTo("dev-test-only");
    }

    @Test
    void devProfile_不应加载ProdDataSource() {
        assertThat(applicationContext.getBeansOfType(ProdDataSource.class)).isEmpty();
    }

    @Test
    void conditionalOnProperty_缓存开启_应加载CacheService() {
        CacheService cache = applicationContext.getBean(CacheService.class);
        assertThat(cache.get("key")).isEqualTo("cached-key");
    }

    @Test
    void conditionalOnProperty_审计开启_应加载AuditService() {
        AuditService audit = applicationContext.getBean(AuditService.class);
        assertThat(audit).isNotNull();
    }
}
