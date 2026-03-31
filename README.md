# Java 面试知识点复习

模块化 Maven 项目，用于系统复习 Java 面试相关知识点。

## 项目结构

```
java_project/
├── pom.xml                 # 父 POM，统一依赖版本与构建配置
├── spring-module/          # Spring 框架知识点模块
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/interview/spring/
│       └── test/java/com/interview/spring/
└── README.md
```

## 技术栈

- **Java**: 21
- **Maven**: 多模块
- **Spring Boot**: 3.2.x

## 模块说明

### spring-module

Spring 框架相关知识点复习。

**Bean 核心知识点**（`com.interview.spring.bean` 包）：

| 文件 | 知识点 |
|------|--------|
| BeanCreationDemo | Bean 创建方式（组件扫描、@Bean、FactoryBean、@Import）|
| BeanScopeDemo | Bean 作用域（singleton、prototype、request、session、application）|
| BeanDependencyInjectionDemo | 依赖注入方式（构造器、字段、Setter、@Resource、ObjectProvider）|
| BeanLazyDemo | 懒加载 @Lazy |
| BeanConditionalDemo | 条件 Bean（@Profile、@ConditionalOnProperty）|
| BeanConfigurationPropertiesDemo | 配置属性 Bean @ConfigurationProperties |
| BeanLifecycleDemo | Bean 生命周期（@PostConstruct、InitializingBean、initMethod 等）|
| CircularDependencyDemo | 循环依赖及解决方案（Setter、@Lazy、ObjectProvider）|

## 快速开始

```bash
# 编译整个项目
mvn clean compile

# 运行 Spring 模块测试
mvn -pl spring-module test

# 打包
mvn clean package
```

## 后续计划

- [ ] 在 spring-module 中按知识点逐步实现示例代码
- [ ] 可扩展更多模块（如：并发、JVM、设计模式等）
# java_project
