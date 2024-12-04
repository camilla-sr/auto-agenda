package principal;

import include.Helper;
import java.io.IOException;
import java.util.Scanner;

public class Interface {
    Scanner sc = new Scanner(System.in);
    Integer opcaoValida = null;
    Integer telaValida = null;
    Helper h = new Helper();

    //chamada dos métodos principais
    Lote lt = new Lote();
    Peca pc = new Peca();
    Cliente cl = new Cliente();
    TipoServico ts = new TipoServico();
    Agendamento ag = new Agendamento();
    Funcionario f = new Funcionario();
    Estoque e = new Estoque();

    // ------------------------------------------------
    public void inicio() {
        System.out.println("\tBem-Vindo ao Auto Agenda");
        System.out.println("1. Cadastrar Itens\t 2. Editar Itens\t 3. Apagar Itens\n"
                + "4. Consultar Itens\t 5. Concluir Agendamento 6. Relatórios\n"
                + "0. Sair");

        System.out.print("\nVocê pode navegar pelo sistema"
                + " informando seus números correspondentes");
        System.out.print("\n>>>>>  ");

        String opcao = sc.nextLine();
        opcaoValida = h.isNumeric(opcao);
        while (opcaoValida == null) {
            System.out.println("Opção inválida. Tente novamente\n");
            prosseguir();
            opcaoValida = null;
        }

        switch (opcaoValida) {
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
            case 5:
                System.out.println("\nConcluir agendamentos");
                ag.finalizar();
                prosseguir();
                break;
            case 6:
                System.out.println("\nGerar Relatórios");
                ag.relatorios();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
                editar();
                break;
        }
    }

    public void cadastrar() {
        System.out.print("1. Peças\t2. Lotes de Óleo\t3. Clientes\n"
                + "4. Serviços\t5. Agendamento\t\t6. Funcionários"
                + "\n0. Voltar\n >>>>>  ");

        String tela = sc.nextLine();
        telaValida = h.isNumeric(tela);
        while (telaValida == null) {
            System.out.println("Opção inválida. Tente novamente\n");
            prosseguir();
            telaValida = null;
        }

        switch (telaValida) {
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
            default:
                System.out.println("Opção inválida, tente novamente.");
                editar();
                break;
        }
    }

    public void editar() {
        System.out.print("1. Peças\t 2. Lote de Óleo\t 3. Cliente"
                + "\n4. Tipo Serviço  5. Agendamento\t\t 6. Funcionário "
                + "\n0. Voltar\n >>>>>  ");

        String tela = sc.nextLine();
        telaValida = h.isNumeric(tela);
        while (telaValida == null) {
            System.out.println("Opção inválida. Tente novamente\n");
            prosseguir();
            telaValida = null;
        }

        switch (telaValida) {
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
        System.out.print("1. Peças\t 2. Lote de Óleo\t 3. Cliente"
                + "\n4. Tipo Serviço  5. Agendamento\t\t 6. Funcionário"
                + "\n0. Voltar\n>>>>>  ");

        String tela = sc.nextLine();
        telaValida = h.isNumeric(tela);
        while (telaValida == null) {
            System.out.println("Opção inválida. Tente novamente\n");
            prosseguir();
            telaValida = null;
        }

        switch (telaValida) {
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
                + "\n4. Serviço \t 5. Agendamento \t 6. Funcionário"
                + "\n7. Estoque \t 0. Voltar\n >>>>> ");

        String tela = sc.nextLine();
        telaValida = h.isNumeric(tela);
        while (telaValida == null) {
            System.out.println("Opção inválida. Tente novamente\n");
            prosseguir();
            telaValida = null;
        }

        switch (telaValida) {
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
            case 7:
                e.consEstoque();
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
