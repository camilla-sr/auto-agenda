package com.autoagenda.auth_api.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "codigo2fa")
public class Codigo2FA {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
	private String email;
	private String codigo;
	private boolean enviado;
	private LocalDateTime horaGeracao;
	private boolean validado;
	
	public Codigo2FA() {}

    public Codigo2FA(String email, String codigo, LocalDateTime horaGeracao, boolean validado) {
        this.email = email;
        this.codigo = codigo;
        this.horaGeracao = horaGeracao;
        this.validado = validado;
    }

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getCodigo() { return codigo; }
	public void setCodigo(String codigo) { this.codigo = codigo; }
	public boolean getEnviado() { return enviado; }
	public void setEnviado(boolean enviado) { this.enviado = enviado; }
	public LocalDateTime getHoraGeracao() { return horaGeracao; }
	public void setHoraGeracao(LocalDateTime horaGeracao) { this.horaGeracao = horaGeracao; }
	public boolean isValidado() { return validado; }
	public void setValidado(boolean validado) { this.validado = validado; }
}