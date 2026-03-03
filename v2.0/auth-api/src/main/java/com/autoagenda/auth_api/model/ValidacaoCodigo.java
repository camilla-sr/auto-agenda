package com.autoagenda.auth_api.model;

public class ValidacaoCodigo {
	private String email;
	private String codigo;
	
	public ValidacaoCodigo() {}
	
	public ValidacaoCodigo(String email, String codigo) {
		this.email = email;
		this.codigo = codigo;
	}
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
}