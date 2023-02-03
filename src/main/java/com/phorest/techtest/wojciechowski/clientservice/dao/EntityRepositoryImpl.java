package com.phorest.techtest.wojciechowski.clientservice.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class EntityRepositoryImpl<T, K> implements EntityRepository<T, K> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public T findByKey(K key) {
        String query = "select * from "+ getTableName() +" where " + getKeyColumn() + " = ?";
        return jdbcTemplate.queryForObject(query, new Object[] {key}, getRowMapper());
    }

    @Override
    public T find(T entity) {
        K key = getKey(entity);
        return findByKey(key);
    }

    @Override
    @Transactional
    public void save(T entity) {
        K key = getKey(entity);
        Object[] values = getInsertValues(entity);
        String insertValues = Arrays.stream(values).map(val -> "?").reduce("", (s1, s2) -> s1 + s2);
        String query = "insert into " + getTableName() + "(" + getInsertColumns() + ")" + " values (" + insertValues + ")";
        jdbcTemplate.update(query, values);
    }

    @Override
    @Transactional
    public void save(Collection<T> enitities) {
        enitities.forEach(this::save);
    }

    @Override
    @Transactional
    public void update(T entity) {
        K key = getKey(entity);
        Collection<ImmutablePair<String, Object>> values = getColumnUpdates(entity);
        String updateValuesStr = values.stream().map(pair -> pair.getLeft() + " = ?, ").reduce("", (s1, s2) -> s1 + s2);
        List updateValues = values.stream().map(ImmutablePair::getRight).collect(Collectors.toList());
        String query = "update " + getTableName() + " set " + updateValuesStr + " where " + getKeyColumn() + " = ?";
        List paramList = new ArrayList(updateValues);
        paramList.add(key);

        jdbcTemplate.update(query, paramList.toArray());
    }

    protected abstract String getTableName();

    protected abstract String getKeyColumn();

    protected abstract String getInsertColumns();

    protected abstract Object[] getInsertValues(T entity);

    protected abstract Collection<ImmutablePair<String, Object>> getColumnUpdates(T entity);

    protected abstract K getKey(T entity);

    protected abstract RowMapper<T> getRowMapper();
}
