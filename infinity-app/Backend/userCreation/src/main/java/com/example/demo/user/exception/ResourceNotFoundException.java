package com.example.demo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
/**
 * This class creates a custom made exception that is thrown when an error has occurred
 * A lot of this was just copy-pasted to mimic another Youtube Video.
 *
 *	@author jroot,ascase
 */



public class ResourceNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue)
    {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName()
    {
        return resourceName;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public Object getFieldValue()
    {
        return fieldValue;
    }
}