package com.phorest.techtest.wojciechowski.clientservice.service;

import com.phorest.techtest.wojciechowski.clientservice.dao.EntityRepository;
import com.phorest.techtest.wojciechowski.clientservice.service.exception.ValidationError;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class EntityService<T, K> {
    private final EntityRepository<T, K> repository;

    private final EntityValidator<T> validator;

    public T findEntity(K id) {
        return repository.findByKey(id);
    }

    public T createEntity(T entity) throws ValidationError {

        validator.validate(entity);
        repository.save(entity);
        return repository.find(entity);
    }

    public void updateEntity(T entity) throws ValidationError {

        validator.validate(entity);
        entity = repository.find(entity);
        if (entity == null) {
            throw new ValidationError("Cannot update not existing entity");
        }
        repository.save(entity);
    }

    public void saveEntities(Collection<T> entites) {
        repository.save(entites);
    }

    public void deleteEntity(K key) throws ValidationError {

        T entity = repository.findByKey(key);
        if (entity == null) {
            throw new ValidationError("Cannot delete not existing entity");
        }
        repository.save(entity);
    }
}
