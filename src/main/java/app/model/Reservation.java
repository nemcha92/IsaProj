package app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservation;
	
	private String date;
	private String time;
	private int duration;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Restaurant restaurant;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User creator;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Invitation> invitations;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public List<Invitation> getInvitations() {
		return invitations;
	}
	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}
	
	@Override
	public String toString() {
		String retVal = "\n\n"+date+", "+time+","+duration+", "+restaurant.getName()+", "+creator.getName()+"\n\nInvited:\n";
		
		for(Invitation inv : invitations){
			
			retVal += "("+inv.getUser().getName()+" "+inv.getUser().getSurname() +" : "+inv.isCofirmed()+")\n";
			
		}
		
		return retVal;
	}
	
	
	
}
