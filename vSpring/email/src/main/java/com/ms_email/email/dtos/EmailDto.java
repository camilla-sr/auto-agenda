package com.ms_email.email.dtos;

import org.springframework.beans.factory.annotation.Value;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDto {
	@NotBlank @Email private String destinatario;
	@Value("${spring.mail.username}") private String remetente;
	@NotBlank private String assunto;
	@NotBlank private String texto;
		
	public String getDestinatario() { return destinatario; }
	public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
	public String getRemetente() { return remetente; }
	public void setRemetente(String remetente) { this.remetente = remetente; }
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }
	public String getTexto() { return texto; }
	public void setTexto(String texto) { this.texto = texto; }
}