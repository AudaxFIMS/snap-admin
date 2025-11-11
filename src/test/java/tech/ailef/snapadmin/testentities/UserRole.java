package tech.ailef.snapadmin.testentities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * Test entity using @IdClass for composite primary key
 */
@Entity
@Table(name = "user_roles")
@IdClass(UserRoleId.class)
public class UserRole {

	@Id
	private Long userId;

	@Id
	private String roleCode;

	private String userName;
	private String roleName;
	private LocalDateTime assignedDate;
	private Boolean active;

	public UserRole() {
	}

	public UserRole(Long userId, String roleCode, String userName, String roleName) {
		this.userId = userId;
		this.roleCode = roleCode;
		this.userName = userName;
		this.roleName = roleName;
		this.assignedDate = LocalDateTime.now();
		this.active = true;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public LocalDateTime getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDateTime assignedDate) {
		this.assignedDate = assignedDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserRole{" +
			"userId=" + userId +
			", roleCode='" + roleCode + '\'' +
			", userName='" + userName + '\'' +
			", roleName='" + roleName + '\'' +
			", assignedDate=" + assignedDate +
			", active=" + active +
			'}';
	}
}
