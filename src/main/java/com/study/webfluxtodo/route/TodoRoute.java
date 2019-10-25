package com.study.webfluxtodo.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.study.webfluxtodo.handler.TodoHandler;

import lombok.RequiredArgsConstructor;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Yonghui
 * @since 2019. 10. 24
 */
@Configuration
@RequiredArgsConstructor
public class TodoRoute {
    private final TodoHandler todoHandler;

    @Bean
    RouterFunction<ServerResponse> todoRouteFunction() {
        return route()
                .path("/api", builder -> builder
                        .GET("/todos", todoHandler::getAll)
                        .GET("/todos/{id}", todoHandler::getById)
                        .POST("/todos", todoHandler::save)
                        .PUT("/todos/done/{id}", todoHandler::done)
                        .PUT("/todos/content", todoHandler::updateContent)
                        .DELETE("/todos/{id}", todoHandler::delete))
                .build();
    }
}
