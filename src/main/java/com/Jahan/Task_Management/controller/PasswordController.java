package com.Jahan.Task_Management.controller;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.Jahan.Task_Management.helper.*;
import com.Jahan.Task_Management.helperModel.PasswordResetHelperModel;
import com.Jahan.Task_Management.model.*;
import com.Jahan.Task_Management.repo.UserRepository;
@Controller
public class PasswordController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private LoginHelper LoginHelperT;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository UserRepositoryT;
	/*
	 * Display forgotPassword page
	 */
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage(Model model) {
		return new ModelAndView("/passwordreset-interface/forgotpassword");
    }
	/*
	 * Process form submission from forgotPassword page
	 */
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request,RedirectAttributes redir) {
		// Lookup user in database by e-mail
		User aUser = UserRepositoryT.findByEmail(userEmail);
		if (aUser==null) 
		{
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} 
		else 
		{
			// Generate random 36-character string token for reset password 
			String randompassword=UUID.randomUUID().toString();
			// Save token to database
			LoginHelperT.savetokenUser(userEmail, randompassword);
			String appUrl = request.getScheme() + "://" + request.getServerName();
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("sawjahan4@gmail.com");
			passwordResetEmail.setTo(aUser.getemail());
			passwordResetEmail.setSubject("Password Reset");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl+""
					+ "/reset?token=" + aUser.getResetToken());
			emailService.sendEmail(passwordResetEmail);
			// Add success message to view
			//modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
			redir.addFlashAttribute("successMessage", "A password reset link has been sent to " + userEmail);
			
		}
		modelAndView.setViewName("redirect:forgot");
		return modelAndView;
	}
	/*
	 * Display form to reset password
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		User aUser = UserRepositoryT.findUserByResetToken(token);
		if (aUser!=null) 
		{ // Token found in DB
			PasswordResetHelperModel aPasswordResetHelperModel= new PasswordResetHelperModel();
			aPasswordResetHelperModel.aUser=aUser;
			aPasswordResetHelperModel.setResetToken(token);
			modelAndView.addObject("aPasswordResetHelperModel", aPasswordResetHelperModel);
		} else 
		{ // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}
		modelAndView.setViewName("/passwordreset-interface/resetPassword");
		return modelAndView;
	}
	/*
	 * Process reset password form
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @ModelAttribute("aPasswordResetHelperModel") PasswordResetHelperModel aPasswordResetHelperModel,RedirectAttributes redir) {
		// Find the user associated with the reset token
		User aUser = UserRepositoryT.findUserByResetToken(aPasswordResetHelperModel.getResetToken());
		String password1=aPasswordResetHelperModel.newPassword;
		String password2=aPasswordResetHelperModel.oldPassword;
		if(password1.equals(password2) && password1.length()>4)
		{
			// This should always be non-null but we check just in case
			if (aUser!=null) 
			{
				User resetUser = aUser;    
				// Set new password    
	            resetUser.setpassword(bCryptPasswordEncoder.encode(aPasswordResetHelperModel.newPassword));
				// Set the reset token to null so it cannot be used again
				resetUser.setResetToken(null);
				// Save user
				UserRepositoryT.save(resetUser);
				// In order to set a model attribute on a redirect, we must use
				// RedirectAttributes
				redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
				modelAndView.setViewName("redirect:Login");
				return modelAndView;
			} 
			else 
			{
				modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link ");
				modelAndView.setViewName("/passwordreset-interface/resetPassword");	
			}
		}
		else 
		{			
			modelAndView.addObject("errorMessage", "Oops!  Password mismatch or password length should be atleast 5");
			modelAndView.setViewName("/passwordreset-interface/resetPassword");	
		}
		return modelAndView;
	}
	/*
	 * Going to reset page without a token redirects to login page
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:Login");
	}

}



