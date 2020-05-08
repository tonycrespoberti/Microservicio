/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */
package com.springjpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;

//import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "DIRECTIONS")
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
//@JsonSerialize
@Data
public class Direction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIRECTION_ID")
	private Long idDirection;
	
	@Column(name = "STREET")
	private String street;
	
	@Column(name = "NUMBER")
	private String number;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "ZIPCODE")
	private String zipCode;
	 
	@Column(name = "STATE")
	private String state;

	//@JsonIgnore //for json infinite recursion spring it is a Jackson issues that it was sorted out with that annotation
	//@Cascade({CascadeType.SAVE_UPDATE}) //To avoid error persistency detach entity using  javax.persistence.CascadeType, we replace it for org.hibernate.annotations.Cascade
	@JsonBackReference
	@OneToOne(mappedBy = "direction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;

	/*
	public Direction() {
		
	}

	public Direction(Long idDirection, String street, String number, String location, String zipCode, String state,
			User user) {
		this.idDirection = idDirection;
		this.street = street;
		this.number = number;
		this.location = location;
		this.zipCode = zipCode;
		this.state = state;
		this.user = user;
	}

	public Long getIdDirection() {
		return idDirection;
	}

	public void setIdDirection(Long idDirection) {
		this.idDirection = idDirection;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Direction [idDirection=" + idDirection + ", street=" + street + ", number=" + number + ", location="
				+ location + ", zipCode=" + zipCode + ", state=" + state + "]";
	}
	*/
}
