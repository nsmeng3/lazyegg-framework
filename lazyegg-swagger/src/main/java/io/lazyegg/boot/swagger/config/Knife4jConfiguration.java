package io.lazyegg.boot.swagger.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Knife4jConfiguration
 *
 * @author DifferentW  nsmeng3@163.com
 */

@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        //指定使用Swagger2规范
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                //描述字段支持Markdown语法
                .description("# Lazyegg RESTful APIs")
                .termsOfServiceUrl("")
                .contact("")
                .version("1.0")
                .build())
            //分组名称
            .groupName("单体服务")
            .select()
            //这里指定Controller扫描包路径
            .apis(RequestHandlerSelectors.basePackage("io.lazyegg"))
            .paths(PathSelectors.any())
            .build();
        log.info("Swagger2 API文档初始化完成...");
        return docket;
    }
}
