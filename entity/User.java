/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */
package com.springjpa.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; // All of these classes must be installed. 1. 



@Entity
@Table(name = "USERS")
@Getter // Both of them by Lombok. It is a library which facilitates many tedious tasks and reduces Java source code verbosity.
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long idUser;
	
	@Column(name = "DNI")
	private String dni;
	
	@Column(name = "FULL_NAME")
	private String firstAndSecondName;

	@Column(name = "PASSWORD")
	private String password;
	
	//@JsonIgnore
	//@JsonIgnore property helps to avoid an infinite recursion spring it is a Jackson issues that it was sorted out with that annotation
	//It is important if it is marked the http request in controller endpoint won¡t be read correctly and request body in Json format will fail.
	@JsonManagedReference
	//Using JsonManagedReference cause the following error:
	//com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
	//That's Jackson requires all field and method have to be public.
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "DIRECTION_ID")
	private Direction direction;
	
	@OneToMany(mappedBy = "user")
	private List<CellPhone> cellPhoneList;
	
	//**********

/* All of these are provided by Lombok Annotations @Data or @Getters/@Setters, @AllArgsConstructor, @NoArgsConstructor
	public User() {

	}

	public User(Long idUser, String dni, String firstAndSecondName, String password, Direction direction,
			List<CellPhone> cellPhoneList) {
		this.idUser = idUser;
		this.dni = dni;
		this.firstAndSecondName = firstAndSecondName;
		this.password = password;
		this.direction = direction;
		this.cellPhoneList = cellPhoneList;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getFirstAndSecondName() {
		return firstAndSecondName;
	}

	public void setFirstAndSecondName(String firstAndSecondName) {
		this.firstAndSecondName = firstAndSecondName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public List<CellPhone> getCellPhoneList() {
		return cellPhoneList;
	}

	public void setCellPhoneList(List<CellPhone> cellPhoneList) {
		this.cellPhoneList = cellPhoneList;
	}
*/
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", dni=" + dni + ", firstAndSecondName=" + firstAndSecondName + ", password="
				+ password + ", direction=" + direction.getIdDirection() + ", cellPhoneList=" + cellPhoneList + "]";
	}
}
