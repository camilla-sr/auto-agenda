package br.com.autoagenda.autoagenda.dto.mobile;

public record ProdutoDto(Integer idProduto, OficinaResumo oficina, String codigoProduto, String categoria,
						String nomeProduto, double precoCusto, double precoVenda, String fornecedor,
						int estoqueAtual, int estoqueMinino, String descricao) {

	public record OficinaResumo(Integer idOficina) {}
}