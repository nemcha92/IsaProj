package app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Invitation implements Serializable {
	
	public enum Status { CONFIRMED, REJECTED, PENDING }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInvitation;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;
	private int rating;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Reservation reservation;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Meal> order;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public List<Meal> getOrder() {
		return order;
	}

	public void setOrder(List<Meal> order) {
		this.order = order;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	

}
