package com.airline.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Flight
 *
 */
@NamedQuery(name= "flight.selectAFlight", query = "SELECT f FROM Flight f WHERE f.id = :id")
@Entity
@XmlRootElement(name="flight")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flight implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	public Flight() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@XmlAttribute(required=true)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@XmlElement(required=true)
	private FlightDestination origin;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@XmlElement(required=true)
	private FlightDestination destination;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	@XmlElement(required=true)
	private Date time;
	
	@DecimalMin(value = "0.00")
	@NotNull
	@XmlElement(required=true)
	private Integer price;
	
	@OneToOne
	@JoinColumn(name = "airplan_fk")
	@XmlElement(required=true)
	private Airplan airplan;
	
	@OneToMany(mappedBy = "flight")
	@XmlElement(required=true)
	private List<Pilot> pilots;
	
	@ManyToMany(mappedBy="flights")
	@XmlElement(required=true)
	private List<Passenger> passengers;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FlightDestination getOrigin() {
		return origin;
	}

	public void setOrigin(FlightDestination origin) {
		this.origin = origin;
	}

	public FlightDestination getDestination() {
		return destination;
	}

	public void setDestination(FlightDestination destination) {
		this.destination = destination;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	
	public Airplan getAirplan() {
		return airplan;
	}

	public void setAirplan(Airplan airplan) {
		this.airplan = airplan;
	}

	public List<Pilot> getPilots() {
		return pilots;
	}

	public void setPilots(List<Pilot> pilots) {
		this.pilots = pilots;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", origin=" + origin + ", destination=" + destination + ", time=" + time
				+ ", price=" + price + ", airplan=" + airplan + ", pilots=" + pilots + ", passengers=" + passengers
				+ "]";
	}
	
   
}
