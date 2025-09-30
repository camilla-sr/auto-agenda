package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    private String nomeCliente;
    private String whatsappCliente;
    private String modeloCarro;
    private int anoCarro;

    public Cliente() {}
    
    public boolean cadastrarCliente(String nomeCliente, String whatsappCliente, String modeloCarro, int anoCarro) {
        String sql = "INSERT into cliente (nome_cliente, whatsapp_cliente, modelo_carro, ano_carro)"
                + "VALUES ('" + nomeCliente + "', '" + whatsappCliente + "', '" + modeloCarro + "', '" + anoCarro + "')";

//        boolean resposta = Conexao.executar(sql);
//        if(!resposta) return false;
        
        return true;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getWhatsappCliente() { return whatsappCliente; }
    public void setWhatsappCliente(String whatsappCliente) { this.whatsappCliente = whatsappCliente; }
    public String getModeloCarro() { return modeloCarro; }
    public void setModeloCarro(String modeloCarro) {this.modeloCarro = modeloCarro; }
    public int getAnoCarro() { return anoCarro; }
    public void setAnoCarro(int anoCarro) { this.anoCarro = anoCarro; }
}
