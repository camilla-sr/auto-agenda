package principal;

import java.io.IOException;
import java.util.Scanner;
import principal.Agendamento;
import principal.Cliente;
import principal.Funcionario;
import principal.Lote;
import principal.Peca;
import principal.TipoServico;

public class Interface {

    //adicionei um utf8 para eliminar os erros de caracteres quebrados
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    
    //chamada dos métodos principais
    // INTERFACE CONCLUÍDA
    Lote lt = new Lote();
    Peca pc = new Peca();
    
    // INTERFACE PENDENTE
    Agendamento ag = new Agendamento();
    Cliente cl = new Cliente();
    Funcionario f = new Funcionario();
    TipoServico ts = new TipoServico();

    // ------------------------------------------------
    public void inicio() {
        System.out.println("\tBem-Vindo ao Auto Agenda");
        System.out.println("1. Cadastrar Itens\t 2. Editar Itens\n"
                + "3. Apagar Itens\t\t 4.Consultar Itens\t5.Relatórios\n"
                + "0. Sair");

        System.out.print("\nVocê pode navegar pelo sistema"
                + " informando seus números correspondentes");
        System.out.print("\n>>>>>  ");

        int opcao = num.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Cadastros");
                cadastrar();
                break;
            case 2:
                System.out.println("Editar");
                editar();
                break;
            case 3:
                System.out.println("Apagar");
                apagar();
                break;
            case 4:
                System.out.println("Consultas");
                consultar();
                break;
//            case 5:
//                System.out.println("Relatórios disponíveis");
//                relatorios();
//                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    public void cadastrar() {
        System.out.println("1. Peças\t2. Lotes de Óleo\n3. Clientes\t"
                + "4. Serviços\t5. Agendamento\t6. Funionários\n"
                + "0. Voltar");
        int tela = num.nextInt();

        switch (tela) {
            case 1:
                pc.addPeca();
                System.out.println("\n");
                prosseguir();
                break;
            case 2:
                lt.addLote();
                System.out.println("\n");
                prosseguir();
                break;
            case 3:
                cl.addCliente();
                System.out.println("\n");
                prosseguir();
                break;
//            case 4:
//                ts.addServico();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 5:
//                ag.addAgendamento();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 6:
//                f.addFuncionario();
//                System.out.println("\n");
//                inicio();
//                break;
            case 0:
                inicio();
                break;
        }
    }

    public void editar() {
        System.out.println("1. Peças\t 2. Lote de Óleo\t\t 3. Cliente"
                + "\n0. Voltar");
        int tela = num.nextInt();

        switch (tela) {
            case 1:
                pc.edPeca();
                System.out.println("\n");
                prosseguir();
                break;
            case 2:
                lt.edLote();
                System.out.println("\n");
                prosseguir();
                break;
            case 3:
                cl.edCliente();
                System.out.println("\n");
                prosseguir();
                break;
//            case 4:
//                ts.edServico();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 5:
//                ag.edAgendamento();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 6:
//                f.edFuncionario();
//                System.out.println("\n");
//                inicio();
//                break;
            case 0:
                inicio();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
                editar();
                break;
        }
    }

    public void apagar() {
        System.out.println("1. Peças\t 2. Lote de Óleo\t\t 3.Cliente");
        int tela = num.nextInt();

        switch (tela) {
            case 1:
                pc.delPeca();
                System.out.println("\n");
                prosseguir();
                break;
            case 2:
                lt.delLote();
                System.out.println("\n");
                prosseguir();
                break;
            case 3:
                cl.delCliente();
                System.out.println("\n");
                prosseguir();
                break;
//            case 4:
//                ts.delServico();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 5:
//                ag.delAgendamento();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 6:
//                f.delFuncionario();
//                System.out.println("\n");
//                inicio();
//                break;
            case 0:
                inicio();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
                apagar();
                break;
        }
    }

    public void consultar() {
        System.out.println("1. Peças\t 2. Lotes de Óleo\t\t 3. Clientes"
                + "\n0. Voltar");
        int tela = num.nextInt();

        switch (tela) {
            case 1:
                pc.consPeca();
                prosseguir();
                break;
            case 2:
                lt.consLote();
                System.out.println("\n");
                prosseguir();
                break;
            case 3:
                cl.consCliente();
                System.out.println("\n");
                prosseguir();
                break;
//            case 4:
//                ts.consServico();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 5:
//                ag.consAgendamento();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 6:
//                f.consFuncionario();
//                System.out.println("\n");
//                inicio();
//                break;
            case 0:
                inicio();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
                consultar();
                break;
        }
    }
//    public void relatorios(){
//        System.out.println("1. Peças");
//        int tela = num.nextInt();
//        
//        switch(tela){
//            case 1:
//                pc.addPeca();
//                System.out.println("\n");
//                inicio();
//                break;
//            case 0:
//                inicio();
//                break;
//        }
//    }

    // método para abrir pergunta antes de continuar o fluxo do sistema
    private void prosseguir() {
        System.out.println("\n\nPressione qualquer tecla para continuar");
        try {
            System.in.read();
            inicio();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
