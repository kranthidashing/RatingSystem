package com.ritwik.web.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quesdes", catalog = "reviewsystem")
public class quesdes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="quesid") 
	private Integer Quesid;
	@Column(name="question")
	private String Question;
	
	
	
	@OneToMany(mappedBy = "quesdes", cascade = CascadeType.ALL)
	private Set<questions> questions;
	
	
	public quesdes(String ques) {
		this.Question=ques;
	}

    public Integer getQuesid() {
		return Quesid;
	}
	public void setQuesid(Integer qid) {
		Quesid = qid;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public Set<questions> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<questions> questions) {
		this.questions = questions;
	}

}
