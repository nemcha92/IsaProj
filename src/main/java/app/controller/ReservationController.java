package app.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Invitation;
import app.model.Reservation;
import app.model.Restaurant;
import app.model.Table;
import app.model.User;
import app.repository.InvitationRepository;
import app.repository.ReservationRepository;
import app.repository.RestaurantRepository;
import app.repository.TableRepository;
import app.repository.UserRepository;


@RestController
@RequestMapping("/resources/reservation")
@RepositoryEventHandler(Reservation.class)
public class ReservationController {
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TableRepository tableRepo;
	
	@Autowired
	InvitationRepository invitationRepo;
	
	@RequestMapping(value="/{username}/{restaurantName}", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity makeReservation(@PathVariable("username") String username, @PathVariable("restaurantName") String restaurantName, @RequestBody String body){
		
		JSONObject json = new JSONObject (body); 
		JSONObject reservationJSON = (JSONObject) json.get("reservation");
		Reservation reservation = new Reservation();
		
		reservation.setDateTime(reservationJSON.getString("time"));
		reservation.setDuration(reservationJSON.getDouble("duration"));
		
		//tables
		JSONArray arrayTables = reservationJSON.getJSONArray("tables");
		List<Table> tablesForReservation = new ArrayList<Table>();
		
		if(arrayTables.length() > 0){
			for(int i = 0 ; i < arrayTables.length() ; i++){
				tablesForReservation.add(tableRepo.findOne(arrayTables.getJSONObject(i).getInt("idTable")));
			}
		}
		
		reservation.setTables(tablesForReservation);
		
		//friends
		List<Invitation> invitations = new ArrayList<Invitation>();
		JSONArray arrayFriends = json.getJSONArray("friends");
		
		if(arrayFriends.length() > 0){
			for(int i = 0 ; i < arrayFriends.length() ; i++){
				Invitation inv = new Invitation();
				User friend = userRepo.findByUsername(arrayFriends.getJSONObject(i).getString("username"));
				inv.setUser(friend);
				invitations.add(inv);
				invitationRepo.save(inv);
			}
		}
		
		reservation.setInvitations(invitations);
		
		//user
		User crU = userRepo.findByUsername(username);
		reservation.setUser(crU);
		
		//restaurant
		Restaurant res = restaurantRepo.findByName(restaurantName);
		reservation.setRestaurant(res);
		
		reservationRepo.save(reservation);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	
}
