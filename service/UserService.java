/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */
package com.springjpa.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.StatusTransaction;
import com.springjpa.entity.User;
import com.springjpa.functions.ValidateObject;
import com.springjpa.repository.ICellPhone;
import com.springjpa.repository.IUser;


@Service
public class UserService {

	@Autowired
	private IUser userDao;
	
	@Autowired
	private ICellPhone cellPhoneDao;
	
	@Autowired
	private ValidateObject validateObject;
	
	//****
	

	/**
	 * Method for adding new users. Return certain code errors. See validate method for details. 
	 * @param user Object
	 * @return StatusTransaction object who has code/messages of transaction result and object used
	 */
	@Transactional
	public StatusTransaction addUser(User user) {
		
		validateObject.deleteStatus();
		
		if (!validateObject.validateAttributes(user, 0)) {
			
			return validateObject.getStatusTransaction();
			
		}
		
		//In service layer will validate DNI if exist given that here working service-DAO interface.
		//That's reason why it is not part of ValidateOBject class
		Optional<User> optUser = userDao.findByDni(user.getDni());
		
		if (optUser.isPresent()) {
			
			validateObject.loadStatus(6, "[ERROR]: El DNI ya existe. Favor verificar", user);
			
		}else {
		
			User auxUser = new User();
			
			auxUser.setIdUser(0L);
			
			user.setIdUser(0L);
			
			Optional<User> saveUser = Optional.of(userDao.save(user));
			
			if (optUser.isPresent()) {
				
				validateObject.loadStatus(0, "[OK]: El Nuevo Usuario ha sido creado correctamente", saveUser);
					
				return validateObject.getStatusTransaction();
					
			}else {

				validateObject.loadStatus(10, "[ERROR]: Ha habido un fallo intentando crear al nuevo Usuario. Intente de Nuevo o contactar con Soporte.", saveUser);
			}	
			
		}
		return validateObject.getStatusTransaction();
	}
	
	/**
	 * DELETE User
	 * @param an User object
	 * @return StatusTransaction object who has code/messages of transaction result and object used
	 */
	@Transactional
	public StatusTransaction deleteUser(String dni) {
		
		validateObject.deleteStatus();
		
		Optional<User> optUser = userDao.findByDni(dni);
		
		if (optUser.isPresent()){
			
			userDao.deleteByDni(dni);
			
			validateObject.loadStatus(0, "[OK]: EL Usuario con DNI: " + optUser.get().getDni() + " a sido eliminado correctamente.", null);
			
		}else {
		
			validateObject.loadStatus(1, "[ERROR]: NO EXISTE un Usuario con el DNI indicado, favor confirmar el documento de identidad.", null);
		
		}
		
		return validateObject.getStatusTransaction();
	}
	
	
	/**
	 * Method to update an User object
	 * @param An User object
	 * @return StatusTransaction object who has code/messages of transaction result and object used
	*/
	@Transactional
	public StatusTransaction updateUser(User user) {
		
		validateObject.deleteStatus();
		
		if (!validateObject.validateAttributes(user, 1)) {
			
			return validateObject.getStatusTransaction();
		}
		
		Optional<User> optUser = userDao.findByDni(user.getDni());
		
		if (optUser.isPresent()) {
			
			User auxUser = new User();
			
			Optional<User> saveUser = Optional.of(userDao.save(user));
			
			if (optUser.isPresent()) {
				
				validateObject.loadStatus(0, "[OK]: El Usuario ha sido actualizado correctamente", saveUser);
					
				return validateObject.getStatusTransaction();
					
			}else {

				validateObject.loadStatus(10, "[ERROR]: Ha habido un fallo intentando actualizar los datos del Usuario. Intente de Nuevo o contactar con Soporte.", saveUser);
			}	
			
		}else {
		
			validateObject.loadStatus(6, "[ERROR]: El DNI NO existe. Favor verificar", user);
		}
		
		return validateObject.getStatusTransaction();
	}

	
	/**
	 * List All User
	 * @return List of object users
	 */
	public List<User> listUsers(){
		
		return userDao.findByJoinUserDirection();
	}
	
	
	//Find By DNI
	public StatusTransaction findUser(String dni) {
		
		validateObject.deleteStatus();
	
		if (!StringUtils.isEmpty(dni)) {
		
			Optional<User> optUser = userDao.findByDni(dni);
			
			if (optUser.isPresent()){
				
				validateObject.loadStatus(0, "[OK]: Búsqueda exitosa.", optUser.get());
				
			}else {
			
				validateObject.loadStatus(0, "[OK]:EL Usuario con DNI: " + dni + " NO EXISTE, favor confirmar el documento de identidad.", null);
			}
			
		}else {
			
			validateObject.loadStatus(0, "[ERROR]: No ha especificado el DNI para ubicar los datos del Usuario", null);
			
		}
		
		return validateObject.getStatusTransaction();
	}
}
