/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.internal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import dev.semeshin.snapadmin.external.dto.LogsSearchRequest;
import dev.semeshin.snapadmin.external.dto.PaginatedResult;
import dev.semeshin.snapadmin.external.dto.PaginationInfo;
import dev.semeshin.snapadmin.internal.model.UserAction;
import dev.semeshin.snapadmin.internal.repository.CustomActionRepositoryImpl;
import dev.semeshin.snapadmin.internal.repository.UserActionRepository;

/**
 * Service class to retrieve user actions through the {@link CustomActionRepositoryImpl}. 
 *
 */
@Service
public class UserActionService {
	@Autowired
	private UserActionRepository repo;
	
	@Autowired
	private CustomActionRepositoryImpl customRepo;
	
	@Autowired
	private TransactionTemplate internalTransactionTemplate;
	
	public UserAction save(UserAction a) {
		return internalTransactionTemplate.execute(status -> {
			return repo.save(a);
		});
	}
	
	/**
	 * Retruns a page of results of user actions that match the given input request.
	 * @param request a request containing filtering parameters for user actions
	 * @return a page of results matching the input request
	 */
	public PaginatedResult<UserAction> findActions(LogsSearchRequest request) {
		PageRequest page = request.toPageRequest();
		
		long count = customRepo.countActions(request);
		List<UserAction> actions = customRepo.findActions(request);
		int maxPage = (int)(Math.ceil ((double)count / page.getPageSize()));
		
		return new PaginatedResult<>(
			new PaginationInfo(page.getPageNumber() + 1, maxPage, page.getPageSize(), count, null, request),
			actions
		);
	}
	
}
