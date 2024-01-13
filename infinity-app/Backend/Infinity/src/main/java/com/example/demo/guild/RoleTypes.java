package com.example.demo.guild;

/**
 * Has many different roles that can be assigned to a user on a server
 */
public class RoleTypes {

	private RoleType type;
	
	public RoleTypes(String sType) {
		if (sType.equals("OWNER")) {
			this.type = RoleType.OWNER;
		} else if (sType.equals("MOD")) {
			this.type = RoleType.MOD;
		} else {
			this.type = RoleType.MEMBER;
		}
	}
	
	public enum RoleType {
		OWNER, MOD, MEMBER
	}

	/**
	 * gets the type of role a user is
	 * 
	 * @return - type of role this user is
	 */
	public RoleType getType() {
		return type;
	}

	/**
	 * sets the type of role a user is
	 * 
	 * @param type - sets the role of a user
	 */
	public void setType(RoleType type) {
		this.type = type;
	}

	
}
