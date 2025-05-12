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
    
    public boolean cadastrarServico(String descricaoServico) {
    	String sql = "INSERT INTO tipo_servico (desc_servico) VALUES ('" + descricaoServico + "')";
//    	boolean resposta = Conexao.executar(sql);
//    	
//    	if(!resposta) return false;
    	
    	return true;
    }

//    public boolean editarServico(int idServico, String novaDescricao) {
//    	boolean valido = Conexao.validaID("tipo_servico", "id_servico", idServico);
//    	if(!!valido) return false;
//    	
//        String sql = "UPDATE tipo_servico set desc_servico = '" + novaDescricao + "' where id_servico = " + idServico + "";
//        boolean resposta = Conexao.executar(sql);
//        if(!resposta) return false;
//        
//        return true;
//    }
//
//    public boolean apagarServico(int idServico) {
//        boolean valido = Conexao.validaID("tipo_serivo", "id_servico", idServico);
//        if(!valido) return false;
//
//        String sql = "DELETE from tipo_servico where id_servico = " + idServico + "";
//        boolean resposta = Conexao.executar(sql);
//        if (!resposta) return false;
//        
//        return true;
//    }
    
    public int getIdServico() { return idServico; }
    public void setIdServico(int idServico) { this.idServico = idServico; }
    public String getDescServico() { return descServico; }
    public void setDescServico(String descricaoServico) { this.descServico = descricaoServico; }
}
