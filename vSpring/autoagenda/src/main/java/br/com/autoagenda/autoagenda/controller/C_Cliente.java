package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.autoagenda.autoagenda.model.Cliente;
import br.com.autoagenda.autoagenda.repositorios.ClienteRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente-api")
public class C_Cliente {
	@Autowired private ClienteRepository repo;

	@PostMapping("/cadastrarCliente")
    public String salvar(@Valid Cliente cl, BindingResult result) {
		if(result.hasErrors()) { return "?erro=true"; }
		
		if(cl.getIdCliente() != null) {
			Cliente existe = repo.findById(cl.getIdCliente()).orElse(new Cliente());
			existe.setNomeCliente(cl.getNomeCliente());
			existe.setCelular(cl.getCelular());
			existe.setEmailCliente(cl.getEmailCliente());
			existe.setModeloCarro(cl.getModeloCarro());
			repo.save(existe);
			return "?editado=true";
		} else {
			repo.save(cl);
		}
		return "?sucesso=true";
    }

	@PostMapping("/apagarCliente")
    public String apagarCliente(@RequestParam Long idCliente) {
		if(idCliente != null) { repo.deleteById(idCliente); }
		return "mensagem de retorno de deu bom";
    }
}