package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class creates a custom made exception that is thrown when an error has
 * occurred A lot of this was just copy-pasted to mimic another Youtube Video.
 *
 * @author jroot,ascase
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	/**
	 * Custom made exception that gathers information about where a failure occured
	 * @param resourceName - what class it is referring to
	 * @param fieldName - a param that was trying to be used
	 * @param fieldValue - the value of the param
	 */
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}


	/**
	 * gives the reference to the filename we are referencing
	 * @return - the filename we are referring to
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * takes the param name and passes it onto the exception
	 * @return - string of the param being passed in
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * takes the param value for debugging purposes
	 * @return - the value of the param
	 */
	public Object getFieldValue() {
		return fieldValue;
	}
}