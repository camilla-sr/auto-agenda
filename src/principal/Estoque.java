package principal;

import dao.Conexao;

public class Estoque {
    private char produto;
    private int qntdProduto;
    private int qntdTotal;
    private char statusAgendamento;
    
// -------------- GETTERS E SETTERS --------------
    
    public char getProduto() {
        return produto;
    }
    public void setProduto(char produto) {
        this.produto = produto;
    }
    public int getQntdProduto() {
        return qntdProduto;
    }
    public void setQntdProduto(int qntdProduto) {
        this.qntdProduto = qntdProduto;
    }
    public int getQntdTotal() {
        return qntdTotal;
    }
    public void setQntdTotal(int qntdTotal) {
        this.qntdTotal = qntdTotal;
    }
    public char getStatusAgendamento() {
        return statusAgendamento;
    }
    public void setStatusAgendamento(char statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
    
// -----------------------------------------------


}