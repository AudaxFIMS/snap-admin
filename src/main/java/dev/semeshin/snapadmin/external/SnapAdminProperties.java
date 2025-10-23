/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The 'snapadmin.*' properties that can be set in the properties file
 * to configure the behaviour of Spring Boot Admin Panel. 
 */
@ConfigurationProperties("snapadmin")
public class SnapAdminProperties {
	/**
	 * Whether SnapAdmin is enabled.
	 */
	public boolean enabled = false;
	
	/**
	 * The prefix that is prepended to all routes registered by SnapAdmin.
	 */
	private String baseUrl;

	/**
	 * The path of the package that contains your JPA `@Entity` classes to be scanned.
	 */
	private String modelsPackage;

	/**
	 * Set to true when running the tests to configure the "internal" data source as in memory
	 */
	private boolean testMode = false;
	
	/**
	 * Whether the SQL console feature is enabled
	 */
	private boolean sqlConsoleEnabled = true;
	
	/**
	 * Whether SnapAdmin is enabled
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isSqlConsoleEnabled() {
		return sqlConsoleEnabled;
	}
	
	public void setSqlConsoleEnabled(boolean sqlConsoleEnabled) {
		this.sqlConsoleEnabled = sqlConsoleEnabled;
	}

	/**
	 * Returns the prefix that is prepended to all routes registered by SnapAdmin.
	 * @return
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * Returns the path of the package that contains your JPA `@Entity` classes to be scanned.
	 * @return
	 */
	public String getModelsPackage() {
		return modelsPackage;
	}
	
	public void setModelsPackage(String modelsPackage) {
		this.modelsPackage = modelsPackage;
	}
	
	public boolean isTestMode() {
		return testMode;
	}
	
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
	
//	public Map<String, String> toMap() {
//		Map<String, String> conf = new HashMap<>();
//		conf.put("enabled", enabled + "");
//		conf.put("baseUrl", baseUrl);
//		conf.put("modelsPackage", modelsPackage);
//		conf.put("testMode", testMode + "");
//		conf.put("sqlConsoleEnabled", sqlConsoleEnabled + "");
//		return conf;
//	}
	
	
}
