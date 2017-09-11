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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy="reservation", fetch = FetchType.EAGER)
	private List<Invitation> invitations;
	
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Table> tables;
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public List<Invitation> getInvitations() {
		return invitations;
	}
	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}
	
	@Override
	public String toString() {
		String retVal = "\n\n"+dateTime+","+duration+", "+restaurant.getName()+", "+user.getName()+"\n\nInvited:\n";
		
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
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
