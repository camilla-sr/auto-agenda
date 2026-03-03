package br.com.autoagenda.autoagenda.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.model.Produto;
import br.com.autoagenda.autoagenda.repositorios.ProdutoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/{slug}/produto-api")
public class C_Produto {
	@Autowired private ProdutoRepository repo;
	
	@PostMapping(value = "/salvar")
	public String salvar(@PathVariable("slug") String slug, @SessionAttribute("oficinaAtual") Oficina oficina, 
						@Valid Produto prod, BindingResult result) {
		if(result.hasErrors()) { return "redirect:/"+ slug +"/produtos?erro=true"; }
		
		if(prod.getIdProduto() != null) {
			Produto existe = repo.findById(prod.getIdProduto()).orElseThrow();
			existe.setOficina(oficina);
			if(!prod.getCodigoProduto().isEmpty()) { existe.setCodigoProduto(prod.getCodigoProduto()); }
			if(!prod.getCategoria().isEmpty()) { existe.setCategoria(prod.getCategoria()); }
			if(!prod.getNomeProduto().isEmpty()) { existe.setNomeProduto(prod.getNomeProduto()); }
			existe.setPrecoCusto(prod.getPrecoCusto());
			existe.setPrecoVenda(prod.getPrecoVenda());
			if(!prod.getFornecedor().isEmpty()) { existe.setFornecedor(prod.getFornecedor()); }
			existe.setEstoqueAtual(prod.getEstoqueAtual());
			existe.setEstoqueMinimo(prod.getEstoqueMinimo());
			if(!prod.getDescricao().isEmpty()) { existe.setDescricao(prod.getDescricao()); }
			
			repo.save(existe);
			return "redirect:/"+ slug +"/produtos?editado=true";
		}else{
			prod.setOficina(oficina);
			if(prod.getCodigoProduto().isEmpty()) { prod.setCodigoProduto(""); }
			if(prod.getFornecedor().isEmpty()) { prod.setFornecedor(""); }
			if(prod.getDescricao().isEmpty()) { prod.setDescricao(""); }

			repo.save(prod);
			return "redirect:/"+ slug +"/produtos?sucesso=true";
		}
	}
	
	@PostMapping(value = "/apagar")
	public String apagar(@PathVariable("slug") String slug, @RequestParam Integer idProduto) {
		if(idProduto != null) { repo.deleteById(idProduto); }
		return "redirect:/"+ slug +"/produtos?apagar=true";
	}
}