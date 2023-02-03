package com.phorest.techtest.wojciechowski.clientservice.dao;

import com.phorest.techtest.wojciechowski.clientservice.service.model.Appointment;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

public class AppointmentRepository extends EntityRepositoryImpl<Appointment, String> {

    public static final String TABLE = "appointment";
    //columns
    public static final String ID = "id";
    //TODO define more columns

    public AppointmentRepository(JdbcTemplate jdbcTemplate) {
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
    protected Object[] getInsertValues(Appointment customer) {
        return new Object[]{customer.getId()};
        //TODO more propertiies
    }

    @Override
    protected List<ImmutablePair<String, Object>> getColumnUpdates(Appointment customer) {
        return Arrays.asList(
                ImmutablePair.of(ID, customer.getId())
                //TODO more columns and props
        );
    }

    @Override
    protected String getKey(Appointment entity) {
        return entity.getId();
    }

    @Override
    protected RowMapper<Appointment> getRowMapper() {
        return (row, idx) -> {
            Appointment appointment = new Appointment();
            appointment.setId(row.getString(ID));
            //TODO define more column mappings
            return appointment;
        };
    }
}
