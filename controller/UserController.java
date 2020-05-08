package com.springjpa.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springjpa.entity.StatusTransaction;
import com.springjpa.entity.User;
import com.springjpa.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StatusTransaction statusTran;
	
	private Map<Integer, String> resultTransaction = new HashMap<Integer, String>();
	
	//*****
	
	//NEW USER
	@PostMapping(path = "/user/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusTransaction> newUser(@RequestBody User user){
		
		statusTran = userService.addUser(user);
			
		resultTransaction = statusTran.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
				
				return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.OK);		
			}
		}
		
		return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.BAD_REQUEST);				
	}
	
	
	//Find by DNI
	@GetMapping(path = "/user/retrieve/{dni}")
	public ResponseEntity<StatusTransaction> findUser(@PathVariable ("dni") String dni){
		
		statusTran = userService.findUser(dni);
		
		resultTransaction = statusTran.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
				
				return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.OK);		
			}
		}
		
		return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.BAD_REQUEST);
	}
	
		
	@PutMapping(path = "/user/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatusTransaction> updateUser(@RequestBody User user){
		
		statusTran = userService.updateUser(user);
		
		resultTransaction = statusTran.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
				
				return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.OK);		
			}
		}
		
		return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.BAD_REQUEST);

	}
		
	@DeleteMapping(path = "/user/delete/{dni}")
	public ResponseEntity<StatusTransaction> deleteUser(@PathVariable ("dni") String dni){
		
		statusTran = userService.deleteUser(dni);
		
		resultTransaction = statusTran.getResultTransaction();
		
		for (Integer resultKey : resultTransaction.keySet()) {
			
			if (resultKey == 0) {
				
				return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.OK);		
			}
		}
		
		return new ResponseEntity<StatusTransaction>(statusTran, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(path = "/user/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> userList(){
		
		return new ResponseEntity<List<User>>(userService.listUsers(), HttpStatus.OK);
	}
}
