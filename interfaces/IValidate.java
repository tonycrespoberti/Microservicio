package com.springjpa.interfaces;

/**
 * @author Tony Crespo - tonycrespo@outlook.com
 * 
 * Interface to validate object's attributes (for instance User, Direction, CellPhone), type of Transaction (Insert or Update)
 * Extend from IStatusHandle who manages code and messages of transaction result and object affected 
 * 
 */
public interface IValidate extends IStatusHandle{
	
	boolean validateAttributes(Object typeObject, Integer typeTransaction);
	

}
