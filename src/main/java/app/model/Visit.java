package app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Visit implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVisit;
	
	
	private Restaurant restaurant;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Users", joinColumns = @JoinColumn(name = "idVisit"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	private Set<User> users;
	
	private HashMap<User, Boolean> mapOfConfirmations;
	
	private Date dateOfVisit;
	private int numOfHours;
	
	public int getIdVisit() {
		return idVisit;
	}
	public void setIdVisit(int idVisit) {
		this.idVisit = idVisit;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Date getDateOfVisit() {
		return dateOfVisit;
	}
	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}
	public int getNumOfHours() {
		return numOfHours;
	}
	public void setNumOfHours(int numOfHours) {
		this.numOfHours = numOfHours;
	}
	public HashMap<User, Boolean> getMapOfConfirmations() {
		return mapOfConfirmations;
	}
	public void setMapOfConfirmations(HashMap<User, Boolean> mapOfConfirmations) {
		this.mapOfConfirmations = mapOfConfirmations;
	}
	
}
