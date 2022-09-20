package com.bridgelabz.bookstoreorderservice.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Purpose :Execute all REST ApIs
 * Version : 3.0
 * @author : Aviligonda Sreenivasulu
 * @Visit Link :  <a href="http://localhost:8080/swagger-ui/index.html">...</a>
 *
 * */
@Configuration

public class SpringFoxConfig implements WebMvcConfigurer {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean

    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {

        return new BeanPostProcessor() {


            @Override

            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {

                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));

                }

                return bean;

            }


            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {

                List<T> copy = mappings.stream()

                        .filter(mapping -> mapping.getPatternParser() == null)

                        .collect(Collectors.toList());

                mappings.clear();

                mappings.addAll(copy);

            }


            @SuppressWarnings("unchecked")

            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {

                try {

                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");

                    field.setAccessible(true);

                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);

                } catch (IllegalArgumentException | IllegalAccessException e) {

                    throw new IllegalStateException(e);

                }

            }

        };

    }

    @Bean

    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(apiInfo())

                .securityContexts(Arrays.asList(securityContext()))

                .securitySchemes(Arrays.asList(apiKey()))

                .select()

                .apis(RequestHandlerSelectors.basePackage("com.bridgelabz.bookstoreorderservice.controller"))

                .paths(PathSelectors.any())

                .build()

                .apiInfo(apiInfo());

    }

    private ApiKey apiKey() {

        return new ApiKey(AUTHORIZATION_HEADER, "JWT", "header");

    }

    private SecurityContext securityContext() {

        return SecurityContext.builder()

                .securityReferences(defaultAuth())

                .build();

    }

    List<SecurityReference> defaultAuth() {

        AuthorizationScope authorizationScope

                = new AuthorizationScope("global", "accessEverything");

        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;

        return Arrays.asList(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));

    }

    private ApiInfo apiInfo() {

        return new ApiInfo("BookStore Order Service",

                "APIs for E.",

                "1.0",

                "Terms of service",

                new Contact("BookStore Order Service", "https://bridgelabz.com&quot;", System.getenv("Email")),

                "Apache 2.0",

                "https://www.apache.org/licenses/LICENSE-2.0&quot;",

                Collections.emptyList());

    }


}