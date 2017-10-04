package com.ritwik.web.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//admin
@Entity
@Table(name = "providedserviceproducts", catalog = "reviewsystem")
public class providedserviceproducts {
	
	@Id
	@Column(name="pspid")
	private Integer PSPid;
	@Column(name="pspname")
	private String PSPname;
	@Column(name="sid")
	private Integer Sid;
	
	@OneToMany(mappedBy="providedserviceproducts", cascade = CascadeType.ALL)
	private Set<questions> questions;
	
	@ManyToMany(mappedBy = "providedserviceproducts", fetch = FetchType.EAGER)
	private Set<vendor> vendor;
	
	
	public providedserviceproducts() {
		
	}
    public providedserviceproducts(Integer PSPid,String PSPname) {
    	this.PSPid=PSPid;
    	this.PSPname=PSPname;
	}
    public providedserviceproducts(Integer PSPid,String PSPname,Integer Sid) {
    	this.PSPid=PSPid;
    	this.PSPname=PSPname;
    	this.Sid=Sid;	
	}
    public providedserviceproducts(Integer PSPid,String PSPname,Integer Sid,Set<vendor> vendor) {
    	this.PSPid=PSPid;
    	this.PSPname=PSPname;
    	this.Sid=Sid;	
    	this.vendor=vendor;
	}
	
	public Integer getPSPid() {
		return PSPid;
	}
	public void setPSPid(Integer PSPid) {
		this.PSPid=PSPid;
	}
	public String getPSPname() {
		return PSPname;
	}
	public void setPSPname(String PSPname) {
		this.PSPname=PSPname;
	}
	public Integer getSid() {
		return Sid;
	}
	public void setSid(Integer Sid) {
		this.Sid=Sid;
	}
	public Set<vendor> getVendor() {
		return vendor;
	}
	
	public void setVendor(Set<vendor> vendor) {
		this.vendor=vendor;
	}
	public Set<questions> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<questions> questions) {
		this.questions = questions;
	}
	

	
}
