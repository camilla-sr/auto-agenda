package principal;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // em primeiro momento, main criada para testes com banco

        Peca pc = new Peca();
        TipoServico ts = new TipoServico();
        Estoque etq = new Estoque();
        Lote lt = new Lote();
        Funcionario funci = new Funcionario();
        Funcionamento f = new Funcionamento();

        Estoque e = new Estoque();
        
//        e.contadorPecas();
        Scanner sc = new Scanner(System.in, "utf8");
        Scanner num = new Scanner(System.in);
        
        e.listarProdutos();
        
//        System.out.println("Qual produto deseja alterar?");
//        int id_peca = num.nextInt();
//        
//        System.out.println("Informa a nova descrição do produto");
//        String novaDescricao = sc.nextLine();
//        
//        System.out.println("Qual a nova quantidade desse produto?");
//        int nova_qntd = num.nextInt();
//        
//        pc.editarPeca(id_peca, novaDescricao, nova_qntd);
//        pc.listarPecas();

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

//        System.out.println("Novo horário de funcionamento");
//        System.out.println("Insira o dia referente");
//        String dia = sc.nextLine();
//        
//        System.out.println("Insira o horário referente");
//        String horario = sc.nextLine();
//        
//        f.cadastrarFuncionamento(dia, horario);
//        f.listarFuncionamentos();
//        System.out.println("Digite o tipo de serviço");
//        String tipoServico = sc.nextLine();
//        ts.cadastrarTipoServico(tipoServico);
//        ts.listarTiposServico();
//        System.out.println("Digite o nome do funcionário");
//        String nomeFuncionario = sc.nextLine();
//        
//        funci.cadastrarFuncionario(nomeFuncionario);
//        funci.listarFuncionarios();
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
