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
import br.com.autoagenda.autoagenda.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/{slug}/produto-api")
public class C_Produto {
	@Autowired private ProdutoService service;
    
    @PostMapping(value = "/salvar")
    public String salvar(@PathVariable("slug") String slug, @SessionAttribute("oficinaAtual") Oficina oficina, 
                        @Valid Produto prod, BindingResult result) {
        if(result.hasErrors()) { return "redirect:/"+ slug +"/produtos?erro=true"; }
        
        boolean edicao = prod.getIdProduto() != null;
        service.salvarOuAtualizar(prod, oficina);
        
        if(edicao) {
            return "redirect:/"+ slug +"/produtos?editado=true";
        } else {
            return "redirect:/"+ slug +"/produtos?sucesso=true";
        }
    }
    
    @PostMapping(value = "/apagar")
    public String apagar(@PathVariable("slug") String slug, @RequestParam Integer idProduto) {
        service.apagar(idProduto);
        return "redirect:/"+ slug +"/produtos?apagar=true";
    }
}