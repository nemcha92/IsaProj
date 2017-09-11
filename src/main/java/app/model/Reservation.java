package app.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReservation;
	
	private String dateTime;
	private double duration;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Restaurant restaurant;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private User creator;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Invitation> invitations;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Table> tables;
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public double getDuration() {
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
		String retVal = "\n\n"+dateTime+","+duration+", "+restaurant.getName()+", "+creator.getName()+"\n\nInvited:\n";
		
		for(Invitation inv : invitations){
			
			retVal += "("+inv.getUser().getName()+" "+inv.getUser().getSurname() +")\n";
			
		}
		return retVal;
	}
	
	public int getIdReservation() {
		return idReservation;
	}
	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	
	
}
