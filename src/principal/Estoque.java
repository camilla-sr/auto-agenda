package principal;

import dao.Conexao;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Estoque {

    private String produto;
    private int qntdProduto;
    private int qntdTotal;
    private String statusAgendamento = "A";

    public void cadastrarEstoque(char produto, int qntdProduto, int qtndTotal) {
        Conexao conn = new Conexao();

        String sqlInserir = "INSERT into estoque(produto, qntdProduto, qtndTotal)"
                + "VALUES ('" + produto + "', " + qntdProduto + ", " + qntdTotal + ")";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("Produto inserido");
        } else {
            System.out.println("Algo deu errado");
        }
    }

    
    //  listarProdutos em correção!
    public void listarProdutos() {
        Conexao conn = new Conexao();
        String sqlConsulta = "SELECT * from estoque";
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        int calculaQntd = 0;
        String produto = "";
        try {
            while (lista.next()) {
                produto = lista.getString("produto");
                if (produto.equals("P")) {
                    int contagemPecas = contadorPecas();
                } else if (produto.equals("G")) {
                    int contagemOleo = contadorOleo();
                }
                
            }
            System.out.println("Produto é: " + produto + "\nSua quantidade é: " + qntd_produto);
        } catch (SQLException e) {
            System.out.println("Algo deu errado: " + e.getMessage());
        }
    }
    
    
    //Métodos de apoio
    public int contadorPecas() {
        Conexao conn = new Conexao();

        String sqlConsulta = "SELECT SUM(qntd_peca) as total from peca";
        ResultSet valorTotal = conn.executarConsulta(sqlConsulta);

        int row = 0;
        try {
            if (valorTotal != null && valorTotal.next()) {
                row = valorTotal.getInt("total");
                System.out.println(row);
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado: " + e.getMessage());
        }
        return row;
    }

    public int contadorOleo() {
        Conexao conn = new Conexao();

        String sqlConsulta = "SELECT SUM(qntd_produto) as total from estoque";
        ResultSet valorTotal = conn.executarConsulta(sqlConsulta);

        int row = 0;
        try{
            if (valorTotal != null && valorTotal.next()) {
                row = valorTotal.getInt("total");
                System.out.println(row);
            }
        }catch (SQLException e){
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

    public String getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(String statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

// -----------------------------------------------
}
