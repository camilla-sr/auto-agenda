package br.com.autoagenda.autoagenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProduto;
	private String codigoProduto;
	private String categoria;
	private String nomeProduto;
	private double preco;
	private String fornecedor;
	private int estoqueAtual;
	private int estoqueMinimo;
	private String descricao;
	
	public Produto() {}
	
	public boolean cadastrarProduto(int id, String codigoProduto, String categoria, String nomeProduto, double preco, String fornecedor, int estoqueAtual, int estoqueMinimo, String descricao) {
		String sql = "INSERT INTO produto "
	               + "(codigo_produto, categoria, nome_produto, preco, fornecedor, estoque_atual, estoque_minimo, descricao) "
	               + "VALUES ('"
	               + codigoProduto + "', '"
	               + categoria + "', '"
	               + nomeProduto + "', "
	               + preco + ", '"
	               + fornecedor + "', "
	               + estoqueAtual + ", "
	               + estoqueMinimo + ", '"
	               + descricao + "')";
		
		return true;
	}
	
	
//	public boolean editarProduto() {
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	public int getIdProduto() { return idProduto; }
	public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
	public String getCodigoProduto() { return codigoProduto; }
	public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }
	public String getCategoria() { return categoria; }
	public void setCategoria(String categoria) { this.categoria = categoria; }
	public String getNomeProduto() { return nomeProduto; }
	public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
	public double getPreco() { return preco; }
	public void setPreco(double preco) { this.preco = preco; }
	public String getFornecedor() { return fornecedor; }
	public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }
	public int getEstoqueAtual() { return estoqueAtual; }
	public void setEstoqueAtual(int estoqueAtual) { this.estoqueAtual = estoqueAtual; }
	public int getEstoqueMinimo() { return estoqueMinimo; }
	public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }
}
