package br.com.autoagenda.autoagenda.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fotos_agendamento")
public class FotosAgendamento {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idFoto;
	@Column(nullable = false) private Integer agendamentoId;
	@Column(nullable = false) private String caminho;
	@Column(nullable = false) private LocalDateTime dataCriacao = LocalDateTime.now();
	
	public FotosAgendamento() {}
	
	public FotosAgendamento(Integer agendamentoId, String caminho) {
		this.agendamentoId = agendamentoId;
		this.caminho = caminho;
	}

	public Integer getIdFoto() { return idFoto; }
	public void setIdFoto(Integer idFoto) { this.idFoto = idFoto; }
	public Integer getAgendamentoId() { return agendamentoId; }
	public void setAgendamentoId(Integer agendamentoId) { this.agendamentoId = agendamentoId; }
	public String getCaminho() { return caminho; }
	public void setCaminho(String caminho) { this.caminho = caminho; }
	public LocalDateTime getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}