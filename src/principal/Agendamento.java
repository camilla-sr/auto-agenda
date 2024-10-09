package principal;

import dao.Conexao;

public class Agendamento {
    private int idAgendamento;
    private int servico;
    private int funcionamento;
    private int funcionarioResponsavel;
    private String dataCadastro;
    private String dataPrevisaoEntrega;
    private String dataConclusao;

    
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
    public int getFuncionamento() {
        return funcionamento;
    }
    public void setFuncionamento(int funcionamento) {
        this.funcionamento = funcionamento;
    }
    public int getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }
    public void setFuncionarioResponsavel(int funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
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
//  -----------------------------------------------
    
}
