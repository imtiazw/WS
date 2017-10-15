package com.airline.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.airline.models.Airplan;
import com.airline.models.Flight;
import com.airline.models.FlightDestination;
import com.airline.models.Passenger;

/**
 * Session Bean implementation class FlightService
 */
@Stateless
@LocalBean
public class FlightService {

	/**
	 * Default constructor.
	 */
	public FlightService() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "airline")
	private EntityManager em;

	public void addFlight(Flight flight, Airplan airplan) {
		em.persist(airplan);
		em.persist(flight);
	}

	public Flight getFlight(String flightId) {
		TypedQuery<Flight> qFlight = em.createNamedQuery("flight.selectAFlight", Flight.class);
		qFlight.setParameter("id", Integer.parseInt(flightId));
		Flight flight = qFlight.getSingleResult();
		
		return flight;
	}
	public List<Flight> getFlights() {

		TypedQuery<Flight> query = em.createQuery("SELECT f FROM Flight f", Flight.class);
		List<Flight> fList = query.getResultList();
		return fList;
	}

	public void addPassengerToFlight(String flightId, Passenger passenger) {
		
		Flight flight = em.find(Flight.class, new Integer(flightId));	
		flight.getPassengers().add(passenger);
	}
	
	public int updateDesitination(String from, String to) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Flight> update = cb.createCriteriaUpdate(Flight.class);
		Root<Flight> flight = update.from(Flight.class);
		update.set("destination", FlightDestination.valueOf(to));
		update.where(cb.equal(flight.get("destination"), FlightDestination.valueOf(from)));
		
		return this.em.createQuery(update).executeUpdate();
	}
	
	public int deleteRecord(String flightId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Flight> delete = cb.createCriteriaDelete(Flight.class);
		Root<Flight> flight = delete.from(Flight.class);
		delete.where(cb.equal(flight.get("id"), new Integer(flightId)));
		
		return this.em.createQuery(delete).executeUpdate(); 
	}
	
	
}
