package com.interview.spring.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Bean 创建方式回顾
 * <p>
 * Spring 中常见的 Bean 创建方式：
 * 1. 组件扫描：@Component 及其派生注解（@Service、@Repository、@Controller）
 * 2. 配置类 + @Bean：在 @Configuration 类中通过方法声明 Bean
 * 3. FactoryBean：实现 FactoryBean 接口，自定义 Bean 的创建逻辑
 * 4. @Import：导入其他配置类，将其中定义的 Bean 注册到容器
 */
public class BeanCreationDemo {
}

// ==================== 方式一：组件扫描 ====================
// Spring 自动扫描带 @Component 及其派生注解的类，实例化并注册为 Bean
// 派生注解语义更明确：@Service(业务)、@Repository(持久层)、@Controller(Web 层)

@Component
class ComponentScanBean {
    private final String name = "component-scan-bean";

    public String getName() {
        return name;
    }
}

@Service
class OrderService {
    public String getOrder() {
        return "order-from-service";
    }
}

@Repository
class OrderRepository {
    public String findById(Long id) {
        return "order-" + id;
    }
}

// ==================== 方式二：@Configuration + @Bean ====================
// 适用于：第三方库类、需要复杂初始化逻辑、需要根据条件创建不同实现的场景

@Configuration
@Import(ExternalConfig.class)
class BeanConfig {

    @Bean
    public UserService userService(OrderRepository orderRepository) {
        return new UserServiceImpl(orderRepository);
    }

    @Bean(name = "customBeanName")  // 可指定 Bean 名称，默认方法名
    public String customStringBean() {
        return "custom-bean-value";
    }
}

@Configuration
class ExternalConfig {

    @Bean
    public ExternalService externalService() {
        return new ExternalService();
    }
}

// ==================== 方式三：FactoryBean ====================
// 适用于：Bean 创建逻辑复杂、需要根据配置动态创建、创建过程需要封装
// 容器中实际注册的是 getObject() 返回的对象，而非 FactoryBean 本身
// 若要获取 FactoryBean 实例，Bean 名称前加 "&" 前缀

@Component
class ToolFactoryBean implements FactoryBean<Tool> {

    @Override
    public Tool getObject() {
        Tool tool = new Tool();
        tool.setName("factory-created-tool");
        return tool;
    }

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

// ==================== 示例依赖类 ====================

interface UserService {
    String getUserOrder(Long orderId);
}

class UserServiceImpl implements UserService {
    private final OrderRepository orderRepository;

    UserServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public String getUserOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }
}

class ExternalService {
    public String process() {
        return "external-processed";
    }
}

class Tool {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
