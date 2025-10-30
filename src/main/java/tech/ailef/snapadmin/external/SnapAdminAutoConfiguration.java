/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.external;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import tech.ailef.snapadmin.internal.InternalSnapAdminConfiguration;

/**
 * The configuration class for "internal" data source. This is not the
 * source connected to the user's data/entities, but rather an internal
 * H2 database which is used by SnapAdmin to store user
 * settings and other information like operations history. 
 */
@ConditionalOnProperty(name = "snapadmin.enabled")
@ComponentScan
@EnableConfigurationProperties(SnapAdminProperties.class)
@Configuration
@EnableJpaRepositories(
	entityManagerFactoryRef = "internalEntityManagerFactory", 
	basePackages = { "tech.ailef.snapadmin.internal.repository" }
)
@EnableTransactionManagement
@Import(InternalSnapAdminConfiguration.class)
@SuppressWarnings("unchecked")
public class SnapAdminAutoConfiguration {
	private final SnapAdminProperties props;
	private final ConfigurableEnvironment env;

	private Map<String, Object> datasourceProp;
	private Map<String, Object> jpaProp;

	public SnapAdminAutoConfiguration(SnapAdminProperties props, ConfigurableEnvironment env) {
		this.props = props;
		this.env = env;

		if (props.isEnabledAppInternalDs()) {
			Map<String, Object> appInternalDsProperties = getPropertiesWithPrefix(props.getAppInternalDsSettingsPrefix());

			this.datasourceProp = (Map<String, Object>) appInternalDsProperties.get("datasource");
			this.jpaProp = (Map<String, Object>) appInternalDsProperties.get("jpa");
		}
	}

	/**
	 * Builds and returns the internal data source.
	 * 
	 * @return internalDataSource
	 */
	@Bean
	DataSource internalDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

		if (props.isEnabledAppInternalDs()) {
			dataSourceBuilder
				.url(datasourceProp.getOrDefault("jdbc-url", "").toString())
				.driverClassName(datasourceProp.getOrDefault("driver-class-name", "").toString())
				.username(datasourceProp.getOrDefault("username", "").toString())
				.password(datasourceProp.getOrDefault("password", "").toString());
		} else {
			dataSourceBuilder.driverClassName("org.h2.Driver");
			if (props.isTestMode()) {
				dataSourceBuilder.url("jdbc:h2:mem:test");
			} else {
				dataSourceBuilder.url("jdbc:h2:file:./snapadmin_internal");
			}

			dataSourceBuilder.username("sa");
			dataSourceBuilder.password("password");
		}

		return dataSourceBuilder.build();
	}

	private Map<String, Object> getPropertiesWithPrefix(String prefix) {
		Binder binder = Binder.get(env);
		return binder
				.bind(prefix, Bindable.mapOf(String.class, Object.class))
				.orElse(Collections.emptyMap());
	}

	@Bean
	LocalContainerEntityManagerFactoryBean internalEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		factoryBean.setDataSource(internalDataSource());
		factoryBean.setPersistenceUnitName("internal");
		factoryBean.setPackagesToScan("tech.ailef.snapadmin.internal.model");
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties properties = new Properties();

		if (props.isEnabledAppInternalDs()) {
			properties.setProperty("hibernate.dialect",
					jpaProp.getOrDefault("database-platform", "").toString());

			Map<String, Object> hibernateProp = (Map<String, Object>) jpaProp
					.getOrDefault("hibernate", Collections.emptyMap());

			if (hibernateProp.containsKey("ddl-auto")) {
				properties
					.setProperty("hibernate.hbm2ddl.auto", hibernateProp.getOrDefault("ddl-auto", "none")
							.toString()
					);
			} else {
				properties.setProperty("hibernate.hbm2ddl.auto", "none");
			}
		} else {
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			properties.setProperty("hibernate.hbm2ddl.auto", "update");
		}

		factoryBean.setJpaProperties(properties);
		factoryBean.afterPropertiesSet();

		return factoryBean;
	}

	/**
	 * The internal transaction manager. It is not defined as a bean
	 * in order to avoid "colliding" with the default transactionManager
	 * registered by the user. Internally, we use this to instantiate a
	 * TransactionTemplate and run all transactions manually instead of
	 * relying on the @link {@link Transactional} annotation.
	 * @return internalTransactionManager
	 */
	PlatformTransactionManager internalTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(internalEntityManagerFactory().getObject());
		return transactionManager;
	}
	
	@Bean
	TransactionTemplate internalTransactionTemplate() {
	    return new TransactionTemplate(internalTransactionManager());
	}

}