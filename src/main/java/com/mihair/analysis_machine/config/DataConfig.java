package com.mihair.analysis_machine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    // FIXME : load credentials from env or from secret vault.... I shouldn't need to mention that they must be changed if ever going into production :/
    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(
                "jdbc:postgresql://localhost:5432/medi-sync-patients");
        return dataSource;
    }
}
