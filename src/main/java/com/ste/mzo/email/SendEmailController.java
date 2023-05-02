package com.ste.mzo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SendEmailController {

	@Autowired
	private JavaMailSender mailSender;
	
    @GetMapping("/sendEmail")
	public String showContactForm() {
		
		return "email/sendEmail";
	}
    
    @PostMapping("/sendEmail")
    public String submitContact(HttpServletRequest request) {
    	String fullName = request.getParameter("fullName");
    	String email = request.getParameter("email");
    	String subject = request.getParameter("subject");
    	String content = request.getParameter("content");
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	
    	message.setFrom("zorganimed.mohamed@gmail.com");
    	message.setTo("zorganimed.mohamed@gmail.com");
    	
    	String mailSubject = fullName + " has sent a message ";
    	String mailContent = "Sender Name "+fullName+" \n ";
    	mailContent += "Sender E-mail "+email;
    	mailContent += "Subject "+subject+" \n";
    	mailContent += "Content "+content+" \n ";
    	message.setSubject(mailSubject);
    	message.setText(mailContent);
    	
    	mailSender.send(message);
    	
    	
    	return "redirect:/sendEmail";
    }
}
