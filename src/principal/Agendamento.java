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

    LocalDate hoje = LocalDate.now();

    Integer numeroValidado = null;
    AgendamentoDAO ag = new AgendamentoDAO();
    TipoServicoDAO ts = new TipoServicoDAO();
    FuncionarioDAO f = new FuncionarioDAO();
    ClienteDAO cl = new ClienteDAO();

    Helper h = new Helper();
    Scanner sc = new Scanner(System.in);
    Scanner num = new Scanner(System.in);

    public void addAgendamento() {
        boolean cad = false;

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // Formata a data como String
        String dataFormatada = hoje.format(formato);
        setDataCadastro(dataFormatada);

        System.out.print("\n\tAgendamento de Serviços");

        //cliente / serviço / funcionário
        cl.listaEdicao();
        System.out.print("\nInforme o ID do cliente: ");
        int clienteID = num.nextInt();
        if (cl.validaID(clienteID)) {
            setCliente(clienteID);
        }

        ts.listaEdicao();
        System.out.print("\nInforme o ID do serviço: ");
        int servicoID = num.nextInt();
        if (ts.validaID(servicoID)) {
            setServico(servicoID);
        }

        f.listaEdicao();
        System.out.print("\nInforme o ID do funcionário: ");
        int funcionarioID = num.nextInt();
        if (cl.validaID(funcionarioID)) {
            setFuncionario(funcionarioID);
        }

        System.out.print("Agendar para: (formato: dd/MM/yyyy): ");
        String dataPrevisao = sc.nextLine();
        setDataPrevisaoEntrega(h.dataPadraoBanco(dataPrevisao));

        System.out.print("Deseja fazer alguma observação?  1-SIM   2-NÃO\n");
        int escolha = num.nextInt();
        if(escolha == 2){
            setObservacao("");
        }else{
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
    
    public void consAgendamento() {
        ag.listarAgendamento();
    }

    public void delAgendamento() {
        boolean del = false;
        numeroValidado = null;

        ag.listaEdicao();
        while (numeroValidado == null) {
            System.out.print("Digite o ID do agendamento: ");
            String pecaID = sc.nextLine();

            // atualizo minha variável para testar outra vez
            numeroValidado = h.isNumeric(pecaID);

            if (numeroValidado == null) {
                System.out.println("Digite apenas números.");
            }
        }
        // verifico se o id é válido no banco
        setIdAgendamento(numeroValidado);
        if (!validarAgendamento()) {
            System.out.println("ID não encontrado. Tente novamente.");
            numeroValidado = null;  // Redefine para continuar o loop
        }
        del = ag.apagarAgendamento(getIdAgendamento());
        if (del == false) {
            System.out.println("Erro ao apagar o agendamento.");
        } else {
            System.out.println("Agendamento excluído.");
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
