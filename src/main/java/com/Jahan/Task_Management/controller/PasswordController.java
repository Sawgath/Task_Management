package com.Jahan.Task_Management.controller;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.Jahan.Task_Management.helper.*;
import com.Jahan.Task_Management.model.*;
import com.Jahan.Task_Management.repo.UserRepository;
@Controller
public class PasswordController {
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository UserRepositoryT;
	
	// Display forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage(Model model) {

		return new ModelAndView("/passwordreset-interface/forgotpassword");
    }
    
    // Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {

		// Lookup user in database by e-mail
		User aUser = UserRepositoryT.findByEmail(userEmail);

		if (aUser==null) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} 
		else {
			
			// Generate random 36-character string token for reset password 
			String randompassword=UUID.randomUUID().toString();

			//aUser.setpassword(bCryptPasswordEncoder.encode(randompassword));
			// Save token to database
			UserRepositoryT.save(aUser);

			String appUrl = request.getScheme() + "://" + request.getServerName();
			
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("sawjahan4@gmail.com");
			passwordResetEmail.setTo(aUser.getemail());
			passwordResetEmail.setSubject("Password Reset");
			passwordResetEmail.setText("You have reset your password, New password has given below:\n" + randompassword+"\n Please change your password after login."+appUrl);
			
			emailService.sendEmail(passwordResetEmail);

			// Add success message to view
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
		}

		modelAndView.setViewName("forgotPassword");
		return modelAndView;

	}

	// Display form to reset password
//	@RequestMapping(value = "/reset", method = RequestMethod.GET)
//	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		
//		Optional<User> user = userService.findUserByResetToken(token);
//
//		if (user.isPresent()) { // Token found in DB
//			modelAndView.addObject("resetToken", token);
//		} else { // Token not found in DB
//			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
//		}
//
//		modelAndView.setViewName("resetPassword");
//		return modelAndView;
//	}

	// Process reset password form
//	@RequestMapping(value = "/reset", method = RequestMethod.POST)
//	public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
//
//		// Find the user associated with the reset token
//		Optional<User> user = userService.findUserByResetToken(requestParams.get("token"));
//
//		// This should always be non-null but we check just in case
//		if (user.isPresent()) {
//			
//			User resetUser = user.get(); 
//            
//			// Set new password    
//            resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
//            
//			// Set the reset token to null so it cannot be used again
//			resetUser.setResetToken(null);
//
//			// Save user
//			userService.saveUser(resetUser);
//
//			// In order to set a model attribute on a redirect, we must use
//			// RedirectAttributes
//			redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
//
//			modelAndView.setViewName("redirect:login");
//			return modelAndView;
//			
//		} else {
//			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
//			modelAndView.setViewName("resetPassword");	
//		}
//		
//		return modelAndView;
//  }
   
    // Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:Login");
	}

}



