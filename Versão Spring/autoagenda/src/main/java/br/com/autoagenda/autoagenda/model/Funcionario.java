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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFuncionario;
	
	@NotEmpty
    private String nomeFuncionario;
	@NotEmpty
	private String usuario;
	@NotEmpty
	private String senha;
	
	public Funcionario() {}

	public Funcionario(int idFuncionario, String nomeFuncionario, String usuario, String senha) {
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.usuario = usuario;
		this.senha = senha;
	}
    
    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
}
