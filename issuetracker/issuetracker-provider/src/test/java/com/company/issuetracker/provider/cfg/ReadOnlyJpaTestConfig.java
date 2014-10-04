package com.company.issuetracker.provider.cfg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@PropertySource({ "classpath:testdatabase.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.company.issuetracker.provider")
public class ReadOnlyJpaTestConfig {

	private static final String DATABASE_DRIVER = "jdbc.driverClassName";
	private static final String DATABASE_PASSWORD = "jdbc.password";
	private static final String DATABASE_URL = "jdbc.url";
	private static final String DATABASE_USERNAME = "jdbc.username";
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_DDL = "hibernate.hbm2ddl.auto";
	private static final String PERSISTENCE_UNIT_NAME="IssueTrackerRESTProvider";

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));

		populateDatabase(dataSource.getConnection());	

		return dataSource;
	}

	private void populateDatabase(Connection connection){
		ResourceDatabasePopulator r=new ResourceDatabasePopulator();
		r.addScript(new ClassPathResource("testData.sql"));		
		r.populate(connection);
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws SQLException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.company.issuetracker.provider.domain" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties properties=new Properties();
		properties.setProperty("hibernate.dialect", env.getProperty(HIBERNATE_DIALECT));
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty(HIBERNATE_DDL));
		properties.setProperty("hibernate.id.new_generator_mappings", "true");

		return properties;
	}

	/*
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		txManager.setDataSource(dataSource());

		return txManager;
	}
	 */

	@Bean
	public JpaTransactionManager transactionManager() throws SQLException {

		JpaTransactionManager t=new JpaTransactionManager();
		t.setEntityManagerFactory(entityManagerFactory().getObject());

		return t;

	}
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter=new HibernateJpaVendorAdapter();		
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		hibernateJpaVendorAdapter.setDatabasePlatform(env.getProperty(HIBERNATE_DIALECT));
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setShowSql(true);		

		return hibernateJpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException{
		LocalContainerEntityManagerFactoryBean emf=new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setJpaDialect(hibernateJpaDialect());
		emf.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		emf.setPackagesToScan("com.company.issuetracker.provider.domain");
		emf.setJpaVendorAdapter(jpaVendorAdapter());
		emf.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

		return emf;
	}

	@Bean 
	public JpaDialect hibernateJpaDialect(){
		return new HibernateJpaDialect();		
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
}