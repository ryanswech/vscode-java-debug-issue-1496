package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatasourceConfig
{
    @Value("${spring.datasource.url:}")
    String datasourceUrl;

    @Bean
    DataSource createJDBCDatasource()
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(datasourceUrl);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(3);

        return new HikariDataSource(config);
    }
}
