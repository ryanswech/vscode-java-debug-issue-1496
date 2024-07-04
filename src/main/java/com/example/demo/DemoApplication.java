package com.example.demo;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@SpringBootApplication
public class DemoApplication {
    private String datasourceUrl;
    private DataSource datasource;

    @Autowired
    public DemoApplication(@Value("${spring.datasource.url}") String datasourceUrl, DataSource datasource)
    {
        this.datasourceUrl = datasourceUrl;
        this.datasource = datasource;
    }

    @Bean
    DSLContext context()
    {
        org.jooq.Configuration configuration = new DefaultConfiguration().set(new TransactionAwareDataSourceProxy(datasource)).set(SQLDialect.MYSQL);

        if (StringUtils.isNotBlank(datasourceUrl))
        {
            String dbName = StringUtils.substringBefore(StringUtils.trimToNull(StringUtils.substringAfterLast(datasourceUrl, "/")), "?");
            if (dbName != null)
            {
                Settings settings = new Settings().withRenderMapping(
                    new RenderMapping().withSchemata(
                        new MappedSchema().withInput("mydb").withOutput(dbName)
                    )
                );
                configuration.set(settings);
            }
        }

        return DSL.using(configuration);
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
