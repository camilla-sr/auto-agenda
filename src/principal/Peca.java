package principal;

import dao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Peca {    
    private int idPeca;
    private String descricaoPeca;
    private int qntdPeca;
    private char tipoProduto = 'P';
    
    // Métodos Principais
    public void cadastrarPeça(String descricaoPeca, int qntdPeca){
        Conexao conn = new Conexao();
        
        String sqlInserir = "INSERT into peca (tipo_produto, desc_peca, qntd_peca)"
                + "VALUES ('"+tipoProduto+"', '"+descricaoPeca+"', "+qntdPeca+")";
        
        boolean resposta = conn.executar(sqlInserir);
        if(resposta == true){
            System.out.println("Peça inserida");
        }else{
            System.out.println("Algo deu errado");
        }
    }
    
    public void listarPecas(){
        Conexao conn = new Conexao();
        String sqlConsulta = "SELECT * from peca";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        
        try {
            while (lista.next()) {
                String tipoProduto = lista.getString("tipo_produto");
                String descricaoPeca = lista.getString("desc_peca");
                int quantidade = lista.getInt("qntd_peca");

                System.out.println("Tipo do Produto: " + tipoProduto);
                System.out.println("Descrição da Peça: " + descricaoPeca);
                System.out.println("Quantidade: " + quantidade);
                System.out.println("---------------------------");
            }
            
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
            
    }

    
    
    
    
    
// -------------- GETTERS E SETTERS --------------
    
    public int getIdPeca() {
        return idPeca;
    }
    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }
    public String getDescricaoPeca() {
        return descricaoPeca;
    }
    public void setDescricaoPeca(String descricaoPeca) {
        this.descricaoPeca = descricaoPeca;
    }
    public int getQntdPeca() {
        return qntdPeca;
    }
    public void setQntdPeca(int qntdPeca) {
        this.qntdPeca = qntdPeca;
    }
    public char getTipoProduto() {
        return tipoProduto;
    }
    public void setTipoProduto(char tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
    
// -----------------------------------------------


}