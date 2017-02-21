package app.model;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservation;
	
	private String time;
	private String date;
	private Restaurant restaurant;
	private String hoursToStay;
	private User creator;
	private HashMap<User, Integer> friendsInvited;
	
}
