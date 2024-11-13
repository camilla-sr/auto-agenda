package principal;

import dao.ClienteDAO;
import include.Helper;
import java.util.Scanner;

public class Cliente {

    Integer numeroValidado = null;
    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);
    ClienteDAO cl = new ClienteDAO();

    private int idCliente;
    private String nomeCliente;
    private String whatsappCliente;
    private String modeloCarro;
    private String anoCarro;

    public void addCliente() {
        boolean cad = false;

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
            System.out.print("Digite o código da peça que deseja editar: ");
            String clienteID = sc.nextLine();

            // Valida se a entrada é numérica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inválido. Digite novamente.");
            } else {
                // Verifica se o cliente existe no banco de dados
                setIdCliente(numeroValidado);
                if (!validarCliente()) {
                    System.out.println("ID não encontrado. Tente novamente.");
                    numeroValidado = null;  // Redefine para continuar o loop
                }
            }
        }

        System.out.println("O que você deseja editar?");
        System.out.println("1. Nome do Cliente\t2. WhatsApp do Cliente"
                + "\n3. Modelo do Carro\t4. Ano do Carro\t5. Todos os campos\n0. Voltar");

        int opcaoEdicao = num.nextInt();
        num.nextLine(); // Consome nova linha após o número

        switch (opcaoEdicao) {
            case 1:
                System.out.print("Digite o novo nome do cliente: ");
                setNomeCliente(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), getNomeCliente(), null, null, null);
                break;

            case 2:
                System.out.print("Digite o novo WhatsApp do cliente: ");
                setWhatsappCliente(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, getWhatsappCliente(), null, null);
                break;

            case 3:
                System.out.print("Digite o novo modelo do carro: ");
                setModeloCarro(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, null, getModeloCarro(), null);
                break;

            case 4:
                System.out.print("Digite o novo ano do carro: ");
                setAnoCarro(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, null, null, getAnoCarro());
                break;

            case 5:
                System.out.print("Digite o novo nome do cliente: ");
                setNomeCliente(sc.nextLine());

                System.out.print("Digite o novo WhatsApp do cliente: ");
                setWhatsappCliente(sc.nextLine());

                System.out.print("Digite o novo modelo do carro: ");
                setModeloCarro(sc.nextLine());

                System.out.print("Digite o novo ano do carro: ");
                setAnoCarro(sc.nextLine());

                ed = cl.editarCliente(getIdCliente(), getNomeCliente(), getWhatsappCliente(), getModeloCarro(), getAnoCarro());
                break;

            case 0:
                return;

            default:
                System.out.println("Opção inválida. Tente novamente.");
                return;
        }

        if (ed == false) {
            System.out.println("Erro ao editar o cliente.");
        } else {
            System.out.println("Edição realizada com sucesso.");
        }
    }

    public void consCliente() {
        cl.listarCliente();
    }

    public void delCliente() {
        boolean del = false;

        System.out.print("Digite o ID do cliente que deseja excluir: ");
        int clienteID = num.nextInt();
        setIdCliente(clienteID);

        while (!validarCliente()) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            clienteID = num.nextInt();
            setIdCliente(clienteID);
        }

        del = cl.apagarCliente(getIdCliente());
        if (del == false) {
            System.out.println("Erro ao excluir o cliente.");
        } else {
            System.out.println("Cliente excluído com sucesso.");
        }
    }

// -------------- MÉTODOS DE APOIO --------------
    public boolean validarCliente() {
        return cl.validaID(getIdCliente()) == 1;
    }

    // -------------- GETTERS E SETTERS --------------
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getWhatsappCliente() {
        return whatsappCliente;
    }

    public void setWhatsappCliente(String whatsappCliente) {
        this.whatsappCliente = whatsappCliente;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public String getAnoCarro() {
        return anoCarro;
    }

    public void setAnoCarro(String anoCarro) {
        this.anoCarro = anoCarro;
    }
    // ------------------------------------------------
}
