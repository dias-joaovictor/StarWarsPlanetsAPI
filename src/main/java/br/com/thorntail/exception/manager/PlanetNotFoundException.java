package br.com.thorntail.exception.manager;

import java.io.Serializable;

public class PlanetNotFoundException extends Exception implements Serializable{

	private static final long serialVersionUID = -3254611742742744994L;

	public PlanetNotFoundException() {
		super("Planet Not Found");
	}

	public PlanetNotFoundException(String message) {
		super(message);
	}
	
	public PlanetNotFoundException(String message, Exception ex) {
		super(message, ex);
	}
	
}
