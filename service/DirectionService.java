/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */

package com.springjpa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.Direction;
import com.springjpa.entity.StatusTransaction;
import com.springjpa.repository.IDirection;

@Service
public class DirectionService {

	@Autowired
	private IDirection directionDao;
	
	@Autowired
	private StatusTransaction statusTrans;
	
	private Map<Integer, String> resultTransaction = new HashMap<Integer, String>();
	
	//*********
	
	//ADD Method
	@Transactional
	public StatusTransaction addDirection(Direction direction) {
		
		if ((direction.getIdDirection() != null)) {
			
			resultTransaction.put(1, "Ha suministrado un Id inválido. Para el proceso de Alta este dato no debe ser especificado.");
			
		}
		
		if (validateDirection(direction)) {
			
			statusTrans.setGenericObject(direction);
			
			return statusTrans;
		}
		
		statusTrans.setGenericObject(directionDao.save(direction));
		
		resultTransaction.put(0, "Alta de la Dirección completada satisfactoriamente");
		
		return statusTrans;
	}
	
	//FIND ALL Method
	public List<Direction> findAllDirections(){
		
		return directionDao.findAll();
	}
	
	
	//FINDing User with Direction using INNER JOIN (Direction & User Classes)
	//SQL Native Query
	public List<Direction> findJoinUserDirection() {
		
		return  directionDao.findByJoinUserDirection();
	}
	
	
	//FIND by location and state
	//JPQL Query
	public List<Direction> findByLocationAndState(String location, String state){
		
		return directionDao.findByLocationAndState(location, state);
	}
	
	
	//IF EXIST Method
	//If exit a Direction ID, return true else false
	public boolean existDirection(Long idDirection) {
		
		Optional<Direction> optDirection = directionDao.findById(idDirection);
		
		if (optDirection.isPresent()) {
			
			return true;
		}
		
		return false;
	}
	
	//FIND Method
	//Finding a Direction by Id.
	public StatusTransaction findDirection(Long idDirection) {
		
		resultTransaction.clear();
		
		Optional<Direction> optDirection = directionDao.findById(idDirection);
	
		if (optDirection.isPresent()) {
			
			statusTrans.setGenericObject(optDirection.get());
			
			resultTransaction.put(0, "Proceso de búsqueda completado correctamente");
			
			statusTrans.setResultTransaction(resultTransaction);
			
			return statusTrans;
		
		}else {
			
			resultTransaction.put(1, "El Id de la Dirección no existe.");
			
		}
		
		statusTrans.setResultTransaction(resultTransaction);
		
		return statusTrans;
	}
	
	//FINDing based-on LIKE as a JPA operator
	public List<Direction> findSteetByConcidences(String street){
		
		return directionDao.findByStreetLike(street);
	}
	
	//UPDATE Method
	//Update a Direction returning a new object updated
	@Transactional
	public StatusTransaction updateDirection(Direction direction) {

		if (!resultTransaction.isEmpty()) {
			
			resultTransaction.clear();
		}
		
		if (existDirection(direction.getIdDirection())) {
			
			if (!validateDirection(direction)) {
			
				statusTrans.setGenericObject(findDirection(direction.getIdDirection()));
				
				Optional<Direction> optDirection = Optional.of(directionDao.save(direction));
				
				if (optDirection.isPresent()) {
					
					statusTrans.setGenericObject(optDirection.get());
			
					resultTransaction.put(0, "Proceso de Actualizacion de la Direccion completado satisfactoriamente");
					
				}else {
					
					statusTrans.setGenericObject(direction);
					
					resultTransaction.put(1, "Ha habido un fallo durante la persistencia de los nuevos datos de la Dirección. Proceso no completado");
				}
			}
		
		}else {
			
			resultTransaction.put(2, "El Id de la Dirección no existe, proceso de actualización de la Dirección no completado");
			
		}
		
		statusTrans.setResultTransaction(resultTransaction);
		
		return statusTrans;
	}
	
	//DELETE Method
	//Removing a Direction and User associated via Cascade relationship OneToOne property
	@Transactional
	public StatusTransaction deleteDirection(Long idDirection) {

		resultTransaction.clear();
		
		if (existDirection(idDirection)) {
			
			directionDao.deleteById(idDirection);
			
			resultTransaction.put(0, "Se ha eliminado exitosamente la Dirección de Código :" + idDirection);
		
		}else {
		
			resultTransaction.put(1, "El Código de Dirección :" + idDirection + " no existe. Proceso de elminación no completado");
			
		}
		
		statusTrans.setResultTransaction(resultTransaction);
		
		return statusTrans;
	}
	
	//VALIDATE Method
	//If we find some error, the DirectionControl Object store every message to notify the issue
	private boolean validateDirection(Direction direction) {
		
		boolean errorStatus = false;
		
		if (!resultTransaction.isEmpty()) {
			
			resultTransaction.clear();
		}
		
		if (direction.getLocation().isEmpty()) {
			
			resultTransaction.put(2, "Debe especificar un Localidad para la Dirección");
			
			errorStatus = true;
			
		}
		
		if (direction.getNumber().isEmpty()) {
			
			resultTransaction.put(3, "Debe especificar el número de la Dirección.");
			
			errorStatus = true;
		}
		
		if (direction.getState().isEmpty()) {
			
			resultTransaction.put(4, "Debe especificar el Estado de Dirección.");
			
			errorStatus = true;
		}
		
		if (direction.getStreet().isEmpty()) {
			
			resultTransaction.put(5, "Debe especificar la Calle/Avenida de Dirección.");
			
			errorStatus = true;
		}
		
		if (direction.getZipCode().isEmpty()) {
			
			resultTransaction.put(6, "Debe especificar el Código Postal de la Dirección.");
			
			errorStatus = true;
		}
		
		
		statusTrans.setResultTransaction(resultTransaction);
	
		return errorStatus;
	}
}
