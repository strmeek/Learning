package com.example.learning.domain.exception;

public class NegocioException extends RuntimeException{

	/**
	 * Ajuda a tratar as excessões em Negocio
	 */
	private static final long serialVersionUID = 1L;
	
	public NegocioException(String message) {
		super(message);
	}
}
