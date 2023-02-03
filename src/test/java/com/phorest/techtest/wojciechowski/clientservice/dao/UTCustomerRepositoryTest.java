package com.phorest.techtest.wojciechowski.clientservice.dao;

import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UTCustomerRepositoryTest {
    @Test
    public void shouldCallFindSQL() {
        JdbcTemplate template = mock(JdbcTemplate.class);
        when(template.<Customer>queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(new Customer());

        CustomerRepository repository = new CustomerRepository(template, null);
        repository.findByKey("123");

        String query = "select * from customer where id = ?";
        verify(template).queryForObject(eq(query), argThat(arr -> "123".equals(arr[0])), any(RowMapper.class));
    }

    //TODO another tests testing SQLs for create and update
}
