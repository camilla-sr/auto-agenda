package br.com.autoagenda.autoagenda.repositorios;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import br.com.autoagenda.autoagenda.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
	
	// SEUS MÉTODOS ORIGINAIS (MANTIDOS)
	Iterable<Produto> findAll();
	Optional<Produto> findById(Integer id);
	
	// =====================================================================
    //               INÍCIO DAS ADIÇÕES NECESSÁRIAS
    // =====================================================================

    /**
     * Conta produtos onde o estoque atual está abaixo ou igual ao mínimo, mas AINDA É MAIOR QUE ZERO.
     * Alimenta o card "Abaixo do estoque mínimo".
     */
    @Query("SELECT count(p) FROM Produto p WHERE p.estoqueAtual <= p.estoqueMinimo AND p.estoqueAtual > 0")
    int countByEstoqueBaixo();

    /**
     * Conta produtos onde o estoque atual é EXATAMENTE zero.
     * Alimenta o card "Produtos esgotados".
     */
    int countByEstoqueAtualEquals(int estoque);

	/**
     * Calcula o valor total de todos os produtos em estoque (Preço de Venda * Estoque Atual).
     * Alimenta o card "Valor total em estoque".
     */
    @Query("SELECT SUM(p.precoVenda * p.estoqueAtual) FROM Produto p")
    BigDecimal sumPrecoVendaEstoque();

    // =====================================================================
    //                   FIM DAS ADIÇÕES NECESSÁRIAS
    // =====================================================================
	
	// SEU MÉTODO ORIGINAL countByEstoqueAtualLessThan FOI REMOVIDO POIS OS NOVOS SÃO MAIS PRECISOS.
	// SEU MÉTODO sumTotalPrecoCusto FOI REMOVIDO E SUBSTITUÍDO POR sumPrecoVendaEstoque, QUE É O CORRETO PARA O CARD.
}