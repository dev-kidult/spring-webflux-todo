package com.study.webfluxtodo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author Yonghui
 * @since 2019. 10. 24
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
}
