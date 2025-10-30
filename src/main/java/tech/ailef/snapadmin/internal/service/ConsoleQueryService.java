/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.internal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import tech.ailef.snapadmin.internal.model.ConsoleQuery;
import tech.ailef.snapadmin.internal.repository.ConsoleQueryRepository;

@Service
public class ConsoleQueryService {
	@Autowired
	private TransactionTemplate internalTransactionTemplate;
	
	@Autowired
	private ConsoleQueryRepository repo;
	
	public ConsoleQuery save(ConsoleQuery q) {
		return internalTransactionTemplate.execute((status) -> {
			return repo.save(q);
		});
	}
	
	public void delete(String id) {
		internalTransactionTemplate.executeWithoutResult((status) -> {
			repo.deleteById(id);
		});
	}
	
	public List<ConsoleQuery> findAll() {
		return repo.findAll();
	}

	public Optional<ConsoleQuery> findById(String id) {
		return repo.findById(id);
	}
}
