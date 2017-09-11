package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Reservation;
import app.repository.ReservationRepository;
import app.repository.RestaurantRepository;


@RestController
@RequestMapping("/resources/reservation")
@RepositoryEventHandler(Reservation.class)
public class ReservationController {
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@RequestMapping(value="/{restaurantName}", method=RequestMethod.POST)
	public ResponseEntity makeReservation(@PathVariable("restaurantName") String restaurantName, HttpRequest request){
		
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
