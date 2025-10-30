/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.internal.repository;

import java.util.List;

import tech.ailef.snapadmin.external.dto.LogsSearchRequest;
import tech.ailef.snapadmin.internal.model.UserAction;

public interface CustomActionRepository {
	public List<UserAction> findActions(LogsSearchRequest r);
	
	public long countActions(LogsSearchRequest request);

}
