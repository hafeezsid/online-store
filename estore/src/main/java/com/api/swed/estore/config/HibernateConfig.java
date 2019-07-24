package com.api.swed.estore.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages={"com.api.swed.estore"})
@EnableTransactionManagement
public class HibernateConfig {

	// Change the below based on the DBMS you choose
	private final static String DATABASE_URL = "jdbc:sqlite:memory:myDb?cache=shared";
	private final static String DATABASE_DRIVER = "org.sqlite.JDBC";
	private final static String DATABASE_DIALECT = "com.api.swed.estore.dialect.SQLiteDialect";
	private final static String DATABASE_USERNAME = "sa";
	private final static String DATABASE_PASSWORD = "sa";
	
	@Autowired 
	private Environment env;
	
	// dataSource bean will be available
	@Bean("dataSource")
	public DataSource getDataSource() {
		
		BasicDataSource dataSource = new BasicDataSource();
		
		// Providing the database connection information
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setUsername(env.getProperty("user"));
		dataSource.setPassword(env.getProperty("password"));
				
		
		return dataSource;
		
	}
	
	// sessionFactory bean will be available
	
	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {
		
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		
		builder.addProperties(getHibernateProperties());
		builder.scanPackages("com.api.swed.estore.models");
		
		return builder.buildSessionFactory();
		
	}

	
	
	// All the hibernate properties will be returned in this method	
	private Properties getHibernateProperties() {
		
		Properties properties = new Properties();
		
		
		/*
		 * properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		 * properties.put("hibernate.show_sql", "true");
		 * properties.put("hibernate.format_sql", "true");
		 */
		
		
		if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
			properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        }
        if (env.getProperty("hibernate.dialect") != null) {
        	properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        }
        if (env.getProperty("hibernate.show_sql") != null) {
        	properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        }
	
		
		return properties;
	}
	
	// transactionManager bean
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		return transactionManager;
	}
	
	@Configuration
	@Profile("sqlite")
	@PropertySource("classpath:persistence-sqlite.properties")
	class SqliteConfig {
	}
	
	

}
