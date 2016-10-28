package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.User;
import app.repository.UserRepository;


@RestController
@RequestMapping("/user/login")
public class LoginController {
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity login(@RequestBody User user){
		
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		User lUser = userRepo.findByUsername(user.getUsername());
		
		if(lUser == null)
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		
		if(!lUser.getPassword().equals(user.getPassword()) || !lUser.isActivated())
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		
		Authentication auth = new UsernamePasswordAuthenticationToken(lUser, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResponseEntity(HttpStatus.OK);
	}

}
