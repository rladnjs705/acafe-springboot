package com.javadeveloperzone.model;

import com.javadeveloperzone.config.ProjectConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 6726068610336321405L;
	
	private String id;
	private String password;
	private String name;
	private String rank;
	private String team;
	private String level;
	private String email;
	private int falsePwd;
	private String authority = ProjectConstants.USER_ROLE;
	private String ipAddress;
	private String pwdChgDttm;
	private boolean enabled;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(authority));
        return auth;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFalsePwd() {
		return falsePwd;
	}

	public void setFalsePwd(int falsePwd) {
		this.falsePwd = falsePwd;
	}

	@Override
	public boolean equals(Object obj) {
		CustomUserDetails objInput = (CustomUserDetails)obj;

		return id.equals(objInput.getId());
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPwdChgDttm() {
		return pwdChgDttm;
	}

	public void setPwdChgDttm(String pwdChgDttm) {
		this.pwdChgDttm = pwdChgDttm;
	}

}