package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "veiculo")
public class Veiculo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idVeiculo;
	@ManyToOne @JoinColumn(name = "fk_cliente") private Cliente cliente;
	@NotBlank private String modelo;
	@NotBlank private String marca;
	@NotBlank private String placa;
	
	public Veiculo() {}
	
	public Veiculo(Integer idVeiculo, Cliente cliente,  String modelo, String marca, String placa) {
		this.idVeiculo = idVeiculo;
		this.cliente = cliente;
		this.modelo = modelo;
		this.marca = marca;
		this.placa = placa;
	}
	
	public Integer getIdVeiculo() { return idVeiculo; }
	public void setIdVeiculo(Integer idVeiculo) { this.idVeiculo = idVeiculo; }
	public Cliente getCliente() { return cliente; }
	public void setCliente(Cliente cliente) { this.cliente = cliente; }
	public String getModelo() { return modelo; }
	public void setModelo(String modelo) { this.modelo = modelo; }
	public String getMarca() { return marca; }
	public void setMarca(String marca) { this.marca = marca; }
	public String getPlaca() { return placa; }
	public void setPlaca(String placa) { this.placa = placa; }
}