package app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import app.model.Invitation;
import app.model.Meal;
import app.model.Menu;
import app.model.Reservation;
import app.model.Restaurant;
import app.model.User;
import app.model.UserRole;
import app.repository.InvitationRepository;
import app.repository.MealRepository;
import app.repository.MenuRepository;
import app.repository.ReservationRepository;
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
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	InvitationRepository invRepo;
	
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
		addRestaurants();
		addMealsAndMenus();
		addMealsToMenus();
		addMenusToRestaurants();
		addManagersToRestaurants();
		//addReservations();
		
		printTest();
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
		u.setUsername("n@mail.com");
		
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
		
		
		userRepo.save(u1);
		userRepo.save(u2);
		
		u1.setPassword("");
		u.getFriends().add(u1);
		userRepo.save(u);
		
	}
	
	private void addRestaurants(){
		
		Restaurant res = new Restaurant();
		res.setAddress("Adresa 1");
		res.setName("Restoran1");
		res.setPhone("021/777-888");
		res.setRating(5);
		res.setMenus(new ArrayList<Menu>());
		
		restaurantRepo.save(res);

		Restaurant res1 = new Restaurant();
		res1.setAddress("Adresa 2");
		res1.setName("Restoran2");
		res1.setPhone("019/444-555");
		res1.setRating(4);
		res1.setMenus(new ArrayList<Menu>());
		res1.setManager(null);
		
		restaurantRepo.save(res1);
		
		Restaurant res2 = new Restaurant();
		res2.setAddress("Adresa 3");
		res2.setName("Restoran3");
		res2.setPhone("019/111-5515");
		res2.setRating(3);
		res2.setMenus(new ArrayList<Menu>());
		res2.setManager(null);
		
		restaurantRepo.save(res2);
	}
	
	
	private void addMealsAndMenus(){
		
		Meal m1 = new Meal("Pljeskavica", "Obicna pleskavica 200g", "220");
		Meal m2 = new Meal("Hamburger", "200g", "220");
		Meal m3 = new Meal("10 cevapa", "10 x 20g", "300");
		Meal m4 = new Meal("Pomfrit", "100g", "150");
		
		mealRepo.save(m1);
		mealRepo.save(m2);
		mealRepo.save(m3);
		mealRepo.save(m4);
		
		Menu menu1 = new Menu();
		menu1.setName("Hrana1");
		
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
		menu2.setName("Pice1");

		menuRepo.save(menu2);
		
	}
	
	private void addMealsToMenus(){
		
		Menu menu1 = menuRepo.findByName("Hrana1");
		
		List<Meal> meals = new ArrayList<Meal>();
		
		for(int i = 0; i < 4; ++i){
			Meal m1 = mealRepo.findOne(i+1);
			m1.setMenu(menu1);
			meals.add(m1);
			mealRepo.save(m1);
		}
		
		menu1.setMeals(meals);
		menuRepo.save(menu1);
		
		Menu menu2 = menuRepo.findByName("Pice1");
		meals.clear();
		
		for(int i = 4; i < 7; ++i){
			Meal m1 = mealRepo.findOne(i+1);
			m1.setMenu(menu2);
			meals.add(m1);
			mealRepo.save(m1);
		}
		
		menu2.setMeals(meals);
		menuRepo.save(menu2);
		
		
	}
	
	private void addMenusToRestaurants(){
		
		Restaurant res = restaurantRepo.findByName("Restoran1");
		Menu menu1 = menuRepo.findByName("Hrana1");
		Menu menu2 = menuRepo.findByName("Pice1");
		
		menu1.setRestaurant(res);
		menu2.setRestaurant(res);
		
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(menuRepo.findByName("Hrana1"));
		menus.add(menuRepo.findByName("Pice1"));
		res.setMenus(menus);
		
		menuRepo.save(menu1);
		menuRepo.save(menu2);
		restaurantRepo.save(res);
		
	}
	
	private void addManagersToRestaurants(){
		
		User manager = userRepo.findByUsername("n@mail.com");
		Restaurant res = restaurantRepo.findByName("Restoran1");
		
		List<Restaurant> managerOf = new ArrayList<Restaurant>();
		managerOf.add(res);
		manager.setManagerOf(managerOf);
		
		res.setManager(manager);
		
		userRepo.save(manager);
		restaurantRepo.save(res);
	}
	
	private void addReservations(){
		
		Reservation reserv = new Reservation();
		reserv.setCreator(userRepo.findByUsername("n@mail.com"));
		reserv.setDate("12.09.17");
		reserv.setDuration(5);
		
		List<Invitation> invitations = new ArrayList<Invitation>();
		
		Invitation inv1 = new Invitation(userRepo.findByUsername("pera@hotmail.com"), false);
		Invitation inv2 = new Invitation(userRepo.findByUsername("mare@hotmail.com"), true);
		
		invRepo.save(inv1);
		invRepo.save(inv2);
		
		invitations.add(inv1);
		invitations.add(inv2);
		reserv.setInvitations(invitations);
		
		reserv.setRestaurant(restaurantRepo.findOne(1));
		reserv.setTime("18:30");
		reservationRepo.save(reserv);
		System.out.println(reserv);
	}
	
	private void printTest(){
		printRestaurant("Restoran1");
	}
	
	
	private void printRestaurant(String restaurant){
		
		System.out.println("** PRINT RESTAURANT**");
		Restaurant r = restaurantRepo.findByName(restaurant);
		System.out.println("->"+r.getName());
		System.out.println("\n*Menus:*");
		for(Menu m : r.getMenus()){
			System.out.println("-->"+m.getName());
			for(Meal mm : m.getMeals()){
				System.out.println("--->"+mm.getName());
			}
		}
		
		System.out.println("*Managers:*");
		System.out.println(r.getManager().getName()+" "+r.getManager().getSurname());
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