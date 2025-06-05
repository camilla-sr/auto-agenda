package br.com.autoagenda.autoagenda.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "agendamento")
public class Agendamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAgendamento;
	@NotEmpty
    private String dataCadastro;
	@NotEmpty
    private String dataPrevisao;
	@NotEmpty
    private String dataConclusao;
	@NotEmpty
    private String statusAgendamento;
    private String observacao;
    @NotEmpty
    private String nomeCliente;
    @OneToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    public Agendamento() {}
    
    public Agendamento(Integer idAgendamento, String nomeCliente, String dataCadastro, String dataPrevisao, String dataConclusao, String statusAgendamento, String observacao) {
    	this.idAgendamento = idAgendamento;
    	this.nomeCliente = nomeCliente;
    	this.servico = new Servico();
    	this.dataCadastro = LocalDate.now().toString();
    	this.dataPrevisao = dataPrevisao;
    	this.dataConclusao = dataConclusao;
    	this.statusAgendamento = statusAgendamento;
    	this.observacao = observacao;
    }
    
    public Integer getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Integer idAgendamento) { this.idAgendamento = idAgendamento; }
    
    
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }
    
    public String getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(String dataCadastro) { this.dataCadastro = dataCadastro; }
    public String getDataPrevisao() { return dataPrevisao; }
    public void setDataPrevisao(String dataPrevisaoEntrega) { this.dataPrevisao = dataPrevisaoEntrega; }
    public String getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(String dataConclusao) { this.dataConclusao = dataConclusao; }
    public String getStatusAgendamento() { return statusAgendamento; }
    public void setStatusAgendamento(String status) { this.statusAgendamento = status; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
