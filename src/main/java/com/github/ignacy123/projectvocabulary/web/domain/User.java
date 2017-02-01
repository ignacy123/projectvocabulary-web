package com.github.ignacy123.projectvocabulary.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Formatter;
import java.util.Set;

/**
 * Created by ignacy on 19.05.16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements UserDetails {

	public enum Type {
		STUDENT,
		TEACHER;
	}

	@XmlAttribute
	private Long id;
	@XmlAttribute
	private Type type;
	@XmlAttribute
	private String email;
	@XmlAttribute
	@JsonIgnore
	private String encodedPassword;
	@XmlAttribute
	private String firstName;
	@XmlAttribute
	private String lastName;

	@JsonIgnore
	private Collection<GrantedAuthority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public void setRawPassword(PasswordEncoder encoder, String rawPassword) {
		this.encodedPassword = encoder.encode(rawPassword);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public static String computeHash(String str) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(str.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;

	}

	public boolean matchesPassword(PasswordEncoder encoder, String rawPassword) {
		return encoder.matches(rawPassword, this.encodedPassword);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Role> roles) {
		String[] rolesStrings = new String[roles.size()];
		int i = 0;
		for (Role role : roles) {
			rolesStrings[i] = "ROLE_"+role.toString();
			i++;
		}
		authorities = AuthorityUtils.createAuthorityList(rolesStrings);
	}

	@Override
	public String getPassword() {
		return encodedPassword;
	}

	@Override
	public String getUsername() {
		return email;
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
		return true;
	}
}
