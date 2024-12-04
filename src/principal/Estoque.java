package principal;


import dao.EstoqueDAO;
import dao.FuncionarioDAO;
import include.Helper;
import java.util.Scanner;


public class Estoque {    
    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    FuncionarioDAO fn = new FuncionarioDAO();
    
    private int idEstoque;
    private int qntdEstoque;
    private String dataUltimaAtualizacao;
    
    public void estoque (){
        boolean cad = false;
        
        System.out.print("Quantidade no estoque: ");
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // -------------- GETTERS E SETTERS --------------
    public int getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(int idEstoque) {
        this.idEstoque = idEstoque;
    }

    public int getQntdEstoque() {
        return qntdEstoque;
    }

    public void setQntdEstoque(int qntdEstoque) {
        this.qntdEstoque = qntdEstoque;
    }

    public String getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }
    // -----------------------------------------------
}
