package br.com.autoagenda.autoagenda.controller;

import br.com.autoagenda.autoagenda.model.M_Estoque;
import br.com.autoagenda.autoagenda.model.M_Funcionario;
import br.com.autoagenda.autoagenda.include.Helper;

public class C_Estoque {
	Integer numeroValidado = null;
    M_Estoque es = new M_Estoque();
    
    private int idEstoque;
    private int qntdEstoque;
    private String dataUltimaAtualizacao;
    
    public void consEstoque (){
        es.listarProdutos();
    }

    public int getIdEstoque() { return idEstoque; }
    public void setIdEstoque(int idEstoque) { this.idEstoque = idEstoque; }
    public int getQntdEstoque() { return qntdEstoque; }
    public void setQntdEstoque(int qntdEstoque) { this.qntdEstoque = qntdEstoque; }
    public String getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }
}
