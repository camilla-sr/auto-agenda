package br.com.autoagenda.autoagenda.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idCliente;
    private String nomeCliente;
    private String telefone;
    private String email;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private List<Veiculo> veiculos;

    public Cliente() {}
    
    public Cliente(Integer idCliente, String nomeCliente, String telefone, String email) {
    	this.idCliente = idCliente;
    	this.nomeCliente = nomeCliente;
    	this.telefone = telefone;
    	this.email= email;
    }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getEmail() { return email; }
    public void setEmail(String emailCliente) { this.email = emailCliente; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}