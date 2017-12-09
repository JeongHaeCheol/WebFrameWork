package kr.ac.hansung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

	@RequestMapping("/login")
	public String showLogin( @RequestParam(value="error", required=false) String error,
		@RequestParam(value="logout", required=false) String logout, Model model) {
		
		if(logout != null) {
			model.addAttribute("logoutMsg", "you have been logged out successfully");
		}
		
		if(error != null)
			model.addAttribute("errorMsg", "Invalid username and password");
		return "login";
	}
	
}
