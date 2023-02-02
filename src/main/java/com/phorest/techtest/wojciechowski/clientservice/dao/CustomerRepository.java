package com.phorest.techtest.wojciechowski.clientservice.dao;


import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;


public class CustomerRepository extends EntityRepositoryImpl<Customer, String> {

    public static final String TABLE = "customer";
    //columns
    public static final String ID = "id";
    //TODO define more columns

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getTableName() {
        return TABLE;
    }

    @Override
    protected String getKeyColumn() {
        return ID;
    }

    @Override
    protected String getInsertColumns() {
        return ID;
        //TODO define more columns
    }

    @Override
    protected Object[] getInsertValues(Customer customer) {
        return new Object[] {customer.getId()};
        //TODO more propertiies
    }

    @Override
    protected List<ImmutablePair<String, Object>> getColumnUpdates(Customer customer) {
        return Arrays.asList(
                ImmutablePair.of(ID, customer.getId())
                //TODO more columns and props
        );
    }

    @Override
    protected String getKey(Customer entity) {
        return entity.getId();
    }

    @Override
    protected RowMapper<Customer> getRowMapper() {
        return (row, idx) -> {
            Customer customer = new Customer();
            customer.setId(row.getString(ID));
            //TODO define more column mappings
            return customer;
        };
    }
}
