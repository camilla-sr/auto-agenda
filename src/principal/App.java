package principal;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // em primeiro momento, main criada para testes com banco
        
        Peca pc = new Peca();
        TipoServico ts = new TipoServico();
        
        Scanner sc = new Scanner(System.in, "utf8");
        Scanner num = new Scanner(System.in);
        
        System.out.println("Digite o tipo de servi�o");
        String tipoServico = sc.nextLine();
        
        ts.cadastrarTipoServico(tipoServico);
        ts.listarTiposServico();
        
        
        System.out.println("Insira a primeira pe�a");
        
        System.out.println("\nInsira descri��o da pe�a");
        String descricao = sc.nextLine();
        
        System.out.println("Insira a quantidade da pe�a");
        int qntd = num.nextInt();
        
        pc.cadastrarPe�a(descricao, qntd);
        pc.listarPecas();
    }
}
