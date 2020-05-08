package com.springjpa.interfaces;

import java.util.Map;

import com.springjpa.entity.StatusTransaction;

/**
 * @author Tony Crespo - tonycrespo@outlook.com
 * Interfac to manage codes and messages generated from transaction and object affected
 */
public interface IStatusHandle {

	StatusTransaction loadStatus(Integer codeTransaction, String messageTransaction, Object objectTransaction);
	
	void deleteStatus();
	
	void showStatus(Map<Integer, String> resultTransaction, Object objectTransaction);
}
