package com.ms_email.email.models;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ms_email.email.enums.StatusEnvio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Email {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
	private String destinatario;
	private String remetente;
	private String assunto;
	@Column(columnDefinition = "TEXT") private String texto;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") private LocalDateTime dataCriacao;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") private LocalDateTime dataEnvio;
	private int tentativas = 0;
	@Enumerated(EnumType.STRING) private StatusEnvio statusEnvio;
		
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getDestinatario() { return destinatario; }
	public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
	public String getRemetente() { return remetente; }
	public void setRemetente(String remetente) { this.remetente = remetente; }
	public String getAssunto() { return assunto; }
	public void setAssunto(String assunto) { this.assunto = assunto; }
	public String getTexto() { return texto; }
	public void setTexto(String texto) { this.texto = texto; }
	public LocalDateTime getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
	public LocalDateTime getDataEnvio() { return dataEnvio; }
	public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
	public int getTentativas() { return tentativas; }
	public void setTentativas(int tentativas) { this.tentativas = tentativas; }
	public StatusEnvio getStatusEnvio() { return statusEnvio; }
	public void setStatusEnvio(StatusEnvio statusEnvio) { this.statusEnvio = statusEnvio; }
}