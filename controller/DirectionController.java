/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */
package com.springjpa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.entity.Direction;
import com.springjpa.entity.StatusTransaction;
import com.springjpa.service.DirectionService;

@RestController
public class DirectionController {

	@Autowired
	private DirectionService directionService;
	
	@Autowired
	private StatusTransaction statusTrans;
	
	private Map<Integer, String> resultTransaction = new HashMap<Integer, String>();
	
	//****
	
	@PostMapping(path = "/direction/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusTransaction> newDirection(@RequestBody Direction direction){
		
		resultTransaction.clear();
		
		statusTrans = directionService.addDirection(direction);
		
		resultTransaction = statusTrans.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
			
				return new ResponseEntity<StatusTransaction>(statusTrans, HttpStatus.CREATED);
			}
		}		
		
		return new ResponseEntity<StatusTransaction>(statusTrans, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/direction/retrieve/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusTransaction> retrieveDirection(@PathVariable ("id") Long idDirection){
		
		resultTransaction.clear();
		
		statusTrans = directionService.findDirection(idDirection);
		
		resultTransaction = statusTrans.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
			
				return new ResponseEntity<StatusTransaction>(statusTrans, HttpStatus.CREATED);
			}
		}		
		
		return new ResponseEntity<StatusTransaction>(statusTrans, HttpStatus.BAD_REQUEST);
	}
	
	//Fetching using JPA LIKE Operator
	@GetMapping(path = "/direction/street/{street}")
	public ResponseEntity<List<Direction>> findStreetByConcidences(@PathVariable("street") String street){
			
		return new ResponseEntity<List<Direction>>(directionService.findSteetByConcidences(street), HttpStatus.OK);
	}
	
	
	@GetMapping(path = "/direction/all")
	public ResponseEntity<List<Direction>> findAllDirections(){
		
		return new ResponseEntity<List<Direction>>(directionService.findAllDirections(), HttpStatus.OK);

	}
	
	//Fetching based-on JPA Inner Join property
	@GetMapping(path = "/direction/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Direction>> findUserDirection(){
		
		List<Direction> userDirectionsList = new ArrayList<Direction>();
		
		userDirectionsList = directionService.findJoinUserDirection();
		
		if (userDirectionsList.isEmpty()) {
			
			return new ResponseEntity<List<Direction>>(userDirectionsList, HttpStatus.NO_CONTENT);
			
		}else {
			
			return new ResponseEntity<List<Direction>>(userDirectionsList, HttpStatus.OK);
		}
	}
	
	//Fetching based-on JPA AND Operator 
	@GetMapping(path = "/direction/street/{location}/state/{state}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Direction>> findByLocationAndState(@PathVariable ("location") String location, @PathVariable ("state") String state){
		
		List<Direction> userDirectionsList = new ArrayList<Direction>();
		
		userDirectionsList = directionService.findByLocationAndState(location, state);
		
		if (userDirectionsList.isEmpty()) {
			
			return new ResponseEntity<List<Direction>>(userDirectionsList, HttpStatus.NO_CONTENT);
			
		}else {
			
			return new ResponseEntity<List<Direction>>(userDirectionsList, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(path = "/direction/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusTransaction> removeDirection(@PathVariable ("id") Long idDirection){
		
		resultTransaction.clear();
		
		statusTrans = directionService.deleteDirection(idDirection);
		
		resultTransaction = statusTrans.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
			
				return new ResponseEntity<StatusTransaction>(statusTrans, HttpStatus.OK);
			}
		}		
		
		return new ResponseEntity<StatusTransaction>(statusTrans, HttpStatus.BAD_REQUEST);
	}
}
