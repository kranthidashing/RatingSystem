package com.ritwik.web.services;

import com.ritwik.web.model.setques;
import com.ritwik.web.model.vendor;
import com.ritwik.web.model.vendorform;

public interface services {
	
	public vendor signup(vendorform v);
	public String login(String uname,String password);
	public String viewprofile();
	public String logout();
	public String addQues(setques q);
	public String adminaddServices(String service, String products);
	
}
