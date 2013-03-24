package net.danielfreire.util;

import java.util.Collection;
import java.util.HashMap;

public class GenericResponse {
	
	private Boolean status;
	private HashMap<String, String> messageError;
	private Object generic;
	private Collection<?> genericList;
	
	public GenericResponse() {
		this.status = true;
	}
	
	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public HashMap<String, String> getMessageError() {
		return messageError;
	}

	public void setMessageError(HashMap<String, String> messageError) {
		this.messageError = messageError;
	}

	public Object getGeneric() {
		return generic;
	}

	public void setGeneric(Object generic) {
		this.generic = generic;
	}

	public Collection<?> getGenericList() {
		return genericList;
	}

	public void setGenericList(Collection<?> genericList) {
		this.genericList = genericList;
	}
	

}
