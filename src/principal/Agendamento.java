package principal;

import dao.Conexao;

public class Agendamento {
    final Conexao conn = new Conexao();
    private int idAgendamento;
    private int servico;
    private int funcionario;
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
    public int getFuncionarioResponsavel() {
        return funcionario;
    }
    public void setFuncionarioResponsavel(int funcionario) {
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
//  -----------------------------------------------
    
}
