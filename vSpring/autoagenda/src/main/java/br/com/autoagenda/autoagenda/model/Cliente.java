package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idCliente;
    private String nomeCliente;
    private String telefone;
    private String emailCliente;

    public Cliente() {}
    
    public Cliente(Integer idCliente, String nomeCliente, String telefone, String emailCliente) {
    	this.idCliente = idCliente;
    	this.nomeCliente = nomeCliente;
    	this.telefone = telefone;
    	this.emailCliente = emailCliente;
    }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}