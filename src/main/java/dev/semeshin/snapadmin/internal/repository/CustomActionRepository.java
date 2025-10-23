/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.internal.repository;

import java.util.List;

import dev.semeshin.snapadmin.external.dto.LogsSearchRequest;
import dev.semeshin.snapadmin.internal.model.UserAction;

public interface CustomActionRepository {
	public List<UserAction> findActions(LogsSearchRequest r);
	
	public long countActions(LogsSearchRequest request);

}
