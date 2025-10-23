/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.internal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.semeshin.snapadmin.internal.model.UserSetting;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSetting, String> {
	
}
