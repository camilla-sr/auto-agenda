package com.autoagenda.auth_api.model;

public class ValidacaoCodigo {
	private String email;
	private String codigoDigitado;
	
	public ValidacaoCodigo() {}
	
	public ValidacaoCodigo(String email, String codigoDigitado) {
		this.email = email;
		this.codigoDigitado = codigoDigitado;
	}
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getCodigoDigitado() { return codigoDigitado; }
	public void setCodigoDigitado(String codigoDigitado) { this.codigoDigitado = codigoDigitado; }
}