package br.com.autoagenda.autoagenda.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fotos_agendamento")
public class FotosAgendamento {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idFoto;
	@ManyToOne @JoinColumn(name = "fk_agendamento") private Agendamento agendamento;
	@Column(nullable = false) private String nomeArquivo;
	@Column(nullable = false) private LocalDateTime dataCriacao = LocalDateTime.now();
	
	public FotosAgendamento() {}
	
	public FotosAgendamento(Agendamento agendamento, String nomeArquivo) {
		this.agendamento = agendamento;
		this.nomeArquivo = nomeArquivo;
	}

	public Integer getIdFoto() { return idFoto; }
	public void setIdFoto(Integer idFoto) { this.idFoto = idFoto; }
	public Agendamento getAgendamento() { return agendamento; }
	public void setAgendamento(Agendamento agendamento) { this.agendamento = agendamento; }
	public String getNomeArquivo() { return nomeArquivo; }
	public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
	public LocalDateTime getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}