package app;


import java.util.ArrayList;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import app.model.Meal;
import app.model.Menu;
import app.model.Reservation;
import app.model.Restaurant;
import app.model.User;
import app.model.UserRole;
import app.repository.MealRepository;
import app.repository.MenuRepository;
import app.repository.RestaurantRepository;
import app.repository.UserRepository;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	MealRepository mealRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

    private static Class<Application> applicationClass = Application.class;
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
	
	@Bean
	public ServletRegistrationBean h2servletRegistration() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
	    registration.addUrlMappings("/console/*");
	    return registration;
	}
	
	@Override
	public void run(String... strings) throws Exception {
		
		addUsers();
		addMealsToMenus();
		addRestaurants();
	}
	
	private void addUsers(){
		User u = new User();
		u.setActivated(true);
		u.setAddress("Bulevar Evrope 42");
		u.setManagerOf(null);
		u.setName("Nemanja");
		u.setPassword("n");
		u.setRole(UserRole.ADMIN);
		u.setSurname("Milutinovic");
		u.setUsername("n@hot.com");
		
		User u1 = new User();
		u1.setActivated(true);
		u1.setAddress("Bulevar Evrope 44");
		u1.setManagerOf(null);
		u1.setName("Pera");
		u1.setPassword("pera");
		u1.setRole(UserRole.ADMIN);
		u1.setSurname("Peric");
		u1.setUsername("pera@hotmail.com");
		
		User u2 = new User();
		u2.setActivated(true);
		u2.setAddress("Kisacka 91b");
		u2.setManagerOf(null);
		u2.setName("Marko");
		u2.setPassword("mare");
		u2.setRole(UserRole.ADMIN);
		u2.setSurname("Markovic");
		u2.setUsername("mare@hotmail.com");
		
		
		
		userRepo.save(u);
		userRepo.save(u1);
		userRepo.save(u2);
		
		u1.setPassword("");
		u.getFriends().add(u1);
		userRepo.save(u);
		
	}
	
	private void addRestaurants(){
		
		Restaurant res = new Restaurant();
		res.setAddress("Adresa 1");
		res.setManagers(null);
		res.setMenus(null);
		res.setName("Restoran 1");
		res.setRating(1);
		res.setMenus(new ArrayList<Menu>());
		res.setManagers(new ArrayList<User>());
		res.setReservations(new ArrayList<Reservation>());
		
		res.getMenus().add(menuRepo.findByName("Hrana"));
		res.getMenus().add(menuRepo.findByName("Pice"));
		res.getManagers().add(userRepo.findByUsername("n@hot.com"));
		
		restaurantRepo.save(res);
		
		Restaurant res1 = new Restaurant();
		res1.setAddress("Adresa 2");
		res1.setManagers(null);
		res1.setMenus(null);
		res1.setName("Restoran 2");
		res1.setRating(4);
		res1.setMenus(new ArrayList<Menu>());
		res1.setManagers(new ArrayList<User>());
		res1.setReservations(new ArrayList<Reservation>());
		
		restaurantRepo.save(res1);
		
		Restaurant res2 = new Restaurant();
		res2.setAddress("Adresa 3");
		res2.setManagers(null);
		res2.setMenus(null);
		res2.setName("Restoran 3");
		res2.setRating(3);
		res2.setMenus(new ArrayList<Menu>());
		res2.setManagers(new ArrayList<User>());
		res2.setReservations(new ArrayList<Reservation>());
		
		restaurantRepo.save(res2);
	}
	
	
	private void addMealsToMenus(){
		
		Meal m1 = new Meal("Pljeskavica", "Obicna pleskavica 200g", "220");
		Meal m2 = new Meal("Hamburger", "200g", "220");
		Meal m3 = new Meal("10 cevapa", "10 x 20g", "300");
		Meal m4 = new Meal("Pomfrit", "100g", "150");
		
		mealRepo.save(m1);
		mealRepo.save(m2);
		mealRepo.save(m3);
		mealRepo.save(m4);
		
		Menu menu1 = new Menu();
		menu1.setName("Hrana");
		menu1.getMeals().add(m1);
		menu1.getMeals().add(m2);
		menu1.getMeals().add(m3);
		menu1.getMeals().add(m4);
		
		menuRepo.save(menu1);
		
		Meal m5 = new Meal("Pivo", "Zajecarsko 0.5l toceno", "200");
		Meal m6 = new Meal("Pivo", "tuborg 0.5l", "190");
		Meal m7 = new Meal("Koka kola", "0.25l", "150");
		Meal m8 = new Meal("Espreso", "Produzeni sa mlekom", "130");
		
		mealRepo.save(m5);
		mealRepo.save(m6);
		mealRepo.save(m7);
		mealRepo.save(m8);
		
		Menu menu2 = new Menu();
		menu2.setName("Pice");
		menu2.getMeals().add(m5);
		menu2.getMeals().add(m6);
		menu2.getMeals().add(m7);
		menu2.getMeals().add(m8);
		
		menuRepo.save(menu2);
		
	}
	
	
	
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
     
       return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/app/errorpages/401.html");
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/app/errorpages/403.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/app/errorpages/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/app/errorpages/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page, error403Page);
       });
    }

}