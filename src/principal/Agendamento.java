package principal;

import dao.AgendamentoDAO;
import dao.TipoServicoDAO;
import dao.FuncionarioDAO;
import include.Helper;
import java.util.Scanner;

public class Agendamento {
    Integer numeroValidado = null;
    AgendamentoDAO ag = new AgendamentoDAO();
    Scanner sc = new Scanner(System.in);
    Scanner num = new Scanner(System.in);
    
    private int idAgendamento;
    private int servico;
    private int funcionario;
    private String dataCadastro;
    private String dataPrevisaoEntrega;
    private String dataConclusao;
    
    public void addAgenda() {
        System.out.print("Nome da peça:  ");
        
        
        
//        boolean cad = pc.cadastrarPeca(getDescricaoPeca(), getQntdPeca());
//        if (cad == false) {
//            System.out.println("Algo deu errado");
//        } else {
//            System.out.println("Cadastro realizado");
//        }
    }

        
    // -------------- GETTERS E SETTERS --------------
    public int getIdAgendamento() {
        return idAgendamento;
    }
    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
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
// ------------------------------------------------    
}
