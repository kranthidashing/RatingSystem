package com.ritwik.web.services;

import com.ritwik.web.model.vendor;
import com.ritwik.web.model.vendorform;

public interface services {
	
	public vendor signup(vendorform v);
	public String login(String uname,String password);
	public String viewprofile();
	public String logout();
	
}
