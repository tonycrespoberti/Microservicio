/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */
package com.springjpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.User;

@Repository
public interface IUser extends JpaRepository<User, Long>{

	Optional<User> findById(Long idUser);
	
	List<User> findAll();
	
	@Query(value = "SELECT u FROM User u WHERE u.firstAndSecondName LIKE %?1%")
	List<User> findByFirstnameContaining(String expressionMatch);
	
	Optional<User> findByDni(String dni);
	
	//Inner Join Native SQL
	@Query(nativeQuery = true, value = "SELECT * FROM users INNER JOIN directions ON users.direction_id = directions.direction_id")
	List<User> findByJoinUserDirection();
	
	void deleteByDni(String dni);
	
}
