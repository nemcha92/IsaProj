package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.User;
import app.repository.UserRepository;
import app.security.UserDetailsServiceImpl;


@RestController
@RequestMapping("/user/login")
public class LoginController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity login(@RequestBody User user, HttpServletRequest request){
		User lUser = userRepo.findByUsername(user.getUsername());
		
		UserDetails userDetails;
		
		userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		
		if(lUser == null)
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		
		if(!lUser.getPassword().equals(user.getPassword()) || !lUser.isActivated())
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		request.getSession();
		
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResponseEntity(HttpStatus.OK);
	}

}
