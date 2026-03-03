package br.com.autoagenda.autoagenda.model;

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
@Table(name = "servico")
public class Servico {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idServico;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "fk_oficina") private Oficina oficina;
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
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    public String getDescServico() { return descServico; }
    public void setDescServico(String descServico) { this.descServico = descServico; }
    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }
}