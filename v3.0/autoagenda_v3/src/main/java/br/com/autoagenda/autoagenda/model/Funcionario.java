package br.com.autoagenda.autoagenda.model;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "funcionario")
public class Funcionario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idFuncionario;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "fk_oficina") private Oficina oficina;
	@NotEmpty private String nomeFuncionario;
	private String telefone;
	@NotEmpty private String usuario;
	@Column(nullable = false, unique = true) private String email;
	@CPF private String cpf;
	@Column(nullable = true) private String senha;
	private String acesso = "comum";
	@Column(nullable = false) boolean primeiroLogin = false;
	@Column(name = "usar_2fa", columnDefinition = "boolean default false") private boolean usarAuth = false;
	@Column(nullable = false) boolean ativo = true;
	
	public Funcionario() {}

	public Funcionario(Integer idFuncionario, String nomeFuncionario, String cpf, String email,
			String telefone, String usuario, String senha) {
		this.idFuncionario = idFuncionario;
		this.nomeFuncionario = nomeFuncionario;
		this.telefone = telefone;
		this.cpf = cpf;
		this.email = email;
		this.usuario = usuario;
		this.senha = senha;
	}
	
    public Integer getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Integer idFuncionario) { this.idFuncionario = idFuncionario; }
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setEmail(String email) { this.email = email; }
	public String getEmail() { return email; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }
	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	public String getAcesso() { return acesso; }
	public void setAcesso(String acesso) { this.acesso = acesso; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public boolean isPrimeiroLogin() { return primeiroLogin; }
	public void setPrimeiroLogin(boolean primeiroLogin) { this.primeiroLogin = primeiroLogin; }
	public boolean isUsarAuth() { return usarAuth; }
    public void setUsarAuth(boolean usarAuth) { this.usarAuth = usarAuth; }
	public boolean isAtivo() { return ativo; }
	public void setAtivo(boolean ativo) { this.ativo = ativo; }
}