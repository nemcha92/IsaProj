package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Restaurant;
import app.model.User;
import app.repository.RestaurantRepository;

@RestController
@RequestMapping("/resources/restaurant")
@RepositoryEventHandler(Restaurant.class)
public class RestaurantsController {
	
	@Autowired
	RestaurantRepository restRepo;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity get(){
		
		return new ResponseEntity(restRepo.findAll(), HttpStatus.OK);
	}
	
	/*@RequestMapping(value="/{name}/managers", method = RequestMethod.GET)
	public ResponseEntity getManagers(@PathVariable("name") String name){
		
		List<User> managers = new ArrayList<User>();
		System.out.println(name);
		Restaurant r = restRepo.findByName(name);
		for(User u : r.getManagers()){
			managers.add(u);
			System.out.println(u.getName() + " "+u.getSurname());
		}
		
		return new ResponseEntity(managers, HttpStatus.OK);
	}*/
}
