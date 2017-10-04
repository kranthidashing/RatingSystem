package com.ritwik.web.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "questions", catalog = "reviewsystem")
public class questions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="qid")
	private Integer Qid;
	
	@ManyToOne
	@JoinColumn(name = "Quesid")
	private quesdes quesdes;
	
	@ManyToOne
	@JoinColumn(name = "Vid")
	private vendor v;
	@ManyToOne
	@JoinColumn(name = "PSid")
	private providedservices providedservices;
	@ManyToOne
	@JoinColumn(name = "PSPid")
	private providedserviceproducts providedserviceproducts;
	
	public questions(quesdes quesdes,vendor v,providedservices ps,providedserviceproducts psp) {
		this.quesdes=quesdes;
		this.v=v;
		this.providedservices=ps;
		this.providedserviceproducts=psp;
		
	}
	
	
	public Integer getQid() {
		return Qid;
	}

	public void setQid(Integer qid) {
		Qid = qid;
	}


	
	public vendor getV() {
		return v;
	}

	public void setV(vendor v) {
		this.v = v;
	}

	public providedservices getProvidedservices() {
		return providedservices;
	}
	public void setProvidedservices(providedservices providedservices) {
		this.providedservices = providedservices;
	} 	
	public providedserviceproducts getProvidedserviceproducts() {
		return providedserviceproducts;
	}

	public void setProvidedserviceproducts(providedserviceproducts providedserviceproducts) {
		this.providedserviceproducts = providedserviceproducts;
	}


	public quesdes getQuesdes() {
		return quesdes;
	}


	public void setQuesdes(quesdes quesdes) {
		this.quesdes = quesdes;
	}


	

}
