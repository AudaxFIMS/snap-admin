/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dev.semeshin.snapadmin.external.SnapAdmin;
import dev.semeshin.snapadmin.external.SnapAdminProperties;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminNotFoundException;
import dev.semeshin.snapadmin.internal.UserConfiguration;

/**
 * This class registers some global ModelAttributes and exception handlers.
 * 
 */
@ControllerAdvice
public class GlobalController {

	@Autowired
	private SnapAdminProperties props;

	@Autowired
	private UserConfiguration userConf;
	
	@Autowired
	private SnapAdmin snapAdmin;
	
	@ExceptionHandler(SnapAdminException.class)
	public String handleException(Exception e, Model model, HttpServletResponse response) {
		model.addAttribute("status", "");
		model.addAttribute("error", "Error");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("snapadmin_userConf", userConf);
		model.addAttribute("snapadmin_baseUrl", getBaseUrl());
		model.addAttribute("snapadmin_version", snapAdmin.getVersion());
		model.addAttribute("snapadmin_properties", props);
		return "snapadmin/other/error";
	}
	
	@ExceptionHandler(SnapAdminNotFoundException.class)
	public String handleNotFound(Exception e, Model model, HttpServletResponse response) {
		model.addAttribute("status", "404");
		model.addAttribute("error", "Error");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("snapadmin_userConf", userConf);
		model.addAttribute("snapadmin_baseUrl", getBaseUrl());
		model.addAttribute("snapadmin_version", snapAdmin.getVersion());
		model.addAttribute("snapadmin_properties", props);
		response.setStatus(404);
		return "snapadmin/other/error";
	}
	
	@ModelAttribute("snapadmin_version")
	public String getVersion() {
		return snapAdmin.getVersion();
	}
	
	/**
	 * A multi valued map containing the query parameters. It is used primarily
	 * in building complex URL when performing faceted search with multiple filters.
	 * @param request the incoming request
	 * @return multi valued map of request parameters
	 */
	@ModelAttribute("snapadmin_queryParams")
	public Map<String, String[]> getQueryParams(HttpServletRequest request) {
		return request.getParameterMap();
	}
	
	/**
	 * The baseUrl as specified in the properties file by the user
	 * @return
	 */
	@ModelAttribute("snapadmin_baseUrl")
	public String getBaseUrl() {
		return props.getBaseUrl();
	}
	
	/**
	 * The full request URL, not including the query string
	 * @param request
	 * @return
	 */
	@ModelAttribute("snapadmin_requestUrl")
	public String getRequestUrl(HttpServletRequest request) {
		return request.getRequestURI();
	}
	
	/**
	 * The UserConfiguration object used to retrieve values specified
	 * in the settings table.
	 * @return
	 */
	@ModelAttribute("snapadmin_userConf")
	public UserConfiguration getUserConf() {
		return userConf;
	}
	
	@ModelAttribute("snapadmin_properties")
	public SnapAdminProperties getProps() {
		return props;
	}
	
	@ModelAttribute("snapadmin_authenticated") 
	public boolean isAuthenticated() {
		return snapAdmin.isAuthenticated();
	}
	
	@ModelAttribute("snapadmin_authenticatedUser")
	public String authenticatedUser(Principal principal) {
		if (principal == null) return null;
		return principal.getName();
	}
}

