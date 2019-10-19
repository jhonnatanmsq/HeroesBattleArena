package com.stefanini.hackaton.rest.exceptions;

public class NegocioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public NegocioException(final String mensagem) {
		super(mensagem);
	}

	public NegocioException(final Throwable t) {
		super(t);
	}

	public NegocioException(final String mensagem, final Throwable t) {
		super(mensagem, t);
	}
	
}
