package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionario")
public class Funcionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFuncionario;
    private String nomeFuncionario;
    private String usuario;
    private String senha;
	
	public Funcionario() {}

	public Funcionario(int idFuncionario, String nomeFuncionario, String usuario, String senha) {
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.usuario = usuario;
		this.senha = senha;
	}
    
    public boolean cadastrarFuncionario(String nomeFuncionario, String usuario, String senha) {
        String sql = "INSERT INTO funcionario (nome_funcionario) VALUES ('" + nomeFuncionario + "', '" + usuario + "', "
        		+ "'" + senha + "')";
        
        return true;
    }

// -------------- MÉTODOS DE APOIO --------------
    
    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
}
