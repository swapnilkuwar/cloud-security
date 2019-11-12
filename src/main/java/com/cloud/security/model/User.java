package com.cloud.security.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	
	@Column(name="USER_ID", unique = true, nullable = false)
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="SSO_ID", unique=true, nullable=false)
	private String ssoId;
	
	@Column(name="GID", unique=true, nullable=false)
	private String gId;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;
		
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@Column(name="LAST_NAME", nullable=false)
	private String lastName;

	@Column(name="EMAIL", nullable=false)
	private String email;

	@Column(name="STATE", nullable=false)
	private String state=State.ACTIVE.getState();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_PROFILE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "PROFILE_ID") })
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

	
	public User() {
		super();
	}
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USER_ID")
    private List<MyDataset> userSet = new ArrayList<MyDataset>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (ssoId == null) {
			if (other.ssoId != null)
				return false;
		} else if (!ssoId.equals(other.ssoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", ssoId=" + ssoId + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", state=" + state + ", userProfiles=" + userProfiles +"]";
	}

	public List<MyDataset> getUserSet() {
		return userSet;
	}

	public void setUserSet(List<MyDataset> userSet) {
		this.userSet = userSet;
	}


	public User(int id, String ssoId, String gId, String password, String firstName, String lastName, String email,
			String state, Set<UserProfile> userProfiles, List<MyDataset> userSet) {
		super();
		this.id = id;
		this.ssoId = ssoId;
		this.gId = gId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.state = state;
		this.userProfiles = userProfiles;
		this.userSet = userSet;
	}

	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}
}
