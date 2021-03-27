package com.enigma.creditscoringapi.services;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class AbstractService <T, ID> implements ServiceInterface<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public AbstractService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T removeById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            repository.deleteById(id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(T search, int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return repository.findAll(Example.of(search, matcher), PageRequest.of(page, size, sort));
    }
}
