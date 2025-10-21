package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "funcionario")
public class Funcionario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idFuncionario;
	@NotEmpty private String nomeFuncionario;
	@NotEmpty private String cpf;
	@NotEmpty private String email;
	@NotEmpty private String usuario;
	@NotEmpty private String senha;
	private String acesso = "comum";
	
	public Funcionario() {}

	public Funcionario(Integer idFuncionario, String nomeFuncionario, String cpf, String email, String usuario, String senha) {
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.cpf = cpf;
		this.email = email;
		this.usuario = usuario;
		this.senha = senha;
	}
    
    public Integer getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Integer idFuncionario) { this.idFuncionario = idFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setEmail(String email) { this.email = email; }
	public String getEmail() { return email; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public String getAcesso() { return acesso; }
	public void setAcesso(String acesso) { this.acesso = acesso; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
}