package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstoqueDAO {
    final Conexao conn = new Conexao();
    private int idEstoque;
    private int produto;
    private int Nomeproduto;    //campo vai armazenar o nome da peça com inner join
    private int lote;
    private int descricaoOleo;  //campo vai armazenar qual o óleo com inner join
    private int qntdProduto;
    private int qntdTotal;
    private String dataUltimaAtualizacao;

    public void listarProdutos() {
        String sqlConsulta = "SELECT  produto, sum(qntd_produto) as qntd_produto from estoque group by produto";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        String nomeProduto = "";
        int qntd = 0;
        
        try {
            while (lista.next()) {
                String tip_produto = lista.getString("produto");
                qntd = lista.getInt("qntd_produto");
                
                if(tip_produto.equals("P")){
                    nomeProduto = "Peças ";
                }else if(tip_produto.equals("G")){
                    nomeProduto = "Garrafas de Óleo ";
                }
            System.out.println(nomeProduto + "----> " + qntd);
            }
            System.out.println("Total estoque ----> " + (contadorPecas() + contadorOleo()));
        } catch (SQLException e) {
            System.out.println("Algo deu errado: " + e.getMessage());
        }
    }
    

    // -------------- MÉTODOS DE APOIO --------------
    public int contadorOleo() {
        String sqlConsulta = "SELECT SUM(qntd_garrafa) as total from lote";
        ResultSet valorTotal = conn.executarConsulta(sqlConsulta);

        int row = 0;
        try {
            if (valorTotal != null && valorTotal.next()) {
                row = valorTotal.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado: " + e.getMessage());
        }
        return row;
    }

    public int contadorPecas() {
        String sqlConsulta = "SELECT SUM(qntd_peca) as total from peca";
        ResultSet valorTotal = conn.executarConsulta(sqlConsulta);

        int row = 0;
        try {
            if (valorTotal != null && valorTotal.next()) {
                row = valorTotal.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado: " + e.getMessage());
        }
        return row;
    }
    
    
// -------------- GETTERS E SETTERS --------------
    public int getIdEstoque() {
        return idEstoque;
    }
    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }
    public int getProduto() {
        return produto;
    }
    public void setProduto(int produto) {
        this.produto = produto;
    }
    public int getNomeproduto() {
        return Nomeproduto;
    }
    public void setNomeproduto(int Nomeproduto) {
        this.Nomeproduto = Nomeproduto;
    }
    public int getLote() {
        return lote;
    }
    public void setLote(int lote) {
        this.lote = lote;
    }
    public int getDescricaoOleo() {
        return descricaoOleo;
    }
    public void setDescricaoOleo(int descricaoOleo) {
        this.descricaoOleo = descricaoOleo;
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
    public String getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }
    public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }
// -----------------------------------------------
}
