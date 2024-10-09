package principal;

import dao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Estoque {
    private String produto;
    private int qntdProduto;
    private int qntdTotal;

    public void listarProdutos() {
        Conexao conn = new Conexao();
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

    public int contadorOleo() {
        Conexao conn = new Conexao();

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
        Conexao conn = new Conexao();

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
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
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
// -----------------------------------------------
}
