package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "servico")
public class Servico {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idServico;
	@NotEmpty private String nomeServico;
	@Column(length = 1000) @NotEmpty private String descServico;
    
    public Servico() {}
    
    public Servico(Integer idServico, String nomeServico,String descServico) {
    	this.idServico = idServico;
    	this.nomeServico = nomeServico;
    	this.descServico = descServico;
    }
    
    public Integer getIdServico() { return idServico; }
    public void setIdServico(Integer idServico) { this.idServico = idServico; }
    public String getDescServico() { return descServico; }
    public void setDescServico(String descServico) { this.descServico = descServico; }
    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }
}