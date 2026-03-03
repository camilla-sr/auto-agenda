package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "produto")
public class Produto {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer idProduto;
	private String codigoProduto;
	@NotEmpty private String categoria;
	@NotEmpty private String nomeProduto;
	@NotNull private double precoCusto;
	@NotNull private double precoVenda;
	private String fornecedor;
	@NotNull private int estoqueAtual;
	@NotNull private int estoqueMinimo;
	private String descricao;
	
	public Produto() {}
	
	public Produto(int idProduto, String codigoProduto, String categoria, String nomeProduto,
					double precoCusto, double precoVenda, int estoqueAtual, int estoqueMinimo, String descricao) {
		this.idProduto = idProduto;
		this.codigoProduto = codigoProduto;
		this.nomeProduto = nomeProduto;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.estoqueAtual = estoqueAtual;
		this.estoqueMinimo = estoqueMinimo;
		this.descricao = descricao;
	}

	public Integer getIdProduto() { return idProduto; }
	public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }
	public String getCodigoProduto() { return codigoProduto; }
	public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }
	public String getCategoria() { return categoria; }
	public void setCategoria(String categoria) { this.categoria = categoria; }
	public String getNomeProduto() { return nomeProduto; }
	public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
	public double getPrecoCusto() { return precoCusto; }
	public void setPrecoCusto(double preco) { this.precoCusto = preco; }
	public double getPrecoVenda() { return precoVenda; }
	public void setPrecoVenda(double preco) { this.precoVenda = preco; }
	public String getFornecedor() { return fornecedor; }
	public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }
	public int getEstoqueAtual() { return estoqueAtual; }
	public void setEstoqueAtual(int estoqueAtual) { this.estoqueAtual = estoqueAtual; }
	public int getEstoqueMinimo() { return estoqueMinimo; }
	public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
}