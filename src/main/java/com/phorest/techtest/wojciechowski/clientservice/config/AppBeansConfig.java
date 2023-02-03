package com.phorest.techtest.wojciechowski.clientservice.config;

import com.phorest.techtest.wojciechowski.clientservice.dao.AppointmentRepository;
import com.phorest.techtest.wojciechowski.clientservice.dao.CustomerRepository;
import com.phorest.techtest.wojciechowski.clientservice.dao.EntityRepository;
import com.phorest.techtest.wojciechowski.clientservice.service.EntityService;
import com.phorest.techtest.wojciechowski.clientservice.service.ImportingService;
import com.phorest.techtest.wojciechowski.clientservice.service.mapping.csv.CSVMappingHelper;
import com.phorest.techtest.wojciechowski.clientservice.service.mapping.csv.CsvMapper;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Appointment;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Customer;
import com.phorest.techtest.wojciechowski.clientservice.service.model.Service;
import com.phorest.techtest.wojciechowski.clientservice.service.parsing.EntityCSVParser;
import com.phorest.techtest.wojciechowski.clientservice.web.Controller;
import com.phorest.techtest.wojciechowski.clientservice.web.CustomerController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class AppBeansConfig {

    @Bean
    public EntityCSVParser csvParser() {
        return new EntityCSVParser(",");
    }

    @Bean
    public CsvMapper<Customer> clientCsvMapper() {
        return new CsvMapper<>(Customer::new, CSVMappingHelper.csvClientMapping());
    }

    @Bean
    public CsvMapper<Appointment> appointmentCsvMapper() {
        return new CsvMapper<>(Appointment::new, CSVMappingHelper.csvClientAppointment());
    }

    @Bean
    public CsvMapper<Service> serviceCsvMapper() {
        return new CsvMapper<>(Service::new, CSVMappingHelper.csvClientService());
    }

    @Bean
    //@Profile("dev")
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                //.addScript("classpath:jdbc/test-data.sql")
                .build();
    }

//    @Bean
//    @Profile("prod")
//    public DataSource dataSource(@Value("${jdbc.driver}") String driver,
//                                 @Value("${jdbc.url}") String url,
//                                 @Value("${jdbc.user}") String user,
//                                 @Value("${jdbc.password}") String password) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl(url);
//        dataSource.setUsername("guest_user");
//        dataSource.setPassword("guest_password");
//        return dataSource;
//    }

//    @Bean
//    JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

    @Bean
    public CustomerRepository customerRepository(JdbcTemplate jdbcTemplate, EntityRepository<Appointment, String> appointmentRepository) {
        return new CustomerRepository(jdbcTemplate, appointmentRepository);
    }

    @Bean
    public AppointmentRepository appointmentRepository(JdbcTemplate jdbcTemplate) {
        return new AppointmentRepository(jdbcTemplate);
    }

    @Bean
    public ImportingService importingService(EntityCSVParser parser,
                                             CsvMapper<Customer> clientCsvMapper,
                                             CsvMapper<Appointment> appointmentCsvMapper,
                                             CsvMapper<Service> serviceCsvMapper,
                                             EntityRepository<Customer, String> customerEntityRepository) {
        return new ImportingService(parser, clientCsvMapper, appointmentCsvMapper, serviceCsvMapper, customerEntityRepository);
    }

    @Bean
    public EntityService<Customer, String> clientService(EntityRepository<Customer, String> customerRepository) {
        return new EntityService<>(customerRepository, entity -> {});
    }

    @Bean
    public Controller controller(ImportingService service) {
        return new Controller(service);
    }

    @Bean
    public CustomerController customerController(EntityService<Customer, String> service) {
        return new CustomerController(service);
    }

}
