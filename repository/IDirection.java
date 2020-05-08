package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Direction;

@Repository
public interface IDirection extends JpaRepository<Direction, Long>{

	//Inner Join Native SQL
	@Query(nativeQuery = true, value = "SELECT * FROM directions INNER JOIN users ON directions.direction_id = users.direction_id")
	List<Direction> findByJoinUserDirection();
	
	//JPQL Select
	@Query(value = "SELECT u FROM Direction u WHERE u.location = ?1 and u.state = ?2")
	List<Direction> findByLocationAndState(String location, String state);
	
	//JPQL LIKE param
	@Query(value = "SELECT d FROM Direction d WHERE d.street LIKE %?1%")
	List<Direction> findByStreetLike(String street);
	
}
