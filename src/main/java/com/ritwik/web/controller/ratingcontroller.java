package com.ritwik.web.controller;


import java.util.HashSet;
import java.util.Set;

import javax.mail.internet.MimeMessage;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ritwik.web.model.providedserviceproducts;
import com.ritwik.web.model.providedservices;
import com.ritwik.web.model.vendor;
import com.ritwik.web.model.vendorform;
import com.ritwik.web.services.services;
import com.ritwik.web.services.servicerepo;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;


@RestController
@RequestMapping("/rating")
public class ratingcontroller {
		
    Integer Status=1;
	
	
	
	private Facebook facebook;
    private ConnectionRepository connectionRepository;
	public ratingcontroller(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/kk")
    public String helloFacebook(HttpServletRequest request,HttpServletResponse response) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
        String [] fields = { "id","name","birthday","email","location","hometown","gender","first_name","last_name"};
        User user = facebook.fetchObject("me", User.class, fields);
      //  String id=user.getId();
        String name=user.getName();
        String email=user.getEmail();
        String gender=user.getGender();
        String firstname=user.getFirstName();
        String lastname=user.getLastName();
        String pass=null;
        System.out.println(name + " " +email+" "+gender+" "+firstname+" "+lastname);
        vendor v = null;
        try {
        String jpql = "FROM  vendor as v WHERE v.UserName = ?";
		v = (vendor) entityManager.createQuery(jpql).setParameter(1,name).getSingleResult();
        }catch(NoResultException e) {System.out.println(e);}
		if(v==null) {
        	vendor vnd = new vendor(firstname,lastname,name,pass,email);
  //      	rr.save(vnd);
        	String jpql = "FROM  vendor as v WHERE v.UserName = ?";
     		v = (vendor) entityManager.createQuery(jpql).setParameter(1,name).getSingleResult();
        	//return "success";
        } 
		Integer id1=v.getVid();
        HttpSession session=request.getSession();  
        session.setAttribute("id",id1); 
        
        Status=0;
        
        return "hello";
    }
	
	@Autowired
	services services;
	
	@Autowired
	servicerepo servicerepo;
	
	@Autowired
	private JavaMailSender sender;

	
	@PersistenceContext
	EntityManager entityManager;
	
	@RequestMapping(method = RequestMethod.PUT, value = "/vendor")
	public vendor signup(@RequestBody vendorform v) {
		 return services.signup(v);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/login/{u}/{p}")
	public String login(@PathVariable("u") final String uname,@PathVariable("p") final String password) {
		return services.login(uname,password);
	}
	@RequestMapping(method = RequestMethod.POST, value ="/viewprofile")
	public String viewprofile() {
		return services.viewprofile();
		
	}
	@RequestMapping(method = RequestMethod.POST, value ="/logout")
	public String logout() {
		return services.logout();
	}
	
	 @ResponseBody
	 String home(String p,String e) {
        try {
            sendEmail(p,e);
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: "+ex;
        }
    }
		    private void sendEmail(String p,String e) throws Exception{
		        MimeMessage message = sender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		        
		        helper.setTo(e);
		        helper.setText("Your PassWord :"+p);
		        helper.setSubject("Forget Password");
		        
		        sender.send(message);
		    
	}
   @RequestMapping(method = RequestMethod.POST, value ="/api/javainuse/forgetpassword/{uname}")
   public String forgetpassword(@PathVariable("uname") final String uname,HttpServletRequest request) {
	   HttpSession session=request.getSession(false);  
       if(session==null){  
	   String jpql = "FROM  vendor as v WHERE v.UserName = ?";
	   vendor v = (vendor) entityManager.createQuery(jpql).setParameter(1,uname).getSingleResult();
	   String p = v.getPassword();
	   String e = v.getEmail();
	   return home(p,e);
       }
       else {
       	return "All Ready Valid User";
       }
	   	   
   }
   @RequestMapping(method = RequestMethod.POST, value ="/api/javainuse/Changepassword/{currentpassword}/{newpassword}")
   public String Changepassword(@PathVariable("currentpassword") final String currentpassword,@PathVariable("newpassword") final String newpassword,HttpServletRequest request) {
	   HttpSession session=request.getSession(false);  
       if(session!=null){  
    	   Integer id = (Integer) session.getAttribute("id");
    	   String jpql = "FROM  vendor as v WHERE v.Vid = ?";
    	   vendor v = (vendor) entityManager.createQuery(jpql).setParameter(1,id).getSingleResult();
    	  if(v!=null && (v.getPassword().equals(currentpassword)) ) {
    		  v.setPassword(newpassword);
    //		  rr.save(v);
    		  return "Password Changed Succesfully";
    		  
    	  }
    	  else {
    		  return "Wrong Current Password";
    	  }
    	   
       }
       else {
          	return "Please login first";
          }
       
   }
   @RequestMapping(method = RequestMethod.POST, value ="/api/javainuse/create/connect/facebook")
	public String fblogin() {
		if(Status==1)
		 return "LOGIN TO FACEBOOK:localhost:8550/connect/facebook";
		else 
		 return "logined Succesfully";
		

	} 
   
   @RequestMapping(method = RequestMethod.POST, value ="/api/javainuse/Add service & Product/{add_service}/{add_product}")
   public String addservice(@PathVariable("add_service") final String as,@PathVariable("add_product") final String ap,HttpServletRequest request) {
	   HttpSession session=request.getSession(false);  
       if(session!=null){  
    	   Integer id = (Integer) session.getAttribute("id");
	   String jpql = "FROM  providedservices as p WHERE p.PSname = ?";
	   providedservices PS=(providedservices) entityManager.createQuery(jpql).setParameter(1,as).getSingleResult(); 
	   String jpql1 = "FROM providedserviceproducts as p WHERE p.PSPname = ?";
	   providedserviceproducts PSP= (providedserviceproducts) entityManager.createQuery(jpql1).setParameter(1,ap).getSingleResult();	
	    Set<providedservices> ps = new HashSet<providedservices>(); 
		Set<providedserviceproducts> psp = new HashSet<providedserviceproducts>();
		ps.add(PS);	
		psp.add(PSP);
		vendor vg = new vendor(id,ps,psp);
//		rr.save(vg);
		 return "details_Added";
       }
       else
    	   return "login_First";
		
   }
   

}
