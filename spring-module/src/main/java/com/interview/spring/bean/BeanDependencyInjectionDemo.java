package com.interview.spring.bean;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Bean 依赖注入方式回顾
 * <p>
 * Spring 支持的依赖注入方式：
 * 1. 构造器注入：推荐，依赖不可变、易测试
 * 2. 字段注入：@Autowired 加在字段上，简单但不推荐
 * 3. Setter 注入：@Autowired 加在 setter 上，适用于可选依赖
 * 4. @Resource：JSR-250，默认按名称注入
 * 5. ObjectProvider：延迟获取，适合原型 Bean 每次获取新实例
 */
public class BeanDependencyInjectionDemo {
}

// ==================== 1. 构造器注入 ====================

@Service
class ConstructorInjectionService {
    private final OrderRepository orderRepository;

    public ConstructorInjectionService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String getOrder(Long id) {
        return orderRepository.findById(id);
    }
}

// ==================== 2. 字段注入 ====================

@Service
class FieldInjectionService {
    @Autowired
    private OrderRepository orderRepository;

    public String getOrder(Long id) {
        return orderRepository.findById(id);
    }
}

// ==================== 3. Setter 注入 ====================

@Service
class SetterInjectionService {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String getOrder(Long id) {
        return orderRepository.findById(id);
    }
}

// ==================== 4. @Resource（按名称注入）====================

@Service
class ResourceInjectionService {
    @Resource(name = "orderRepository")
    private OrderRepository repository;

    public String getOrder(Long id) {
        return repository.findById(id);
    }
}

// ==================== 5. ObjectProvider（延迟获取原型）====================

@Service
class ObjectProviderService {
    private final ObjectProvider<PrototypeScopeBean> prototypeProvider;

    public ObjectProviderService(ObjectProvider<PrototypeScopeBean> prototypeProvider) {
        this.prototypeProvider = prototypeProvider;
    }

    public int getFreshCount() {
        return prototypeProvider.getObject().increment();
    }
}
