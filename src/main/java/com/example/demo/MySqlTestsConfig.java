package com.example.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("mysql-tests")
class MySqlTestsConfig {
    @Bean
    DataSource dataSource() {
        var dsConfig = new HikariConfig();
        dsConfig.setJdbcUrl("jdbc:mysql://localhost:3306/stub-test");
        dsConfig.setUsername("root");
        dsConfig.setPassword("root");
        return new HikariDataSource(dsConfig);
    }

}
