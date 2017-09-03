package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Restaurant;
import app.repository.RestaurantRepository;

@RestController
@RequestMapping("/resources/restaurant")
@RepositoryEventHandler(Restaurant.class)
public class RestaurantsController {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity get(){
		return new ResponseEntity(restaurantRepository.findAll(), HttpStatus.OK);
	}
}
