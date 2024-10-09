package principal;

import dao.Conexao;

public class Lote {
    private int codLote;
    private String dataCompra;
    private String dataVencimento;
    private String tipoOleo;
    private int qntdGarrafa;
    private char tipoProduto;
    
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
    public char getTipoProduto() {
        return tipoProduto;
    }
    public void setTipoProduto(char tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
    
// -----------------------------------------------


}