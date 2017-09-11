package app.model;

import java.util.List;

public class ReservationWrapper {
	
	private Reservation reservation;
	private List<User> friends;
	
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	
	

}
