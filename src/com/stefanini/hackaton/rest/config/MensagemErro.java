package com.stefanini.hackaton.rest.config;

public class MensagemErro {

	private String menssagem;
	private Integer statusCode;
	private String stack;
	
	public String getStack() {
		return stack;
	}
	
	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getMenssagem() {
		return menssagem;
	}

	public void setMenssagem(String menssagem) {
		this.menssagem = menssagem;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

}
