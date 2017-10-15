package com.Jahan.Task_Management.controller;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.Jahan.Task_Management.helper.*;
import com.Jahan.Task_Management.helperModel.*;
import com.Jahan.Task_Management.repo.UserRepository;
/*
 * 	Login Controller for checking and validation of user input
*/
@SessionAttributes("UserSession")
@Controller
public class LoginController {
	@Autowired(required = true)
	UserRepository UserRepositoryT;
	@Autowired
	LoginHelper LoginHelperT;
	@RequestMapping(value= {"/", "/home"},method=RequestMethod.GET)
	public String indexPage(){
		// return view of the login page.
		return "/login/index";
	}
	/* test */
	@RequestMapping(value= {"/{accountNumber}"},method=RequestMethod.GET)
	public String indexPage2(@PathVariable final int accountNumber){
		
		// return view of the login page.
		return "public number "+accountNumber;
	}
	/* Login form with error. */
	@RequestMapping(value="/Login",method=RequestMethod.GET)
	public String Login(Model model){
		UserHelperModel aUser=new UserHelperModel();
		model.addAttribute("aUser",aUser);
		// return view of the login page.
		return "/login/login";
	}
	/* Login form  */
	@RequestMapping(value="/Login",method=RequestMethod.POST)
	public ModelAndView process(Model model, @ModelAttribute("aUser") UserHelperModel aUser){
		String aText="";
		if(LoginHelperT!=null) {
			aText= LoginHelperT.checkUser(aUser);
		}
		if(aText.equals("Success"))
		{
			// redirect to find all user page.
			long tempId= LoginHelperT.getUserId(aUser);
			if(tempId!=-1) {
				aUser=new UserHelperModel(LoginHelperT.getUserInfoByID(tempId));
			}
			model.addAttribute("UserSession",aUser);
			return new ModelAndView("redirect:/UI");
		}
		else
		{
			return new ModelAndView("redirect:/login-error");
		}
	}
	/* Login form with error. 
	 * 
	 */
    @RequestMapping("/login-error")
    public String loginError(Model model) {
    	UserHelperModel aUser=new UserHelperModel();
		model.addAttribute("aUser",aUser);
        model.addAttribute("loginError", true);
        return "/login/login";
    }
    /* 
	 * Logout  
	 */
    @RequestMapping(value="/Logout" ,method=RequestMethod.GET)
    public ModelAndView Logout(Model model, HttpSession session, @ModelAttribute("UserSession") UserHelperModel aSessionUser,final SessionStatus status) {
    	aSessionUser=new UserHelperModel();
    	session.invalidate();
    	status.setComplete();
    	return new ModelAndView("redirect:/Login");
    }
}
