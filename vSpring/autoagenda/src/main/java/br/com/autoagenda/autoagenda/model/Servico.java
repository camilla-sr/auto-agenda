package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "servico")
public class Servico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServico;
    private String descServico;
    
    public Servico() {}
    
    public Servico(int idServico, String descricaoServico) {
    	this.idServico = idServico;
    	this.descServico = descricaoServico;
    }
    
    public int getIdServico() { return idServico; }
    public void setIdServico(int idServico) { this.idServico = idServico; }
    public String getDescServico() { return descServico; }
    public void setDescServico(String descricaoServico) { this.descServico = descricaoServico; }
}
