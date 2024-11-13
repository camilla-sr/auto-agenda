package principal;

import dao.FuncionarioDAO;
import include.Helper;
import java.util.Scanner;

public class Funcionario {

    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    FuncionarioDAO fn = new FuncionarioDAO();

    private int idFuncionario;
    private String nomeFuncionario;

    public void addFuncionario(){
        boolean cad = false;
        
        System.out.print("Nome do funcionário: ");
        setNomeFuncionario(sc.nextLine());
        
    }
    
    
    
    
    
    
    
    
    // -------------- GETTERS E SETTERS --------------
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
    //----------------------------------------------------
}