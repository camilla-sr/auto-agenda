package br.com.autoagenda.autoagenda.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "agendamento")
public class Agendamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAgendamento;
	@NotNull
    private LocalDate dataCadastro = LocalDate.now();
	@NotNull
    private LocalDate dataPrevisao;
	
    private LocalDate dataConclusao;
	@NotEmpty
    private String statusAgendamento;
	
    private String observacao;
    @NotEmpty
    private String nomeCliente;
    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    public Agendamento() {}
    
    public Agendamento(Integer idAgendamento, String nomeCliente, LocalDate dataCadastro, LocalDate dataPrevisao, LocalDate dataConclusao, String statusAgendamento, String observacao) {
    	this.idAgendamento = idAgendamento;
    	this.nomeCliente = nomeCliente;
    	this.servico = new Servico();
    	this.dataCadastro = LocalDate.now();
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
    
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    public LocalDate getDataPrevisao() { return dataPrevisao; }
    public void setDataPrevisao(LocalDate dataPrevisao) { this.dataPrevisao = dataPrevisao; }
    public LocalDate getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }
    public String getStatusAgendamento() { return statusAgendamento; }
    public void setStatusAgendamento(String statusAgendamento) { this.statusAgendamento = statusAgendamento; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
