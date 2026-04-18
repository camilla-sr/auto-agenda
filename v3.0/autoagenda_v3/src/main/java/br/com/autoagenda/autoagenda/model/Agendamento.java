package br.com.autoagenda.autoagenda.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idAgendamento;
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "fk_oficina") private Oficina oficina;
	@ManyToOne @JoinColumn(name = "fk_funcionario") private Funcionario funcionario;
	@ManyToOne @JoinColumn(name = "fk_cliente") private Cliente cliente;
	@ManyToOne @JoinColumn(name = "fk_veiculo") private Veiculo veiculo;
	@ManyToOne @JoinColumn(name = "fk_servico") private Servico servico;
	@NotNull private LocalDate dataCadastro = LocalDate.now();
	@NotNull private LocalDate dataPrevisao;
    private LocalDate dataConclusao;
	@NotEmpty private String statusAgendamento;
    @Column(length = 1000) private String observacao;

    public Agendamento() {}
    
    public Agendamento(Integer idAgendamento, Cliente cliente, Funcionario funcionario, Veiculo veiculo, LocalDate dataCadastro, LocalDate dataPrevisao, LocalDate dataConclusao, String statusAgendamento, String observacao) {
    	this.idAgendamento = idAgendamento;
    	this.cliente = cliente;
    	this.funcionario = funcionario;
    	this.veiculo= veiculo;
    	this.servico = new Servico();
    	this.dataPrevisao = dataPrevisao;
    	this.dataConclusao = dataConclusao;
    	this.statusAgendamento = statusAgendamento;
    	this.observacao = observacao;
    }
    
    public Integer getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Integer idAgendamento) { this.idAgendamento = idAgendamento; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario (Funcionario funcionario) { this.funcionario = funcionario; }
    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }
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
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
}