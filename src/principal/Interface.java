package principal;

import java.io.IOException;
import java.util.Scanner;

public class Interface {
    Scanner num = new Scanner(System.in);
    
    //chamada dos métodos principais
    // INTERFACE CONCLUÍDA
    Lote lt = new Lote();
    Peca pc = new Peca();
    Cliente cl = new Cliente();
    TipoServico ts = new TipoServico();
    
    // INTERFACE PENDENTE
    Agendamento ag = new Agendamento();
    Funcionario f = new Funcionario();

    // ------------------------------------------------
    public void inicio() {
        System.out.println("\tBem-Vindo ao Auto Agenda");
        System.out.println("1. Cadastrar Itens\t 2. Editar Itens\n"
                + "3. Apagar Itens\t\t 4.Consultar Itens\n"
                + "0. Sair");

        System.out.print("\nVocê pode navegar pelo sistema"
                + " informando seus números correspondentes");
        System.out.print("\n>>>>>  ");

        int opcao = num.nextInt();
        switch (opcao) {
            case 1:
                System.out.println("\nCadastros");
                cadastrar();
                break;
            case 2:
                System.out.println("\nEditar");
                editar();
                break;
            case 3:
                System.out.println("\nApagar");
                apagar();
                break;
            case 4:
                System.out.println("\nConsultar");
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
        System.out.print("1. Peças\t2. Lotes de Óleo\t3. Clientes\n"
                + "4. Serviços\t5. Agendamento\t\t6. Funcionários\n"
                + "\n0. Voltar\n >>>>>  ");
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
            case 4:
                ts.addServico();
                System.out.println("\n");
                prosseguir();
                break;
            case 5:
                ag.addAgendamento();
                System.out.println("\n");
                inicio();
                break;
            case 6:
                f.addFuncionario();
                System.out.println("\n");
                prosseguir();
                break;
            case 7:
                ts.addServico();
                System.out.println("\n");
                prosseguir();
                break;
            case 0:
                inicio();
                break;
        }
    }

    public void editar() {
        System.out.print("1. Peças\t 2. Lote de Óleo\t 3. Cliente"
                + "\n4. Tipo Serviço  5. Agendamento\t\t6. Funcionário "
                + "\n0. Voltar\n >>>>>  ");
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
            case 4:
                ts.edServico();
                System.out.println("\n");
                prosseguir();
                break;
            case 5:
                ag.edAgendamento();
                System.out.println("\n");
                prosseguir();
                break;
            case 6:
                f.edFuncionario();
                System.out.println("\n");
                prosseguir();
                break;
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
        System.out.print("1. Peças\t 2. Lote de Óleo\t 3.Cliente"
                + "\n4. Tipo Serviço  5. Agendamento\t6. Funcionário"
                + "\n0. Voltar\n>>>>>  ");
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
            case 4:
                ts.delServico();
                System.out.println("\n");
                prosseguir();
                break;
            case 5:
                ag.delAgendamento();
                System.out.println("\n");
                prosseguir();
                break;
            case 6:
                f.delFuncionario();
                System.out.println("\n");
                prosseguir();
                break;
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
        System.out.print("1. Peças\t 2. Lotes de Óleo\t 3. Clientes"
                + "\n4. Serviço \t 5. Agendamento \t 6.Funcionário"
                + "\n0. Voltar\n >>>>> ");
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
            case 4:
                ts.consServico();
                System.out.println("\n");
                prosseguir();
                break;
            case 5:
                ag.consAgendamento();
                System.out.println("\n");
                prosseguir();
                break;
            case 6:
                f.consFuncionario();
                System.out.println("\n");
                prosseguir();
                break;
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
        System.out.println("\nPressione ENTER para continuar");
        try {
            System.in.read();
            inicio();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
