/**
 * Author: Tony Crespo, tonycrespo@outlook.com
 */
package com.springjpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.entity.CellPhone;
import com.springjpa.entity.StatusTransaction;
import com.springjpa.repository.ICellPhone;

@Service
public class CellPhoneService {

	@Autowired
	private ICellPhone cellPhoneDao;
	
	@Autowired
	private StatusTransaction statusTrans;

	//*********
	
	@Transactional
	public List<CellPhone> addCellPhone() {
		
		return null;
	}
}
