package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    final Conexao conn = new Conexao();
    private int idCliente;
    private String nomeCliente;
    private String whatsappCliente;
    private String modeloCarro;
    private String anoCarro;
    
    
// -------------- GETTERS E SETTERS --------------
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public String getWhatsappCliente() {
        return whatsappCliente;
    }
    public void setWhatsappCliente(String whatsappCliente) {
        this.whatsappCliente = whatsappCliente;
    }
    public String getModeloCarro() {
        return modeloCarro;
    }
    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }
    public String getAnoCarro() {
        return anoCarro;
    }
    public void setAnoCarro(String anoCarro) {
        this.anoCarro = anoCarro;
    }
// ------------------------------------------------
}
