/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.internal.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.ailef.snapadmin.internal.model.UserAction;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Integer>, CustomActionRepository {
	public List<UserAction> findAllByOnTableAndActionTypeAndPrimaryKey(String table, String actionType, String primaryKey, PageRequest pageRequest);
	
}
