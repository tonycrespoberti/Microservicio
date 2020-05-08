package com.springjpa.entity;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Scope("prototype")
//This bean is used for all Class Entity to store transaction information, data object involved, codes & messages transaction generated.
//For every request the user will recieve all transaction info.
public class StatusTransaction {

	private Map<Integer, String> resultTransaction;
	
	//@JsonIgnore
	private Object genericObject;

		
	//****
	
	public StatusTransaction() {

	}

	public StatusTransaction(Map<Integer, String> resultTransaction, Object genericObject) {
		this.resultTransaction = resultTransaction;
		this.genericObject = genericObject;
	}

	public Map<Integer, String> getResultTransaction() {
		return resultTransaction;
	}

	public void setResultTransaction(Map<Integer, String> resultTransaction) {
		this.resultTransaction = resultTransaction;
	}

	public Object getGenericObject() {
		return genericObject;
	}

	public void setGenericObject(Object genericObject) {
		this.genericObject = genericObject;
	}

	@Override
	public String toString() {
		return "StatusTransaction [resultTransaction=" + resultTransaction + ", genericObject=" + genericObject + "]";
	}
}
