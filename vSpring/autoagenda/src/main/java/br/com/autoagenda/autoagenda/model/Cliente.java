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

//    public boolean editarCliente(int idCliente, String novoNomeCliente, String novoWhatsappCliente, String novoModeloCarro, int novoAnoCarro) {
//        String sqlEdit = "";
//
//        // Muda o nome do cliente
//        if (novoNomeCliente != null && novoWhatsappCliente == null && novoModeloCarro == null && novoAnoCarro == 0) {
//            sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "'";
//        }
//        //Muda o whatsapp do cliente
//        if (novoWhatsappCliente != null && novoNomeCliente == null && novoModeloCarro == null && novoAnoCarro == 0) {
//            sqlEdit = "UPDATE cliente set whatsapp_cliente = '" + novoWhatsappCliente + "'";
//        }
//        // Muda o modelo do carro
//        if (novoModeloCarro != null && novoNomeCliente == null && novoWhatsappCliente == null && novoAnoCarro == 0) {
//            sqlEdit = "UPDATE cliente set modelo_carro = '" + novoModeloCarro + "'";
//        }
//        //Muda o ano do carro
//        if (novoAnoCarro != 0 && novoNomeCliente == null && novoWhatsappCliente == null && novoModeloCarro == null) {
//            sqlEdit = "UPDATE cliente set ano_carro = " + novoAnoCarro;
//        }
//        // Muda tudo
//        if (novoAnoCarro != 0 && novoNomeCliente != null && novoWhatsappCliente != null && novoModeloCarro != null) {
//            sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "', whatsapp_cliente = '" + novoWhatsappCliente
//                    + "', modelo_carro = '" + novoModeloCarro + "', ano_carro = '" + novoAnoCarro + "'";
//        }
//
//        sqlEdit = sqlEdit + " WHERE id_cliente = " + idCliente + "";
//        boolean resposta = Conexao.executar(sqlEdit);
//        if(!resposta) return false;
//        
//        return true;
//    }

//    public boolean apagarCliente(int idCliente) {
//        String sql = "DELETE from cliente WHERE id_cliente = " + idCliente + "";
//        boolean resposta = Conexao.executar(sql);
//        if(!resposta) return false;
//        return true;
//    }
    
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
