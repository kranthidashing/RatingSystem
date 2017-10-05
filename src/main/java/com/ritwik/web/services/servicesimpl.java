package com.ritwik.web.services;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ritwik.web.model.providedserviceproducts;
import com.ritwik.web.model.providedservices;
import com.ritwik.web.model.quesdes;
import com.ritwik.web.model.questions;
import com.ritwik.web.model.vendor;
import com.ritwik.web.model.vendorform;


@Component
public class servicesimpl implements services  {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	servicerepo servicerepo;
	
	@Autowired
	servicerepo2 servicerepo2;
	
	@Autowired
	servicerepo3 servicerepo3;
	
	@SuppressWarnings("unused")
	@Autowired
	private HttpSession httpsession;
	
	@Autowired
	private HttpServletRequest request;
	
	@SuppressWarnings("unused")
	@Autowired
	private HttpServletResponse response;
	
	
	
	public vendor signup(vendorform v) {
//		String[] words=provideservice.split(","); 
		//	String[] words1=provideserviceproduct.split(",");
			
		 //     for(String w:words) {
		    	  String jpql = "FROM  providedservices as p WHERE p.PSname = ?";
		  		  providedservices PS=(providedservices) entityManager.createQuery(jpql).setParameter(1,v.getProvidedservices()).getSingleResult();  
	        	   
		  //    }
		    
		//    for(String w1:words1)
		//    {
			String jpql1 = "FROM providedserviceproducts as p WHERE p.PSPname = ?";
			providedserviceproducts PSP= (providedserviceproducts) entityManager.createQuery(jpql1).setParameter(1,v.getProvidedserviceproducts()).getSingleResult();	
			
		//    }
			Set<providedservices> ps = new HashSet<providedservices>(); 
			Set<providedserviceproducts> psp = new HashSet<providedserviceproducts>();
			ps.add(PS);	
			psp.add(PSP);
			vendor V = new vendor();
			V.setFirstName(v.getFname());
			V.setLastName(v.getLname());
			V.setUserName(v.getUname());
			V.setPassword(v.getPassword());
			V.setEmail(v.getEmail());
			V.setProvidedservices(ps);
			V.setProvidedserviceproducts(psp);
			return servicerepo.save(V);	
		
		
	}
	
	public String login(String uname,String password) {
		String jpql = "FROM  vendor as v WHERE v.UserName = ?";
		vendor v = (vendor) entityManager.createQuery(jpql).setParameter(1,uname).getSingleResult();
		String name=v.getUserName();
		String pass=v.getPassword();
		Integer id=v.getVid();
		if(name.equals(uname) && pass.equals(password)) {
			HttpSession session=request.getSession();  
	        session.setAttribute("id",id);
			return "Welcome "+ session.getAttribute("id");	
		}
		else {
			return "Wrong UserName or Password";
		}
	}
	public String viewprofile() {
		HttpSession session=request.getSession(false);  
        if(session!=null){  
        Integer id=(Integer) session.getAttribute("id");  
        return "Valid_User" + id;
        } 
        else {
        	return "please_Login_First";
        }
	}
	public String logout() {
		HttpSession session=request.getSession();  
        session.invalidate();
    //    Status=1;
        return "Logged_Out Succesfully";
	}
	
	public String addQues() {
		  String jpql = "FROM  providedservices as p WHERE p.PSname = ?";
 		  providedservices PS=(providedservices) entityManager.createQuery(jpql).setParameter(1,"food").getSingleResult();  
		
 		  String jpql1 = "FROM providedserviceproducts as p WHERE p.PSPname = ?";
		  providedserviceproducts PSP= (providedserviceproducts) entityManager.createQuery(jpql1).setParameter(1,"milkshake").getSingleResult();	
		  
	        HttpSession session=request.getSession();   
	        Integer id=(Integer) session.getAttribute("id");  
	        String jpql2 = "FROM vendor as p WHERE p.Vid = ?";
			vendor vv=(vendor) entityManager.createQuery(jpql2).setParameter(1,id).getSingleResult();	
	        quesdes ques = new quesdes("krishna?");
	        servicerepo2.save(ques);
	        quesdes ques1 = new quesdes("kranthi?");
	        servicerepo2.save(ques1);
	        questions q= new questions(ques,vv,PS,PSP);
	        questions q1= new questions(ques1,vv,PS,PSP);
	        servicerepo3.save(q);
	        servicerepo3.save(q1); 
	        return "success";
	
	}	
}
