package com.ace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//this annotation will scan all the @Bean  and create a spring bean for that in spring context
@Configuration
public class ApplicationBean {

	//here we can set the order of creation of bean
	@Order(value = 1)
	//here we indicate to spring context create spring bean with bean name "DbProperties"
	@Bean("DbProperties")
	//here we can define scope of bean
	@Scope("singleton")
	
	public DbProperties getDbProperties() {
		return new DbProperties();
	}

	//here we can set the order of creation of bean
	@Order(value = 2)
	@Bean("DatabaseConfiguation")
	@Scope("singleton")
	//this annotation describe that create this bean only after depends on bean is created
	@DependsOn("DbProperties")
	public DatabaseConfiguation getDatabaseConfiguation() {
		return new DatabaseConfiguation(getDbProperties().getDriverClass(), getDbProperties().getUrl(),
				getDbProperties().getUser(), getDbProperties().getPassword());
	}
	
	@Bean("HikariDataSource")
	@DependsOn("HikariConfig")
	@Scope("singleton")
	public HikariDataSource getHikariDataSource() {
		return new HikariDataSource(getHikariConfig());
		
	}
	
	@Bean("HikariConfig")
	@DependsOn("DbProperties")
	@Scope("singleton")
	public HikariConfig getHikariConfig() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(getDbProperties().getDriverClass());
		config.setJdbcUrl(getDbProperties().getUrl());
        config.setUsername(getDbProperties().getUser());
        config.setPassword(getDbProperties().getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
       
		return config;
	}
}
