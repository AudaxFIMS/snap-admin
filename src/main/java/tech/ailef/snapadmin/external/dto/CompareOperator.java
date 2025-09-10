/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.dto;

/**
 * A list of operators that are used in faceted search. 
 *
 */
public enum CompareOperator {
	GT {
		@Override
		public String getDisplayName() {
			return "Greater than";
		}
	},
	LT {
		@Override
		public String getDisplayName() {
			return "Less than";
		}
	},
	EQ {
		@Override
		public String getDisplayName() {
			return "Equals";
		}
	},
	STRING_EQ {
		@Override
		public String getDisplayName() {
			return "Equals";
		}
	},
	BEFORE {
		@Override
		public String getDisplayName() {
			return "Before";
		}
	},
	AFTER {
		@Override
		public String getDisplayName() {
			return "After";
		}
	},
	CONTAINS {
		@Override
		public String getDisplayName() {
			return "Contains";
		}
	};
	
	public abstract String getDisplayName();
	
	public String toString() {
		return this.name().toLowerCase();
	}
}
