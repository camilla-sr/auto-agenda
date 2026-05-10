package br.com.autoagenda.autoagenda.controller.mobile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.dto.mobile.ProdutoDto;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Produto;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;
import br.com.autoagenda.autoagenda.service.ProdutoService;

@RestController
@RequestMapping("/mobile/produto-api")
public class C_ProdutoMobile {
    @Autowired private ProdutoRepository repo;
    @Autowired private OficinaRepository oficinaRepo;
    @Autowired private ProdutoService serv;
	
    @GetMapping
    public ResponseEntity<?> listarProdutos(@RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada."));
            List<Produto> lista = repo.findByOficina(oficina);
            ProdutoDto.OficinaResumo resumo = new ProdutoDto.OficinaResumo(idOficina);
            
            List<ProdutoDto> dto = lista.stream().map(prod -> new ProdutoDto(
            		prod.getIdProduto(),
            		resumo, prod.getCodigoProduto(),
            		prod.getCategoria(), prod.getNomeProduto(), prod.getPrecoCusto(), prod.getPrecoVenda(),
            		prod.getFornecedor(), prod.getEstoqueAtual(), prod.getEstoqueMinimo(), prod.getDescricao()
            		))
            		.toList();
            
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Produto> produto = repo.findById(id);
        
        if (produto.isEmpty() || !produto.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Produto não encontrado."));
        }
        return ResponseEntity.ok(produto.get());
    }
	
    @PostMapping
    public ResponseEntity<?> salvarProduto(@RequestBody Produto produto, @RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina inválida."));
            serv.salvarOuAtualizar(produto, oficina);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Produto salvo com sucesso!"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Já existe um produto com este código nesta oficina."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarProduto(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Produto> produto = repo.findById(id);
        
        if (produto.isEmpty() || !produto.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Produto não encontrado."));
        }
        serv.apagar(id);
        return ResponseEntity.ok(Map.of("mensagem", "Produto excluído com sucesso!"));
    }
}