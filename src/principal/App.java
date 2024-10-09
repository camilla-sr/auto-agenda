package principal;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // em primeiro momento, main criada para testes com banco
        
        Peca pc = new Peca();
        TipoServico ts = new TipoServico();
        Estoque etq = new Estoque();
        Lote lt = new Lote();
        

        Scanner sc = new Scanner(System.in, "utf8");
        Scanner num = new Scanner(System.in);

        lt.listarLote();
        

//        System.out.println("Digite o tipo de serviço");
//        String tipoServico = sc.nextLine();
//
//        ts.cadastrarTipoServico(tipoServico);
//        ts.listarTiposServico();
//
//
//        System.out.println("Insira a primeira peça");
//
//        System.out.println("\nInsira descrição da peça");
//        String descricao = sc.nextLine();
//
//        System.out.println("Insira a quantidade da peça");
//        int qntd = num.nextInt();
//
//        pc.cadastrarPeça(descricao, qntd);
//        pc.listarPecas();
    }
}
