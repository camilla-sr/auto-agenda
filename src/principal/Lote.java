package principal;

import dao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Lote {
    private int codLote;
    private String dataCompra;
    private String dataVencimento;
    private String tipoOleo;
    private int qntdGarrafa;
    private String tipoProduto;
    
    public void cadastrarLote(){
        Conexao conn = new Conexao();

        String sqlInserir = "INSERT into lote(cod_lote, tipo_produto, tipo_oleo, qntd_garrafa)"
                + "VALUES ("+codLote+", "+tipoProduto+", "+tipoOleo+", "+qntdGarrafa+")";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            System.out.println("Lote cadastrado");
        } else {
            System.out.println("Algo deu errado");
        }
    }
    
    public void editarLote(int codLote, String tipoOleo, int qntdGarrafa, String tipoProduto){
        Conexao conn = new Conexao();
        String sqlEdit = "UPDATE lote set tipo_produto = '"+tipoProduto+"', tipo_oleo = "+tipoOleo+" where cod_lote = "+codLote+"";
        
        boolean resposta = conn.executar(sqlEdit);
        if(resposta == true){
            System.out.println("Lote Editado");
        }else{
            System.out.println("Algo deu errado");
        }
    }
  
     public void listarLote(){
        Conexao conn = new Conexao();
        String sqlConsulta = "SELECT * from lote";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        
        try {
            while (lista.next()) {
                int cod = lista.getInt("cod_lote");
                String tipoProduto = lista.getString("tipo_produto");
                int qntdGarrafa = lista.getInt("qntd_garrafa");
                String tipoOleo = lista.getString("tipo_oleo");

                System.out.println("C�digo do lote: " + cod);
                System.out.println("Tipo do Produto: " + tipoProduto);
                System.out.println("Quantidade de garrafas: " + qntdGarrafa);
                System.out.println("Tipo de �leo: " + tipoOleo);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
     
     // CRIAR M�TODO DE EXCLUS�O DO LOTE ABAIXO:
     
    
    
// -------------- GETTERS E SETTERS --------------
    
    public int getCodLote() {
        return codLote;
    }
    public void setCodLote(int codLote) {
        this.codLote = codLote;
    }
    public String getDataCompra() {
        return dataCompra;
    }
    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }
    public String getDataVencimento() {
        return dataVencimento;
    }
    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    public String getTipoOleo() {
        return tipoOleo;
    }
    public void setTipoOleo(String tipoOleo) {
        this.tipoOleo = tipoOleo;
    }
    public int getQntdGarrafa() {
        return qntdGarrafa;
    }
    public void setQntdGarrafa(int qntdGarrafa) {
        this.qntdGarrafa = qntdGarrafa;
    }
    public String getTipoProduto() {
        return tipoProduto;
    }
    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
    
// -----------------------------------------------


}