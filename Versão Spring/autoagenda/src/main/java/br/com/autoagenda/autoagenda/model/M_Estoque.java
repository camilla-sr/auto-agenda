package br.com.autoagenda.autoagenda.model;

import br.com.autoagenda.autoagenda.include.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com .autoagenda.autoagenda.model.M_Lote;
import br.com .autoagenda.autoagenda.model.M_Peca;

public class M_Estoque {
	final Conexao conn = new Conexao();
	M_Lote lt = new M_Lote();
	M_Peca pc = new M_Peca();
    
    private int idEstoque;
    private int peca;
    private String lote;
    private int quantidade;

    public void listarProdutos() {
        String sqlConsulta = "SELECT 'Óleos' AS Tipo, SUM(quantidade) AS qntd FROM estoque WHERE fk_lote IS NOT NULL "
            + "UNION "
            + "SELECT 'Peças' AS Tipo, SUM(quantidade) AS qntd FROM estoque WHERE fk_peca IS NOT NULL "
            + "UNION "
            + "SELECT 'Total Estoque' AS Tipo, SUM(quantidade) AS qntd FROM estoque";
        
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            if (lista != null) {
                System.out.println("\n\tConsultando Estoque Total");
                while (lista.next()) {                    
                    String tipo = lista.getString("Tipo");
                    int quantidade = lista.getInt("qntd");

                    System.out.printf("%s:\t%d\n", tipo, quantidade);
                }
                System.out.println("\n\t\tListando peças separadamente");
                pc.listarPecas();
                System.out.println("\n\t\tListando lotes separadamente");
                lt.listarLote();
            } else {
                System.out.println("Nada cadastrado no estoque.");
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public int getIdEstoque() { return idEstoque; }
    public void setIdEstoque(int idEstoque) {this.idEstoque = idEstoque; }
    public int getPeca() { return peca; }
    public void setPeca(int peca) { this.peca = peca; }
    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
