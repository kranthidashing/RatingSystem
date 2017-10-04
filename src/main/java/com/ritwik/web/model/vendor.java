package com.ritwik.web.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "vendor", catalog = "reviewsystem")
public class vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="vid")
	private Integer Vid;
	@Column(name="fname")
	private String FirstName;
	@Column(name="lname")
	private String LastName;
	@Column(name="uname")
	private String UserName;
	@Column(name="pass")
	private String Password;
	@Column(name="email")
	private String Email;
	
	@OneToMany(mappedBy = "v", cascade = CascadeType.ALL)
	private Set<questions> questions;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "vendor_providedservices", joinColumns = @JoinColumn(name = "Vid", referencedColumnName = "Vid"), 
    inverseJoinColumns = @JoinColumn(name = "PSid", referencedColumnName = "PSid"))
	private Set<providedservices> providedservices;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "vendor_providedserivceproducts", joinColumns = @JoinColumn(name = "Vid", referencedColumnName = "Vid"), 
    inverseJoinColumns = @JoinColumn(name = "PSPid", referencedColumnName = "PSPid"))
	private Set<providedserviceproducts> providedserviceproducts;
	
	public vendor() {
		
	}
    public vendor(Integer Vid,Set<questions> questions) {
    	this.Vid=Vid;
    	this.questions=questions;
		
	}
	public vendor(Integer Vid,Set<providedservices> providedservices,Set<providedserviceproducts> providedserviceproducts) {
		super();
		this.Vid=Vid;
		this.providedservices=providedservices;
		this.providedserviceproducts=providedserviceproducts;
	}
	public vendor(String FirstName,String LastName,String UserName,String Password,String Email) {
		this.FirstName=FirstName;
		this.LastName=LastName;
		this.UserName=UserName;
		this.Password=Password;
		this.Email=Email;
	}
	public vendor(Integer Vid,String FirstName,String LastName,String UserName,String Password,String Email,Set<providedservices> providedservices,Set<providedserviceproducts> providedserviceproducts) {
		super();
		this.Vid=Vid;
		this.FirstName=FirstName;
		this.LastName=LastName;
		this.UserName=UserName;
		this.Password=Password;
		this.Email=Email;
		this.providedservices=providedservices;
		this.providedserviceproducts=providedserviceproducts;
	}
	public Integer getVid() {
		return Vid;
	}
	public void setVid(Integer Vid) {
		this.Vid=Vid;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String FirstName) {
		this.FirstName=FirstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String LastName) {
		this.LastName=LastName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String UserName) {
		this.UserName=UserName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String Password) {
		this.Password=Password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String Email) {
		this.Email=Email;
	}
	public Set<providedservices> getProvidedservices() {
		return providedservices;
	}
	
	public void setProvidedservices(Set<providedservices> providedservices) {
		this.providedservices=providedservices;
	}
	public Set<providedserviceproducts> getProvidedserviceproducts() {
		return providedserviceproducts;
	}
	
	public void setProvidedserviceproducts(Set<providedserviceproducts> providedserviceproducts) {
		this.providedserviceproducts=providedserviceproducts;
	}
	
	
	public Set<questions> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<questions> questions) {
		this.questions = questions;
	}
	
}
