package app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Table {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTable;
	
	private boolean isAvailable;
	private int numberOfChairs;
	
	@NotNull
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Restaurant restaurant;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Reservation> reservations;
	
	public Table(){
		
	}
	
	public Table(boolean isAvailable, int numberOfChairs, Restaurant restaurant) {
		super();
		this.isAvailable = isAvailable;
		this.numberOfChairs = numberOfChairs;
		this.restaurant = restaurant;
	}

	public int getIdTable() {
		return idTable;
	}

	public void setIdTable(int idTable) {
		this.idTable = idTable;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getNumberOfChairs() {
		return numberOfChairs;
	}

	public void setNumberOfChairs(int numberOfChairs) {
		this.numberOfChairs = numberOfChairs;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	

}
