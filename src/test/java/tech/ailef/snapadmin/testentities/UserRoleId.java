package tech.ailef.snapadmin.testentities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for UserRole using @IdClass
 */
public class UserRoleId implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String roleCode;

	public UserRoleId() {
	}

	public UserRoleId(Long userId, String roleCode) {
		this.userId = userId;
		this.roleCode = roleCode;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserRoleId that = (UserRoleId) o;
		return Objects.equals(userId, that.userId) &&
			Objects.equals(roleCode, that.roleCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, roleCode);
	}

	@Override
	public String toString() {
		return "UserRoleId{userId=" + userId + ", roleCode='" + roleCode + "'}";
	}
}
