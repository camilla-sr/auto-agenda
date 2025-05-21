package br.com.autoagenda.autoagenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;

@RestController
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	
	@Autowired
	private FuncionarioRepository repo;
	
	@RequestMapping(value = "/cadastroSistema", method = RequestMethod.POST)
	public String cadastroFuncionario(@Valid Funcionario func, BindingResult result) {
		
		if(result.hasErrors()) {
			return "redirect:/cadastroSistema";
		}
		
		repo.save(func);
		
		return "PARECE QUE SALVOU";
	}
	
	@GetMapping("/consulta")
	public List<Funcionario> consultarServico(){
	    List<Funcionario> lista = new ArrayList<>();
	    repo.findAll().forEach(lista::add);
	    return lista;
	}

	
//	@PostMapping("/login")
//	public String login(@RequestBody Funcionario dados,  HttpSession sessao) {
//		if(dados.getNomeFuncionario() == null || dados.getNomeFuncionario().trim().isEmpty()) return "usuario-nao-informado";
//		if(dados.getSenha() == null || dados.getSenha().trim().isEmpty()) return "senha-nao-informada";
//		
//		Funcionario user = repo.findByUsuario(dados.getUsuario());
//		
//		if (user != null && user.getSenha().equals(dados.getSenha())) {
//            // 
////			sessao.setAttribute("usuarioLogado", user);
//			return "Beleza ta funcional";
//        } else {
//            return "Email ou senha incorretos.";
//        }
//	}
    
}
