package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuxiliarProdutosUsadosDAO {
    private int idProdutoUsado;
    private int estoque;
    private int agendamento;
    private int qntdUsada;

    
    // -------------- GETTERS E SETTERS --------------
    public int getIdProdutoUsado() {
        return idProdutoUsado;
    }
    public void setIdProdutoUsado(int idProdutoUsado) {
        this.idProdutoUsado = idProdutoUsado;
    }
    public int getEstoque() {
        return estoque;
    }
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    public int getAgendamento() {
        return agendamento;
    }
    public void setAgendamento(int agendamento) {
        this.agendamento = agendamento;
    }
    public int getQntdUsada() {
        return qntdUsada;
    }
    public void setQntdUsada(int qntdUsada) {
        this.qntdUsada = qntdUsada;
    }
    // ------------------------------------------------
}
