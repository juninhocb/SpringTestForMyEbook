package com.example.demo;

import org.hibernate.dialect.MySQLDialect;

public class MySqlH2Dialect extends MySQLDialect {
    @Override
    public String getTableTypeString() {
        return ""; // avoid ENGINE=InnoDB
    }
}
