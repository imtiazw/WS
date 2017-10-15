package com.airline.webservices.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.airline.models.Airplan;
import com.airline.models.Flight;
import com.airline.models.FlightDestination;
import com.airline.services.FlightService;

@Path("flights")
public class RestFlight {

	
	@EJB
	FlightService fs;
	
	@GET //method type 
	@Produces(MediaType.APPLICATION_JSON) //produces the response in JSON
	public List<Flight> getFlightList(){
		return fs.getFlights(); 
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML) //produces the response in XML
	public Flight oneResult(@PathParam("id") String flightId) {
		return fs.getFlight(flightId);
	}
	
	@POST
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED) //getting parameters from form
	@Produces(MediaType.APPLICATION_JSON) //getting parameter from form in JSON
	public Flight flightFromForm(@FormParam("id") String flightId) {
		return fs.getFlight(flightId);
	}
	
	// inserting flight when getting parameters from Form
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void persistFlight(@FormParam("seating") String seating,
		@FormParam("manufacturer") String manufacturer, @FormParam("model") String model,
		@FormParam("destination") String destination, @FormParam("flight_from") String flight_from,
		@FormParam("price") String price, @FormParam("flight_time") String flight_time) {
		Flight flight = new Flight();
		Airplan airplan = new Airplan();
		
		airplan.setCapacity(new Integer(seating));
		airplan.setManufacteror(manufacturer);
		airplan.setModel(model);		
		
		flight.setAirplan(airplan);
		flight.setDestination(FlightDestination.valueOf(destination.toUpperCase()));
		flight.setOrigin(FlightDestination.valueOf(flight_from.toUpperCase()));
		
		flight.setPrice(new Integer(price));
		Date date = null;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.US).parse(flight_time);
			System.out.println(date.toString());
		} catch (ParseException e) {
			// TODO Handle the following exception with AJAX (invalid format)
			e.printStackTrace();
		} 
		flight.setTime(date);
		fs.addFlight(flight, airplan);
	}
	
	//updating records in database
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public int updateRecord(@FormParam("from") String from, @FormParam("to") String to) {
		return fs.updateDesitination(from, to);
	}
	
	//deleting records in database
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public int deleteRecord(@FormParam("id") String flightId) {
		return fs.deleteRecord(flightId);
	}
}
