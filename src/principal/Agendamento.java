package principal;

import dao.AgendamentoDAO;
import dao.TipoServicoDAO;
import dao.FuncionarioDAO;
import dao.ClienteDAO;
import include.Helper;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Agendamento {
    private int idAgendamento;
    private int cliente;
    private int servico;
    private int funcionario;
    private String dataCadastro;
    private String dataPrevisaoEntrega;
    private String dataConclusao;
    private String status;
    private String observacao;

    LocalDate hoje = LocalDate.now();  //retorna data atual com base no servidor

    AgendamentoDAO ag = new AgendamentoDAO();
    TipoServicoDAO ts = new TipoServicoDAO();
    FuncionarioDAO f = new FuncionarioDAO();
    ClienteDAO cl = new ClienteDAO();

    Helper h = new Helper();
    Scanner sc = new Scanner(System.in, "utf8");
    Scanner num = new Scanner(System.in);

    public void addAgendamento() {
        boolean cad = false;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // Formata a data como String
        String dataFormatada = hoje.format(formato);
        setDataCadastro(dataFormatada);

        System.out.print("\tAgendamento de Serviços\n");
        if (cl.verificaRegistro()== 0) {
            System.out.println("\nNão há clientes cadastrados para realizar agendamento");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            cl.listaEdicao();
        }

        //cliente / serviço / funcionário
        Integer clienteValidado = null;

        while (clienteValidado == null) {
            System.out.print("Cliente: ");
            String novocliente = sc.nextLine(); // Captura a entrada do cliente

            // Valida se a entrada é numérica
            clienteValidado = h.isNumeric(novocliente);

            if (clienteValidado == null) {
                System.out.println("Apenas números.\n");
            } else {
                // Verifica se o cliente é válido
                boolean clienteValido = cl.validaID(clienteValidado); // Verifica se o ID é válido

                if (!clienteValido) {
                    System.out.println("Cliente não encontrado. Tente novamente.");
                    clienteValidado = null;
                } else {
                    setCliente(clienteValidado);
                }
            }
        }

        if (ts.verificaRegistro()== 0) {
            System.out.println("\nNão há serviços cadastrados para realizar agendamento");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            ts.listaEdicao();
        }

        Integer servicoValidado = null; // Reinicia a variável

        while (servicoValidado == null) {
            System.out.print("Novo tipo de serviço: ");
            String novoservico = sc.nextLine(); // Captura a entrada do tipo de serviço

            // Valida se a entrada é numérica
            servicoValidado = h.isNumeric(novoservico);

            if (servicoValidado == null) {
                System.out.println("Apenas números.\n");
            } else {
                // Verifica se o serviço é válido
                boolean servicoValido = ts.validaID(servicoValidado); // Verifica se o ID do serviço é válido

                if (!servicoValido) {
                    System.out.println("Serviço não encontrado. Tente novamente.\n");
                    servicoValidado = null; // Redefine para continuar o loop
                } else {
                    setServico(servicoValidado);
                }
            }
        }

        if (f.verificaRegistro() == 0) {
            System.out.println("\nNão há funcionários cadastrados para realizar agendamento");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            f.listaEdicao();
        }

        Integer funcionarioValidado = null; // Reinicia a variável

        while (funcionarioValidado == null) {
            System.out.print("Novo funcionário: ");
            String novoFuncionario = sc.nextLine(); // Captura a entrada do funcionário

            // Valida se a entrada é numérica
            funcionarioValidado = h.isNumeric(novoFuncionario);

            if (funcionarioValidado == null) {
                System.out.println("Apenas números.\n");
            } else {
                // Verifica se o funcionário é válido
                boolean funcionarioValido = f.validaID(funcionarioValidado); // Verifica se o ID do funcionário é válido

                if (!funcionarioValido) {
                    System.out.println("Funcionário não encontrado. Tente novamente.\n");
                    funcionarioValidado = null; // Redefine para continuar o loop
                } else {
                    setFuncionario(funcionarioValidado);
                }
            }
        }

        while (true) {
            System.out.print("Data de previsão para conclusão (dd/MM/yyyy): ");
            String dataPrev = sc.nextLine();

            String dataValidaP = h.dataPadraoBanco(dataPrev);

            if (dataValidaP == null) {
                System.out.println("Formato inválido. Tente novamente\n");
            } else {
                setDataPrevisaoEntrega(dataValidaP);
                break; // Sai do loop se a data for válida
            }
        }

        System.out.print("\nDeseja fazer alguma observação?  1-SIM   2-NÃO\n");
        int escolha = num.nextInt();
        if (escolha == 2) {
            setObservacao("");
        } else {
            System.out.println("Digite a observação: \n");
            setObservacao(sc.nextLine());
        }

        cad = ag.cadastrarAgendamento(getCliente(), getServico(), getFuncionario(),
                getDataCadastro(), getDataPrevisaoEntrega(), getObservacao());
        if (cad == false) {
            System.out.println("Algo deu errado");
        } else {
            System.out.println("Cadastro realizado");
        }
    }

    public void edAgendamento() {
        // começo retirando qualquer valor dentro dos atributos
        setIdAgendamento(0);
        setCliente(0);
        setServico(0);
        setFuncionario(0);
        setDataPrevisaoEntrega(null);
        setObservacao(null);

        boolean ed = false;
        if (ag.verificaRegistro() == 0) {
            System.out.println("\t\t\tNenhum dado encontrado");
            return;
        } else {
            ag.listaEdicao();
        }

        do {
            System.out.print("Digite o ID do agendamento: ");
            String inputID = sc.nextLine();
            Integer IDvalidado = h.isNumeric(inputID);

            if (IDvalidado == null) {
                System.out.println("Apenas números");
                continue;
            }

            setIdAgendamento(IDvalidado);
            if (!validarAgendamento()) {
                System.out.println("Agendamento não encontrado. Tente novamente.\n");
            }
        } while (!validarAgendamento());

        System.out.println("\n1. Cliente atendido \t 2. Serviço a ser prestado \t3. Funcionário responsável"
                + "\n4. Previsão de entrega\t 5. Observação \t\t\t6. Todos os campos"
                + "\n0. Voltar");
        System.out.print("O que você deseja editar? >>>>> ");
        int opcaoEdicao = num.nextInt();
        System.out.println("");

        switch (opcaoEdicao) {
            case 1:
                if (cl.verificaRegistro() == 0) {
                    System.out.println("\nNão há clientes cadastrados para realizar agendamento");
                    System.out.println("Retornando para o menu principal.");
                    return;
                } else {
                    cl.listaEdicao();
                }

                Integer clienteValidado = null; // Reinicia a variável

                while (clienteValidado == null) {
                    System.out.print("Novo cliente: ");
                    String novocliente = sc.nextLine(); // Captura a entrada do cliente

                    // Valida se a entrada é numérica
                    clienteValidado = h.isNumeric(novocliente);

                    if (clienteValidado == null) {
                        System.out.println("Apenas números.\n");
                    } else {
                        // Verifica se o cliente é válido
                        boolean clienteValido = cl.validaID(clienteValidado); // Verifica se o ID é válido

                        if (!clienteValido) {
                            System.out.println("Cliente não encontrado. Tente novamente.");
                            clienteValidado = null; // Redefine para continuar o loop
                        }
                    }
                }

                setCliente(clienteValidado); // Atribui o cliente validado
                ed = ag.editarAgendamento(getIdAgendamento(), getCliente(), getServico(), getFuncionario(), getDataPrevisaoEntrega(), getObservacao());
                break;

            case 2:
                if (ts.verificaRegistro() == 0) {
                    System.out.println("\nNão há serviços cadastrados para realizar agendamento");
                    System.out.println("Retornando para o menu principal.");
                    return;
                } else {
                    ts.listaEdicao();
                }

                Integer servicoValidado = null; // Reinicia a variável

                while (servicoValidado == null) {
                    System.out.print("Novo tipo de serviço: ");
                    String novoservico = sc.nextLine(); // Captura a entrada do tipo de serviço

                    // Valida se a entrada é numérica
                    servicoValidado = h.isNumeric(novoservico);

                    if (servicoValidado == null) {
                        System.out.println("Apenas números.\n");
                    } else {
                        // Verifica se o serviço é válido
                        boolean servicoValido = ts.validaID(servicoValidado); // Verifica se o ID do serviço é válido

                        if (!servicoValido) {
                            System.out.println("Serviço não encontrado. Tente novamente.\n");
                            servicoValidado = null; // Redefine para continuar o loop
                        }
                    }
                }

                setServico(servicoValidado); // Atribui o serviço validado
                ed = ag.editarAgendamento(getIdAgendamento(), getCliente(), getServico(), getFuncionario(), getDataPrevisaoEntrega(), getObservacao());
                break;

            case 3:
                if (f.verificaRegistro()== 0) {
                    System.out.println("\nNão há funcionários cadastrados para realizar agendamento");
                    System.out.println("Retornando para o menu principal.");
                    return;
                } else {
                    f.listaEdicao();
                }

                Integer funcionarioValidado = null; // Reinicia a variável

                while (funcionarioValidado == null) {
                    System.out.print("Novo funcionário: ");
                    String novoFuncionario = sc.nextLine(); // Captura a entrada do funcionário

                    // Valida se a entrada é numérica
                    funcionarioValidado = h.isNumeric(novoFuncionario);

                    if (funcionarioValidado == null) {
                        System.out.println("Apenas números.\n");
                    } else {
                        // Verifica se o funcionário é válido
                        boolean funcionarioValido = f.validaID(funcionarioValidado); // Verifica se o ID do funcionário é válido

                        if (!funcionarioValido) {
                            System.out.println("Funcionário não encontrado. Tente novamente.\n");
                            funcionarioValidado = null; // Redefine para continuar o loop
                        }
                    }
                }

                setFuncionario(funcionarioValidado); // Atribui o funcionário validado
                ed = ag.editarAgendamento(getIdAgendamento(), getCliente(), getServico(), getFuncionario(), getDataPrevisaoEntrega(), getObservacao());
                break;

            case 4:
                // Editar apenas a data de previsão de entrega
                while (true) {
                    System.out.print("Nova previsão de entrega (dd/MM/yyyy): ");
                    String novaDataPrev = sc.nextLine();

                    String dataValidaP = h.dataPadraoBanco(novaDataPrev);

                    if (dataValidaP == null) {
                        System.out.println("Formato inválido. Tente novamente\n");
                    } else {
                        setDataPrevisaoEntrega(dataValidaP);
                        break; // Sai do loop se a data for válida
                    }
                }
                ed = ag.editarAgendamento(getIdAgendamento(), getCliente(), getServico(), getFuncionario(), getDataPrevisaoEntrega(), getObservacao());
                break;

            case 5:
                // Editar apenas a observação
                System.out.print("Nova observação: ");
                setObservacao(sc.nextLine());

                while (getObservacao() == null) {
                    System.out.print("Nova observação: ");
                    setObservacao(sc.nextLine());
                    break;
                }
                ed = ag.editarAgendamento(getIdAgendamento(), getCliente(), getServico(), getFuncionario(), getDataPrevisaoEntrega(), getObservacao());
                break;

            case 6:
                // Editar todos os campos
                if (cl.verificaRegistro() == 0) {
                    System.out.println("\nNão há clientes cadastrados para realizar agendamento");
                    System.out.println("Retornando para o menu principal.");
                    return;
                } else {
                    cl.listaEdicao();
                }

                clienteValidado = null;
                while (clienteValidado == null) {
                    System.out.print("Novo cliente: ");
                    String novoCliente = sc.nextLine();

                    clienteValidado = h.isNumeric(novoCliente);
                    if (clienteValidado == null) {
                        System.out.println("Apenas números.\n");
                    } else if (!cl.validaID(clienteValidado)) {
                        System.out.println("Cliente não encontrado. Tente novamente.\n");
                        clienteValidado = null;
                    }
                }
                setCliente(clienteValidado);

                // Validação do serviço
                if (ts.verificaRegistro() == 0) {
                    System.out.println("\nNão há serviços cadastrados para realizar agendamento");
                    System.out.println("Retornando para o menu principal.");
                    return;
                } else {
                    ts.listaEdicao();
                }
                servicoValidado = null;
                while (servicoValidado == null) {
                    System.out.print("Novo serviço: ");
                    String novoServico = sc.nextLine();

                    servicoValidado = h.isNumeric(novoServico);
                    if (servicoValidado == null) {
                        System.out.println("Apenas números.\n");
                    } else if (!ts.validaID(servicoValidado)) {
                        System.out.println("Serviço não encontrado. Tente novamente.\n");
                        servicoValidado = null;
                    }
                }
                setServico(servicoValidado);

                // Validação do funcionário
                if (f.verificaRegistro()== 0) {
                    System.out.println("\nNão há funcionários cadastrados para realizar agendamento");
                    System.out.println("Retornando para o menu principal.");
                    return;
                } else {
                    f.listaEdicao();
                }
                funcionarioValidado = null;
                while (funcionarioValidado == null) {
                    System.out.print("Novo funcionário: ");
                    String novoFuncionario = sc.nextLine();

                    funcionarioValidado = h.isNumeric(novoFuncionario);
                    if (funcionarioValidado == null) {
                        System.out.println("Apenas números.\n");
                    } else if (!f.validaID(funcionarioValidado)) {
                        System.out.println("Funcionário não encontrado. Tente novamente.\n");
                        funcionarioValidado = null;
                    }
                }
                setFuncionario(funcionarioValidado);

                // Validação da data de previsão de entrega
                while (true) {
                    System.out.print("Nova previsão de entrega (dd/MM/yyyy): ");
                    String novaPrevisaoEntrega = sc.nextLine();

                    String dataValidaPrevisao = h.dataPadraoBanco(novaPrevisaoEntrega);
                    if (dataValidaPrevisao != null) {
                        setDataPrevisaoEntrega(dataValidaPrevisao);
                        break;
                    } else {
                        System.out.println("Formato inválido. Tente novamente.\n");
                    }
                }

                // Captura da nova observação
                System.out.print("Nova observação: ");
                setObservacao(sc.nextLine());

                // Edição no agendamento
                ed = ag.editarAgendamento(getIdAgendamento(), getCliente(), getServico(),
                        getFuncionario(), getDataPrevisaoEntrega(), getObservacao()
                );
                break;

            case 0:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                return;
        }

        if (ed == false) {
            System.out.println("Erro ao editar o agendamento.");
        } else {
            System.out.println("Edição realizada com sucesso.");
        }
    }

    public void consAgendamento() {
        ag.listarAgendamento();
    }

    public void delAgendamento() {
        boolean del = false;
        Integer agendamentoValidado = null; // Reinicia a variável

        if (ag.verificaRegistro() == 0) {
            System.out.println("\t\t\tNenhum dado encontrado");
            return;
        }

        while (agendamentoValidado == null) {
            System.out.print("Digite o ID do agendamento: ");
            String agenda = sc.nextLine(); // Captura a entrada do agendamento

            // Valida se a entrada é numérica
            agendamentoValidado = h.isNumeric(agenda);

            if (agendamentoValidado == null) {
                System.out.println("Apenas números.\n");
            } else {
                // Verifica se o agendamento é válido
                boolean agendamentoValido = ag.validaID(agendamentoValidado); // Verifica se o ID do agendamento é válido

                if (!agendamentoValido) {
                    System.out.println("Agendamento não encontrado. Tente novamente.\n");
                    agendamentoValidado = null; // Redefine para continuar o loop
                }
            }
        }
        // verifico se o id é válido no banco
        setIdAgendamento(agendamentoValidado);
        del = ag.apagarAgendamento(getIdAgendamento());
        if (del == false) {
            System.out.println("Erro ao apagar o agendamento.");
        } else {
            System.out.println("Agendamento excluído.");
        }
    }
    
    public void finalizar(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // Formata a data como String
        String datastr = hoje.format(formato);
        setDataConclusao(datastr);
        
        if (ag.verificaRegistro()== 0) {
            System.out.println("\nNão há serviços cadastrados para realizar agendamento");
            System.out.println("Retornando para o menu principal.");
            return;
        } else {
            ag.listaEdicao();
        }
        
        do {
            System.out.print("Digite o ID do agendamento: ");
            String inputID = sc.nextLine();
            Integer IDvalidado = h.isNumeric(inputID);

            if (IDvalidado == null) {
                System.out.println("ID inválido. Tente novamente.\n");
                continue;
            }
            setIdAgendamento(IDvalidado);
            if (!validarAgendamento()) {
                System.out.println("Agendamento não encontrado. Tente novamente.\n");
            }
        } while (!validarAgendamento());
        
        boolean fn = false;
        fn = ag.finalizarAgendamento(getIdAgendamento(), getDataConclusao());
        if (fn == false) {
            System.out.println("Erro ao finalizar agendamento.");
        } else {
            System.out.println("Agendamento Finalizado.");
        }        
    }

    // -------------- MÉTODOS DE APOIO ---------------
    public boolean validarAgendamento() {
        if (ag.validaID(getIdAgendamento())) {
            return true;
        } else {
            return false;
        }
    }

    // -------------- GETTERS E SETTERS --------------
    public int getIdAgendamento() {
        return idAgendamento;
    }
    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }
    public int getCliente() {
        return cliente;
    }
    public void setCliente(int cliente) {
        this.cliente = cliente;
    }
    public int getServico() {
        return servico;
    }
    public void setServico(int servico) {
        this.servico = servico;
    }
    public int getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(int funcionario) {
        this.funcionario = funcionario;
    }
    public String getDataCadastro() {
        return dataCadastro;
    }
    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public String getDataPrevisaoEntrega() {
        return dataPrevisaoEntrega;
    }
    public void setDataPrevisaoEntrega(String dataPrevisaoEntrega) {
        this.dataPrevisaoEntrega = dataPrevisaoEntrega;
    }
    public String getDataConclusao() {
        return dataConclusao;
    }
    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
// ------------------------------------------------    
}
