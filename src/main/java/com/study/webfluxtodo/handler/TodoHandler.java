package com.study.webfluxtodo.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.webfluxtodo.domain.Todo;
import com.study.webfluxtodo.dto.TodoDto;
import com.study.webfluxtodo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

/**
 * @author Yonghui
 * @since 2019. 10. 24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TodoHandler {
    private final TodoRepository todoRepository;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ok().body(todoRepository.findAll(), Todo.class).log();
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return ok().body(todoRepository.findById(id), Todo.class)
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .log();
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<TodoDto> todoDto = serverRequest.bodyToMono(TodoDto.class).doOnNext(this::validate);
        return todoDto
                .flatMap(_todoDto -> {
                    _todoDto.setCreatedAt(LocalDateTime.now());
                    return ok().body(todoRepository.save(objectMapper.convertValue(_todoDto, Todo.class)), Todo.class);
                })
                .log();
    }

    public Mono<ServerResponse> done(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return todoRepository.findById(id)
                .flatMap(todo -> {
                    todo.setUpdatedAt(LocalDateTime.now());
                    todo.setDone(true);
                    return todoRepository.save(todo);
                })
                .flatMap(todo -> ok().build())
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .log();
    }

    public Mono<ServerResponse> updateContent(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        Mono<TodoDto> todoDto = serverRequest.bodyToMono(TodoDto.class).doOnNext(this::validate);
        return todoDto
                .flatMap(_todoDto -> todoRepository.findById(id).flatMap(todo -> {
                    todo.setUpdatedAt(LocalDateTime.now());
                    todo.setContent(_todoDto.getContent());
                    return todoRepository.save(todo);
                }))
                .flatMap(todo -> ok().body(Mono.just(todo), Todo.class))
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .log();
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return todoRepository.findById(id)
                .flatMap(todoRepository::delete)
                .flatMap(aVoid -> ok().build())
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .log();
    }

    private void validate(Object object) {
        Errors errors = new BeanPropertyBindingResult(object, object.getClass().getName());
        validator.validate(object, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
