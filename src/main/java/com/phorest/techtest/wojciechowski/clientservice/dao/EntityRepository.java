package com.phorest.techtest.wojciechowski.clientservice.dao;

import java.util.Collection;

public interface EntityRepository<T, K> {

    T findByKey(K id);

    T find(T id);

    void save(T enitty);

    void save(Collection<T> enitty);

    void update(T entity);
}
