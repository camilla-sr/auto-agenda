package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.model.Produto;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produto-api")
public class C_Produto {

	@Autowired
	private ProdutoRepository repo;
	
	@PostMapping(value = "/salvar")
	public String salvar(@Valid Produto prod, BindingResult result) {
		if(result.hasErrors()) {
			return "deu alguma merda";
		}
		
		if(prod.getIdProduto() != null) {
			Produto existe = repo.findById(prod.getIdProduto()).orElse(new Produto());
			
			if(!prod.getCodigoProduto().isEmpty()) {
				existe.setCodigoProduto(prod.getCodigoProduto());				
			}
			existe.setCategoria(prod.getCategoria());
			existe.setNomeProduto(prod.getNomeProduto());
			existe.setPrecoCusto(prod.getPrecoCusto());
			existe.setPrecoVenda(prod.getPrecoVenda());
			if(!prod.getFornecedor().isEmpty()) {
				existe.setFornecedor(prod.getFornecedor());				
			}
			existe.setEstoqueAtual(prod.getEstoqueAtual());
			existe.setEstoqueMinimo(prod.getEstoqueMinimo());
			if(!prod.getDescricao().isEmpty()) {
				existe.setDescricao(prod.getDescricao());
			}
			repo.save(existe);
		} else {
			repo.save(prod);			
			return "redirect:/produtos?editado=true";
		}
		return "redirect:/produtos?sucesso=true";
	}
	
	@PostMapping(value = "/apagar")
	public String apagar(@RequestParam Integer idProduto) {
		if(idProduto != null) {
			repo.deleteById(idProduto);
		}
		return "redirect:/produtos?apagar=true";
	}
}