package app.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Restaurant implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRestaurant;
	
	private String name;
	private String address;
	private String phone;
	private double distance;
	private float rating;
	
	private int numberOfRows;
	private int numberOfColumns;
	
	private HashMap<String, Float> ratings = new HashMap<String, Float>();
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "idUser")
	private User manager;
	
	@OneToMany(mappedBy="restaurant", fetch = FetchType.EAGER)
	private List<Menu> menus;
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Table> tables;

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

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	
	
}
