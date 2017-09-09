package app.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Invitation implements Serializable {
	
	public enum Status { CONFIRMED, REJECTED, PENDING }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInvitation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;
	private int rating;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
