package app.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Invitation implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInvitation;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	private boolean isCofirmed;
	
	
	public Invitation(User user, boolean isCofirmed) {
		super();
		this.user = user;
		this.isCofirmed = isCofirmed;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public boolean isCofirmed() {
		return isCofirmed;
	}


	public void setCofirmed(boolean isCofirmed) {
		this.isCofirmed = isCofirmed;
	}
	
	

}
