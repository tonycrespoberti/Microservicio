package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.CellPhone;

@Repository
public interface ICellPhone extends JpaRepository<CellPhone, Integer>{

}
