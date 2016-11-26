package app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;


@RestController
@RequestMapping("/user/register")
public class RegisterRestController {
	
	private JavaMailSender mailSender = new JavaMailSenderImpl();
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/{username:.+}", method=RequestMethod.GET)
	public ResponseEntity confirm(@PathVariable String username){
		try {
			System.out.println("received path variable username: " + username);
			User user = userRepo.findByUsername(username);
			user.setActivated(true);
			
			userRepo.save(user);
		return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity register(@RequestBody String body, WebRequest req){
		
		JSONObject json = new JSONObject (body); 
		
		String name = (String) json.get("name");
		String surname = (String) json.get("surname");
		String username = (String) json.get("username");
		String password = (String) json.get("password");
		String matchPassword = (String) json.get("repeatPassword");
		
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setUsername(username);
		user.setPassword(password);
		user.setAddress("");
		user.setFriends(null);
		user.setRole(UserRole.USER);
		user.setActivated(false);
		
		if(password.equals(matchPassword)){
			System.out.println(password);
			System.out.println(matchPassword);
			sendConfirmationEmail(user);
			userRepo.save(user);
			return new ResponseEntity(HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
	}
	
	private void sendConfirmationEmail(User user){
		
		FileInputStream input;
		Properties properties = new Properties();
		try {
			input = new FileInputStream("config.properties");
			properties.load(input);
			
			Session session = Session.getInstance(properties, new MailAuthenticator());
			
		    MimeMessage msg = new MimeMessage(session);
		    msg.setSubject("Confirmation mail");
		    msg.setSentDate(new Date());
		    msg.setFrom(new InternetAddress("webmaster@isa.ftn.uns.ac.rs", false));
		    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getUsername(), false));

		    String text =
		    			"Dear " + user.getName() + " "
	                    + user.getSurname()
	                    + ", thank you for registering. Your confirmation link is: "
	                    + "<a href='http://localhost:8080/user/register/"
	                    + user.getUsername()+"'>" 
	                    + "Confirmation link</a>";

		    msg.setContent(text, "text/html; charset=utf-8");
		    
		    Transport.send(msg);
		    
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	
	private class MailAuthenticator extends Authenticator {

		public PasswordAuthentication getPasswordAuthentication()
	    {
			FileInputStream in;
			Properties prop = new Properties();
			try {
				in = new FileInputStream("googleplus.properties");
				prop.load(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	       return new PasswordAuthentication(prop.getProperty("username"), prop.getProperty("password"));
	    }
	}
	
	
}
