package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "superadmin")
public class SuperAdmin {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idSuperadmin;
	@NotNull private String nome;
	@NotNull private String email;
	@NotNull private String usuario;
	@NotNull  String senha;
	private boolean ativo;
	private boolean primeiroLogin;
	
	public SuperAdmin() {}
	
	public Integer getIdSuperadmin() { return idSuperadmin; }
	public void setIdSuperadmin(Integer idSuperadmin) { this.idSuperadmin = idSuperadmin; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public boolean isAtivo() { return ativo; }
	public void setAtivo(boolean ativo) { this.ativo = ativo; }
	public boolean isPrimeiroLogin() { return primeiroLogin; }
	public void setPrimeiroLogin(boolean primeiroLogin) { this.primeiroLogin = primeiroLogin; }
}