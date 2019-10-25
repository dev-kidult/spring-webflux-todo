package com.study.webfluxtodo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.study.webfluxtodo.domain.Todo;

/**
 * @author Yonghui
 * @since 2019. 10. 24
 */
@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {
}
