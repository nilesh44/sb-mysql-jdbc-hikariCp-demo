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


//for configuring hikariDataSource  we have to create bean of HikariDataSource	
//syntex @Bean(name="name of bean")
	@Bean(name = "HikariDataSourceBean")
//here we say to spring container that create this bean only when depondsOn bean is created
//i.e do not create "HikariDataSourceBean" bean until and unless "HikariConfig" bean is created  
// there is a condition where on bean is depends on multiple bean 
	@DependsOn(value = {"HikariConfig"})
	@Scope("singleton")
	public HikariDataSource getHikariDataSource() {
		return new HikariDataSource(getHikariConfig());
		
	}
	

//we also need to create bean of HikariConfig  which contain all the configuration related to our Data base
//we also configure the pool size 	
	
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
