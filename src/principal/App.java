package principal;

import java.util.Scanner;
import dao.AgendamentoDAO;
import dao.AuxiliarProdutosUsadosDAO;
import dao.ClienteDAO;
import dao.EstoqueDAO;
import dao.FuncionarioDAO;
import dao.LoteDAO;
import dao.PecaDAO;
import dao.TipoServicoDAO;

public class App {

    //Use essa main para fazer os testes dos m�todos
    public static void main(String[] args) {
        //uso do m�todo scanner para realizar os testes
        //adicionei um utf8 para eliminar os erros de caracteres quebrados
        Scanner sc = new Scanner(System.in, "utf8");
        Scanner num = new Scanner(System.in);
        
        //todas as classes est�o instanciadas aqui para facilitar os testes
        AgendamentoDAO  ag = new AgendamentoDAO();
        ClienteDAO      cl = new ClienteDAO();
        EstoqueDAO      e = new EstoqueDAO();
        FuncionarioDAO  f = new FuncionarioDAO();
        LoteDAO         lt = new LoteDAO();
        PecaDAO         pc = new PecaDAO();
        TipoServicoDAO  ts = new TipoServicoDAO();
        AuxiliarProdutosUsadosDAO aux = new AuxiliarProdutosUsadosDAO();

        //chame os m�todos para testes no espa�o abaixo
        
    }
}
