package br.com.autoagenda.autoagenda.model;

import org.hibernate.validator.constraints.br.CNPJ;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "oficina")
public class Oficina {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idOficina;
	private String razaoSocial;
	@NotNull private String nomeFantasia;
	@NotNull private String slug;
	@CNPJ @Column(length = 18) private String cnpj;
	@NotNull private String inscricaoMunicipal;
	private String inscricaoEstadual;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	@NotNull private String telefonePrincipal;
	private String telefoneSecundario;
	@NotNull private String emailContato;
	private String logotipo;
	
	@Column(name = "usar_produtos", columnDefinition = "boolean default true")
    private boolean usarProdutos = true;
    
    @Column(name = "usar_financeiro", columnDefinition = "boolean default true")
    private boolean usarFinanceiro = true;
    
    @Column(columnDefinition = "boolean default true")
    private Boolean ativo = true;
	
	public Oficina() {}
	
	//	GETTERS E SETTERS
	public Integer getIdOficina() { return idOficina; }
	public void setIdOficina(Integer idOficina) { this.idOficina = idOficina; }
	public String getRazaoSocial() { return razaoSocial; }
	public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
	public String getNomeFantasia() { return nomeFantasia; }
	public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
	public String getCnpj() { return cnpj; }
	public void setCnpj(String cnpj) { this.cnpj = cnpj; }
	public String getSlug() { return slug; }
	public void setSlug(String slug) { this.slug = slug; }
	public String getInscricaoMunicipal() { return inscricaoMunicipal; }
	public void setInscricaoMunicipal(String inscricaoMunicipal) { this.inscricaoMunicipal = inscricaoMunicipal; }
	public String getInscricaoEstadual() { return inscricaoEstadual; }
	public void setInscricaoEstadual(String inscricaoEstadual) { this.inscricaoEstadual = inscricaoEstadual; }
	public String getLogradouro() { return logradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }
	public String getComplemento() { return complemento; }
	public void setComplemento(String complemento) { this.complemento = complemento; }
	public String getBairro() { return bairro; }
	public void setBairro(String bairro) { this.bairro = bairro; }
	public String getCidade() { return cidade; }
	public void setCidade(String cidade) { this.cidade = cidade; }
	public String getUf() { return uf; }
	public void setUf(String uf) { this.uf = uf; }
	public String getCep() { return cep; }
	public void setCep(String cep) { this.cep = cep; }
	public String getTelefonePrincipal() { return telefonePrincipal; }
	public void setTelefonePrincipal(String telefonePrincipal) { this.telefonePrincipal = telefonePrincipal; }
	public String getTelefoneSecundario() { return telefoneSecundario; }
	public void setTelefoneSecundario(String telefoneSecundario) { this.telefoneSecundario = telefoneSecundario; }
	public String getEmailContato() { return emailContato; }
	public void setEmailContato(String emailContato) { this.emailContato = emailContato; }
	public String getLogotipo() { return logotipo; }
	public void setLogotipo(String logotipo) { this.logotipo = logotipo; }

	public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
	public boolean isUsarProdutos() { return usarProdutos; }
	public void setUsarProdutos(boolean usarProdutos) { this.usarProdutos = usarProdutos; }
	public boolean isUsarFinanceiro() { return usarFinanceiro; }
	public void setUsarFinanceiro(boolean usarFinanceiro) { this.usarFinanceiro = usarFinanceiro; }
}