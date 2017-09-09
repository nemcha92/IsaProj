package app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
		
		User logUser = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
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
		
		User lUser = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		
		if(lUser == null) 
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		
		
		
		return new ResponseEntity(lUser.getFriends(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/nonFriends")
	public ResponseEntity getNonFriends(){
		
		List<User> users = userRepo.findAll();
		List<User> retVal = new ArrayList<User>();
		
		User lUser = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		
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
		
		User lUser = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		boolean ok = true;
		
		for(User u : lUser.getFriends()){
			if(userToAdd.getUsername().equals(u.getUsername())){
				ok = false;
			}
		}

		if(ok){
			userToAdd.setPassword("");
			lUser.getFriends().add(userToAdd);
		}
			
		userRepo.save(lUser);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/removeFriend", method = RequestMethod.POST)
	public ResponseEntity removeFriend(@RequestBody User friend){
		User lUser = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		
		Iterator<User> itu = lUser.getFriends().iterator();
		
		while(itu.hasNext()){
			if(itu.next().getUsername().equals(friend.getUsername())){
				itu.remove();
			}
		}
		userRepo.save(lUser);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateProfile", method = RequestMethod.POST)
	public ResponseEntity updateProfile(HttpServletRequest request){
		
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String address = request.getParameter("address");
		
		System.out.println(name +","+surname+","+address);
		
		
		if(name == "" || name == null || surname == "" || surname == null || address == "" || address ==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		User lUser = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		lUser.setName(name);
		lUser.setSurname(surname);
		lUser.setAddress(address);
		
		userRepo.save(lUser);
		
		return new ResponseEntity(lUser, HttpStatus.OK);
	}
	
}
