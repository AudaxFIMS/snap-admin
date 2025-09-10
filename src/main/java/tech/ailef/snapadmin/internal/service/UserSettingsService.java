/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import tech.ailef.snapadmin.internal.model.UserSetting;
import tech.ailef.snapadmin.internal.repository.UserSettingsRepository;

@Service
public class UserSettingsService {
	@Autowired
	private TransactionTemplate internalTransactionTemplate;
	
	@Autowired
	private UserSettingsRepository repo;
	
	public UserSetting save(UserSetting q) {
		return internalTransactionTemplate.execute((status) -> {
			return repo.save(q);
		});
	
	}
}
