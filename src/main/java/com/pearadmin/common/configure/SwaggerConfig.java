package com.pearadmin.common.configure;

import com.pearadmin.common.configure.proprety.SwaggerProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Swagger 配置文件
 * <p>
 * @serial 2.0.0
 * @author 就眠儀式
 */
@Slf4j
@Configuration
@EnableOpenApi
@EnableConfigurationProperties(SwaggerProperty.class)
public class SwaggerConfig {

    @Resource
    private SwaggerProperty documentAutoProperties;

    @Bean
    public Docket docker() {
        log.info("Read document configuration information");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(documentAutoProperties.getGroupName())
                .enable(documentAutoProperties.getEnable())
                .select()
                .apis(RequestHandlerSelectors.basePackage(documentAutoProperties.getScanPackage()))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                documentAutoProperties.getTitle(),
                documentAutoProperties.getDescribe(),
                documentAutoProperties.getVersion(),
                documentAutoProperties.getTermsOfServiceUrl(), documentAutoProperties.getContact(), documentAutoProperties.getLicence(), documentAutoProperties.getLicenceUrl(),
                new ArrayList<>()
        );
    }
}