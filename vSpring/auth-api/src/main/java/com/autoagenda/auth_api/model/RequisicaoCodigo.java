package com.autoagenda.auth_api.model;

public class RequisicaoCodigo {
	private String email;

	public RequisicaoCodigo() {}
	
	public RequisicaoCodigo(String email) { this.email = email; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
}