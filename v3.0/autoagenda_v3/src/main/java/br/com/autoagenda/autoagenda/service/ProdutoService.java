package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Produto;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired private ProdutoRepository repo;

    public void salvarOuAtualizar(Produto prod, Oficina oficina) {
        if (prod.getIdProduto() != null) {
            Produto existe = repo.findById(prod.getIdProduto()).orElseThrow();
            existe.setOficina(oficina);
            
            if (prod.getCodigoProduto() != null && !prod.getCodigoProduto().isEmpty()) existe.setCodigoProduto(prod.getCodigoProduto());
            if (prod.getCategoria() != null && !prod.getCategoria().isEmpty()) existe.setCategoria(prod.getCategoria());
            if (prod.getNomeProduto() != null && !prod.getNomeProduto().isEmpty()) existe.setNomeProduto(prod.getNomeProduto());
            
            existe.setPrecoCusto(prod.getPrecoCusto());
            existe.setPrecoVenda(prod.getPrecoVenda());
            
            if (prod.getFornecedor() != null && !prod.getFornecedor().isEmpty()) existe.setFornecedor(prod.getFornecedor());
            
            existe.setEstoqueAtual(prod.getEstoqueAtual());
            existe.setEstoqueMinimo(prod.getEstoqueMinimo());
            
            if (prod.getDescricao() != null && !prod.getDescricao().isEmpty()) existe.setDescricao(prod.getDescricao());
            
            repo.save(existe);
        } else {
            prod.setOficina(oficina);
            if (prod.getCodigoProduto() == null || prod.getCodigoProduto().isEmpty()) prod.setCodigoProduto("");
            if (prod.getFornecedor() == null || prod.getFornecedor().isEmpty()) prod.setFornecedor("");
            if (prod.getDescricao() == null || prod.getDescricao().isEmpty()) prod.setDescricao("");

            repo.save(prod);
        }
    }

    public void apagar(Integer idProduto) {
        if (idProduto != null) {
            repo.deleteById(idProduto);
        }
    }
}