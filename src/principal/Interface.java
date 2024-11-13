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
    
    //chamada dos m�todos principais
    // INTERFACE CONCLU�DA
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
                + "3. Apagar Itens\t\t 4.Consultar Itens\t5.Relat�rios\n"
                + "0. Sair");

        System.out.print("\nVoc� pode navegar pelo sistema"
                + " informando seus n�meros correspondentes");
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
                System.out.println("\nConsultas");
                consultar();
                break;
//            case 5:
//                System.out.println("Relat�rios dispon�veis");
//                relatorios();
//                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    public void cadastrar() {
        System.out.print("1. Pe�as\t2. Lotes de �leo\t3. Clientes\n"
                + "4. Servi�os\t5. Agendamento\t\t6. Funcion�rios\n"
                + "7. Tipo Servi�o\n"
                + "0. Voltar\n >>>>>  ");
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
        System.out.print("1. Pe�as\t 2. Lote de �leo\t 3. Cliente"
                + "\n4. Tipo Servi�o"
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
                System.out.println("Op��o inv�lida, tente novamente.");
                editar();
                break;
        }
    }

    public void apagar() {
        System.out.print("1. Pe�as\t 2. Lote de �leo\t 3.Cliente"
                + "\n4. Tipo Servi�o"
                + "\n 0. Voltar\n>>>>>  ");
        int tela = num.nextInt();
        
        
    public void addCliente() {
        boolean cad = false;

        System.out.println("Cadastrando novo cliente");

        System.out.print("Nome do Cliente: ");
        setNomeCliente(sc.nextLine());

        System.out.print("WhatsApp do Cliente: ");
        setWhatsappCliente(sc.nextLine());

        System.out.print("Modelo do Carro: ");
        setModeloCarro(sc.nextLine());

        System.out.print("Ano do Carro: ");
        setAnoCarro(sc.nextLine());

        cad = cl.cadastrarCliente(getNomeCliente(), getWhatsappCliente(), getModeloCarro(), getAnoCarro());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado com sucesso.");
        }
    }

    public void edCliente() {
        boolean ed = false;

        while (numeroValidado == null) {
            System.out.print("Digite o id do cliente que deseja editar: ");
            String clienteID = sc.nextLine();

            // Valida se a entrada � num�rica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inv�lido. Digite novamente.");
            } else {
                // Verifica se o cliente existe no banco de dados
                setIdCliente(numeroValidado);
                if (!validarCliente()) {
                    System.out.println("ID n�o encontrado. Tente novamente.");
                    numeroValidado = null;  // Redefine para continuar o loop
                }
            }
        }

        System.out.print("1. Nome do Cliente\t2. WhatsApp do Cliente"
                + "\n3. Modelo do Carro\t4. Ano do Carro\t\t5. Todos os campos\n0. Voltar");
        System.out.print("\n\nO que voc� deseja editar?  >>>>");

        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 1:
                System.out.print("Nome: ");
                setNomeCliente(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), getNomeCliente(), null, null, null);
                break;

            case 2:
                System.out.print("WhatsApp: ");
                setWhatsappCliente(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, getWhatsappCliente(), null, null);
                break;

            case 3:
                System.out.print("Modelo do carro: ");
                setModeloCarro(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, null, getModeloCarro(), null);
                break;

            case 4:
                System.out.print("Ano do carro: ");
                setAnoCarro(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, null, null, getAnoCarro());
                break;

            case 5:
                System.out.print("Nome: ");
                setNomeCliente(sc.nextLine());

                System.out.print("WhatsApp: ");
                setWhatsappCliente(sc.nextLine());

                System.out.print("Modelo do Carro: ");
                setModeloCarro(sc.nextLine());

                System.out.print("Ano do Carro: ");
                setAnoCarro(sc.nextLine());

                ed = cl.editarCliente(getIdCliente(), getNomeCliente(), getWhatsappCliente(), getModeloCarro(), getAnoCarro());
                break;

            case 0:
                edCliente();
                break;

            default:
                System.out.println("Op��o inv�lida. Tente novamente.");
                return;
        }

        if (ed == false) {
            System.out.println("Erro ao editar o cliente.");
        } else {
            System.out.println("Edi��o realizada com sucesso.");
        }
    }

    public void consCliente() {
        cl.listarCliente();
    }

    public void delCliente() {
        boolean del = false;
        String clienteID = "";

        // Loop para validar a entrada num�rica e a exist�ncia no banco
        while (numeroValidado == null || !validarCliente()) {
            System.out.print("Digite o ID do cliente: ");
            clienteID = sc.nextLine();

            // Tenta validar se a entrada � num�rica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inv�lido. Digite apenas n�meros.");
            } else {
                // com o o ID num�rico, verifica no banco
                setIdCliente(numeroValidado);
                if (!validarCliente()) {
                    System.out.println("Cliente n�o encontrado. Tente novamente.");
                    numeroValidado = null;
                }
            }
        }
        
        del = cl.apagarCliente(getIdCliente());
        if (del == false) {
            System.out.println("Erro ao apagar a cliente.");
        } else {
            System.out.println("Cadastro exclu�do.");
        }
    }

// -------------- M�TODOS DE APOIO --------------
    public boolean validarCliente() {
        if (cl.validaID(getIdCliente()) == 1) {
            return true;
        } else {
            return false;
        }
    }

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
                System.out.println("Op��o inv�lida, tente novamente.");
                apagar();
                break;
        }
    }

    public void consultar() {
        System.out.print("1. Pe�as\t 2. Lotes de �leo\t 3. Clientes"
                + "\n4. Servi�o"
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
                System.out.println("Op��o inv�lida, tente novamente.");
                consultar();
                break;
        }
    }
//    public void relatorios(){
//        System.out.println("1. Pe�as");
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

    // m�todo para abrir pergunta antes de continuar o fluxo do sistema
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
