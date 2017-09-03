package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Reservation;
import app.repository.ReservationRepository;


@RestController
@RequestMapping("/resources/reservation")
@RepositoryEventHandler(Reservation.class)
public class ReservationController {
	
	@Autowired
	ReservationRepository reservationRepo;
	
	

}
