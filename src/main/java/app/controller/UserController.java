package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.User;
import app.repository.UserRepository;


@RestController
@RequestMapping("/resources/users")
@RepositoryEventHandler(User.class)
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping("/getLoggedUser")
	public User getLoggedUser(){
		
		User logUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logUser.setPassword(null);
		return logUser;
	}
	
	@RequestMapping(value = "/logout")
	public ResponseEntity logout(){
		
		SecurityContextHolder.getContext().setAuthentication(null);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/friends")
	public ResponseEntity getFriends(){
		
		
		User lUser = userRepo.findByUsername(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		
		if(lUser == null) 
			return null;
		
		return new ResponseEntity(lUser.getFriends(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/nonFriends")
	public ResponseEntity getNonFriends(){
		
		List<User> users = userRepo.findAll();
		List<User> retVal = new ArrayList<User>();
		
		User lUser = userRepo.findByUsername(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		
		if(lUser == null)
			return null;
		
		if(lUser.getFriends().isEmpty()){
			for(User u : users){
				if(!u.getUsername().equals(lUser.getUsername())){
					u.setPassword("");
					retVal.add(u);
				}
			}
		}else{
			for(User u : users){
				boolean ok = true;
				for(User luf : lUser.getFriends()){
					if(u.getUsername().equals(luf.getUsername()) || u.getUsername().equals(lUser.getUsername())){
						ok = false;
					}
				}
				if(ok){
					u.setPassword("");
					retVal.add(u);
				}
				
			}
		}
		return new ResponseEntity(retVal , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public ResponseEntity addFriend(@RequestBody User userToAdd){
		
		User lUser = userRepo.findByUsername(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		boolean ok = true;
		
		System.out.println("lUser is "+ lUser.getUsername());
		
		for(User u : lUser.getFriends()){
			if(userToAdd.getUsername().equals(u.getUsername())){
				ok = false;
			}
		}
		System.out.println(ok);
		System.out.println(userToAdd);
		System.out.println(userToAdd.getUsername());
		
		if(ok) 
			lUser.getFriends().add(userToAdd);
		
		userRepo.save(lUser);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
