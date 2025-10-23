/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.internal;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the "internal" data source. This is place in the root "internal"
 * package, so as to allow component scanning and detection of models and repositories.
 */
@ConditionalOnProperty(name = "snapadmin.enabled", matchIfMissing = true)
@ComponentScan
@Configuration
public class InternalSnapAdminConfiguration {

}
