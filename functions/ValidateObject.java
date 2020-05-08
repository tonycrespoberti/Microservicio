package com.springjpa.functions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.springjpa.entity.Direction;
import com.springjpa.entity.StatusTransaction;
import com.springjpa.entity.User;
import com.springjpa.interfaces.IValidate;

import lombok.Getter;


/**
 * @author Tony Crespo - tonycrespo@outlook.com
 * Super class to validate Objects (User, Direction, etc) and to handle the final result of transactions like insert and update. 
 * Codes and messages are generated and show object info if it is needed)
 * 
 */
@Component
@Scope("prototype")
@Getter
public class ValidateObject implements IValidate {
	
	static final int MAX_LENGTH_DNI = 9;

	@Autowired
	private StatusTransaction statusTransaction;
	
	private Map<Integer, String> resultTransaction = new HashMap<>();
	
	//***********
	
	//Load Status, is for add/update status information object about the transaction result (insert/update)
	@Override
	public StatusTransaction loadStatus(Integer codeTransaction, String messageTransaction, Object objectTransaction) {

		resultTransaction.put(codeTransaction, messageTransaction);
		
		//This object will be sent for each http request (rest client)
		statusTransaction.setResultTransaction(resultTransaction);
		statusTransaction.setGenericObject(objectTransaction);
		
		return statusTransaction;
	}

	
	//Delete Status Transaction
	@Override
	public void deleteStatus() {
		
		resultTransaction.clear();
		
		statusTransaction.setResultTransaction(null);
		
		loadStatus(-1, "[INFO]: Iniciado proceso de validación de los datos del nuevo Usuario.", null);
			
		statusTransaction.setGenericObject(null);
	}
	

	//Show Status. Print by console the Status Transaction content
	//It is the same response that will receive a rest client (hhttp Response) but by console
	@Override
	public void showStatus(Map<Integer, String> resultTransaction, Object objectTransaction) {
		 
			System.out.println("Estado del Request realizado: " + "Códigos y Mensajes: " + resultTransaction + " Objeto involucrado: " + objectTransaction);
	}

	//Validate Object Attributes. 
	//Return true is validation process is completed successfully or false in case it be received an invalid attribute
	@Override
	public boolean validateAttributes(Object typeObject, Integer typeTransaction) {

		boolean result = false;
		
		//OneToOne relations between User and Direction. This data has to be sent for adding or updating users.
		//If it is for create a new user we have to receive every Direction attribute from client.
		//For updating we validate if the id Direction is already exist
		
		if (typeObject instanceof User) {

			result = ValidateUser((User) typeObject, typeTransaction); // Cast to User Object

		}

		if (typeObject instanceof Direction) {

			result = validateDirection((Direction) typeObject, typeTransaction);
		}
		
		return result;
	}
	
	
	//Validation User object
	private boolean ValidateUser(User user, Integer typeTransaction) {
		
		boolean validationOK = false;
		
		//To ensure status transaction info is empty
		deleteStatus();
		
		if (user.getIdUser() !=null) { 
			
			if (typeTransaction == 0) { //Creating process must receive IdUser = null
			
				loadStatus(1, "[ERROR]: El Id del Usuario es inválido. No debe ser especificado.", user);
			
			}else {
				
				if (user.getIdUser() == 0) {
					
					loadStatus(1, "[ERROR]: El Id del Usuario es inválido. No debe ser cero (0).", user);
				}
			}
		}
		
		if (user.getDni() != null) {
			
			if (user.getDni().length() != MAX_LENGTH_DNI) {
				
				loadStatus(3, "[ERROR]: La longitud del DNI es inválida.", user);
			
			}else {
			
				//Is verified if the first eights chars are numbers
				for (int i = 0; i < MAX_LENGTH_DNI - 1; i++) {
				
					if (!Character.isDigit(user.getDni().charAt(i))) {
					
						loadStatus(4, "[ERROR]: DNI inválido, los primeros 8 caracteres deben ser numéricos.", user);
					
					}
				}
				//If the last position is not a letter
				if (Character.isDigit(user.getDni().charAt(MAX_LENGTH_DNI - 1))) {
						
					loadStatus(5, "[ERROR]: DNI inválido, el último carcater debe ser una letra.", user);
				}
			}
			
		}else {
			
			loadStatus(2, "[ERROR]: El DNI del Usuario es inválido.", user);
			
		}
		
		//Code = 6 is defined in UserService layer when is validated via DAO layer.
		
		//StringUtils is an Apache Common Lang Library. It has been configured in the classpath
		if (StringUtils.isEmpty(user.getFirstAndSecondName())) {
			
			loadStatus(7, "[ERROR]: Nombre/Apellido inválido.", user);
		}
		
		if (StringUtils.isEmpty(user.getPassword())) {
			
			loadStatus(8, "[ERROR]: Password inválido", user);
		}
		
		
		if (user.getDirection().getIdDirection() !=null) { 
			
			if (typeTransaction == 0) { //Creating process must receive IdUser = null
			
				loadStatus(9, "[ERROR]: El Id de la Dirección es inválido. No debe ser especificado.", user);
			
			}else {
				
				if (user.getDirection().getIdDirection() == 0) {
					
					loadStatus(9, "[ERROR]: El Id de la Dirección es inválido. No debe ser cero (0).", user);
				
				}else {
					
					validateDirection(user.getDirection(), typeTransaction);
				}
			}
		}
		
		if (statusTransaction.getResultTransaction().size() == 1) { //At least there is a default code/message of transaction control.
				
			validationOK = true;
		}
		
		return validationOK;
	}
	
	
	//Validate Directio object
	private boolean validateDirection(Direction direction, Integer typeTransaction) {
		
		boolean validationOK = false;
		
		//To ensure status transaction info is empty
		deleteStatus();
		
		if (direction.getIdDirection() !=null) { //Create process must receive IdDirection = null
			
			if (typeTransaction == 0) { //Creating process must receive IdUser = null
				
				loadStatus(1, "[ERROR]: El Id de la Dirección es inválido. No debe ser especificado.", direction);
			
			}else {
				
				if (direction.getIdDirection() == 0) {
					
					loadStatus(1, "[ERROR]: El Id de la Dirección es inválido. No debe ser cero (0).", direction);
				}
			}
		}
		
		//StringUtils is an Apache Common Lang Library. It has been configured in the classpath
		if (StringUtils.isEmpty(direction.getStreet())) {
			
			loadStatus(2, "[ERROR]: La Calle/Avenida es inválida.", direction);
		}
		
		if (StringUtils.isEmpty(direction.getNumber())) {
			
			loadStatus(3, "[ERROR]: El Número de la Dirección es inválido", direction);
		}
		
		if (StringUtils.isEmpty(direction.getLocation())) {
			
			loadStatus(4, "[ERROR]: La Localidad de la Dirección es inválida", direction);
		}
		
		if (StringUtils.isEmpty(direction.getZipCode())) {
			
			loadStatus(5, "[ERROR]: El Código Postal de la Dirección es inválido", direction);
		}
		
		if (StringUtils.isEmpty(direction.getState())) {
			
			loadStatus(6, "[ERROR]: El Estado de la Dirección es inválido", direction);
		}
		
		if (statusTransaction.getResultTransaction().size() == 1) { //At least there is a default code/message of transaction control.
			
			validationOK = true;
		}
		
		return validationOK;
	}
}
