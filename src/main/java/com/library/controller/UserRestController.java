package com.library.controller;



import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.library.user.domain.FindUserForm;
import com.library.user.domain.User;
import com.library.user.domain.UserForm;
import com.library.user.domain.UserType;
import com.library.user.service.UserService;



@Controller
public class UserRestController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";
	@Autowired
	UserService userService;
	
	
/*	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		
		User userForm = new User();    
        model.put("userForm", userForm);
		return "registration";
	}
*/
	
	@RequestMapping(value = "/existsUser", method = RequestMethod.POST)
	public String existsUser(@Valid @ModelAttribute("userform")FindUserForm userform, 
		      BindingResult result, ModelMap model) {

		User user = userService.getUser(userform.getUsername());
		String view;
		//Should have used an optional here
		if(user!=null) {					
			view = "manage";			
		} else {	
			 model.addAttribute("userForm", new UserForm());
			view = "welcome";
		}		
		return view;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showForm(@Valid @ModelAttribute("userform")FindUserForm userform, 
		      BindingResult result, ModelMap model) {
		        if (result.hasErrors()) {
		            return "error";
		        }
		        model.addAttribute("username", userform.getUsername());		        
		        return "welcome";
		    }


	
	 @RequestMapping(value = "/registration", method = RequestMethod.GET)
	    public String registration(Model model) {
	        model.addAttribute("userForm", new UserForm());

	        return "registration";
	    }
	 
	 
	 @RequestMapping(value = "/registration", method = RequestMethod.POST)
	    public String registration(@ModelAttribute("userForm") UserForm userForm)
	 {	
		 System.out.println("sasassssssssssssssssss**********");
	System.out.println(userForm.getAddress_1());
	User  user = new User();
	user.setAddress_1(userForm.getAddress_1());
	
	user.setAddress_2(userForm.getAddress_2());
	user.setAdmin(true);
	user.setFname(userForm.getFname());
	user.setLname(userForm.getLname());
	user.setType(UserType.DONOR);
	user.setZip(userForm.getZip());
	
	  userService.registerUserDetails(user);
	       
	 
	        return "registrationSuccess";
	    }
	/*@RequestMapping("/register")
	public String registerUser(User usr) {
        userService.registerUserDetails(usr);
		return "welcome";
	}
	
	 @RequestMapping(method = RequestMethod.POST)
	    public String processRegistration(@ModelAttribute("userForm") User user,
	            Map<String, Object> model) {
	         
	        // implement your own registration logic here...
	         
	        // for testing purpose:
	        System.out.println("username: " + user.getAddress_1());
	         
	        return "RegistrationSuccess";
	    }*/
}
