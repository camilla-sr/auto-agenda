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
    private int anoCarro;

    public void addCliente() {
        boolean cad = false;

        System.out.println("\tCadastrando novo cliente\n");

        System.out.print("Nome do Cliente: ");
        setNomeCliente(sc.nextLine());
        
        do{
            System.out.print("\nWhatsApp do Cliente (formato: (99) 99999-9999): ");
            whatsappCliente = sc.nextLine();
            
            if(whatsappCliente.length() > 15){
                System.out.println("Número inválido. Tente novamente");
            }else{
                setWhatsappCliente(whatsappCliente);
                break;
            }
        }while(whatsappCliente.length() > 15);
      
        
        System.out.print("\nModelo do Carro: ");
        setModeloCarro(sc.nextLine());

        do{
            System.out.print("\nAno do carro: ");
            String ano = sc.nextLine();

            // Valida se a entrada é numérica
            numeroValidado = h.isNumeric(ano);

            if (numeroValidado == null || ano.length() >= 5) {
                System.out.println("Formato inválido. Tente novamente.");
                numeroValidado = null;
            }
        }while (numeroValidado == null);

        setAnoCarro(numeroValidado);
        cad = cl.cadastrarCliente(getNomeCliente(), getWhatsappCliente(), getModeloCarro(), getAnoCarro());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado com sucesso.");
        }
    }

    public void edCliente() {
        boolean ed = false;
        numeroValidado = null;

        if (cl.verificaRegistro() == 0) {
            System.out.println("\nNão há clientes cadastrados");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            cl.listaEdicao();
        }

        while (numeroValidado == null) {
            System.out.print("Digite o id do cliente que deseja editar: ");
            String clienteID = sc.nextLine();

            // Valida se a entrada é numérica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inválido. Digite novamente.\n");
            } else {
                // Verifica se o cliente existe no banco de dados
                setIdCliente(numeroValidado);
                if (!validarCliente()) {
                    System.out.println("ID não encontrado. Tente novamente.\n");
                    numeroValidado = null;  // Redefine para continuar o loop
                }
            }
        }

        System.out.print("\n1. Nome do Cliente\t2. WhatsApp do Cliente"
                + "\n3. Modelo do Carro\t4. Ano do Carro\t\t5. Todos os campos\n0. Voltar");
        System.out.print("\n\nO que você deseja editar?  >>>> ");

        int opcaoEdicao = num.nextInt();

        switch (opcaoEdicao) {
            case 0:
                if (opcaoEdicao == 0) {
                    System.out.println("Operação cancelada");
                    return;
                }
                break;
            case 1:
                System.out.print("Nome: ");
                setNomeCliente(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), getNomeCliente(), null, null, 0);
                break;

            case 2:
                do{
                    System.out.print("\nWhatsApp do Cliente (formato: (99) 99999-9999): ");
                    whatsappCliente = sc.nextLine();

                    if(whatsappCliente.length() > 15){
                        System.out.println("Número inválido. Tente novamente");
                    }else{
                        setWhatsappCliente(whatsappCliente);
                        break;
                    }
                }while(whatsappCliente.length() > 15);

            case 3:
                System.out.print("Modelo do carro: ");
                setModeloCarro(sc.nextLine());
                ed = cl.editarCliente(getIdCliente(), null, null, getModeloCarro(), 0);
                break;

            case 4:
                numeroValidado = null;
                
                do{
                    System.out.print("\nAno do carro: ");
                    String ano = sc.nextLine();

                    // Valida se a entrada é numérica
                    numeroValidado = h.isNumeric(ano);

                    if (numeroValidado == null || ano.length() >= 5) {
                        System.out.println("Formato inválido. Tente novamente.\n");
                        numeroValidado = null;
                    }
                }while (numeroValidado == null);

                setAnoCarro(numeroValidado); // Usa o valor validado
                ed = cl.editarCliente(getIdCliente(), null, null, null, getAnoCarro());
                break;

            case 5:
                System.out.print("Nome: ");
                setNomeCliente(sc.nextLine());

                do{
                    System.out.print("\nWhatsApp do Cliente (formato: (99) 99999-9999): ");
                    whatsappCliente = sc.nextLine();

                    if(whatsappCliente.length() > 15){
                        System.out.println("Número inválido. Tente novamente");
                    }else{
                        setWhatsappCliente(whatsappCliente);
                        break;
                    }
                }while(whatsappCliente.length() > 15);

                System.out.print("Modelo do Carro: ");
                setModeloCarro(sc.nextLine());

                do{
                    System.out.print("\nAno do carro: ");
                    String ano = sc.nextLine();

                    // Valida se a entrada é numérica
                    numeroValidado = h.isNumeric(ano);

                    if (numeroValidado == null || ano.length() >= 5) {
                        System.out.println("Formato inválido. Tente novamente.");
                        numeroValidado = null;
                    }
                }while (numeroValidado == null);

                ed = cl.editarCliente(getIdCliente(), getNomeCliente(), getWhatsappCliente(), getModeloCarro(), getAnoCarro());
                break;
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
        numeroValidado = null;
        String clienteID = "";
        if (cl.verificaRegistro() == 0) {
            System.out.println("\nNão há clientes cadastrados");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            cl.listaEdicao();
        }

        // Loop para validar a entrada numérica e a existência no banco
        while (numeroValidado == null || !validarCliente()) {
            System.out.print("Digite o ID do cliente: ");
            clienteID = sc.nextLine();

            // Tenta validar se a entrada é numérica
            numeroValidado = h.isNumeric(clienteID);

            if (numeroValidado == null) {
                System.out.println("ID inválido. Digite apenas números.\n");
            } else {
                // com o o ID numérico, verifica no banco
                setIdCliente(numeroValidado);
                if (!validarCliente()) {
                    System.out.println("Cliente não encontrado. Tente novamente.\n");
                    numeroValidado = null;
                }
            }
        }

        del = cl.apagarCliente(getIdCliente());
        if (del == false) {
            System.out.println("Dado em uso, não é possível excuir");
        } else {
            System.out.println("Cadastro excluído.");
        }
    }

// -------------- MÉTODOS DE APOIO --------------
    public boolean validarCliente() {
        if (cl.validaID(getIdCliente())) {
            return true;
        } else {
            return false;
        }
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
    public int getAnoCarro() {
        return anoCarro;
    }
    public void setAnoCarro(int anoCarro) {
        this.anoCarro = anoCarro;
    }
    // ------------------------------------------------
}
