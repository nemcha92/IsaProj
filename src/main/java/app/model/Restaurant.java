package app.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Restaurant implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRestaurant;
	
	private String name;
	private String address;
	private String phone;
	private float rating;
	
	private HashMap<String, Float> ratings = new HashMap<String, Float>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Menu> menus;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="managerOf")
	private List<User> managers;

	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<User> getManagers() {
		return managers;
	}

	public void setManagers(List<User> managers) {
		this.managers = managers;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public HashMap<String, Float> getRatings() {
		return ratings;
	}

	public void setRatings(HashMap<String, Float> ratings) {
		this.ratings = ratings;
	}

	
}
