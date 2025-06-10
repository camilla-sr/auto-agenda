package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;

@Controller
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	@Autowired
	private FuncionarioRepository repo;

	@PostConstruct
	private void verificarAdmin() {
		Funcionario admin = repo.findByUsuario("admin");
		if(admin == null) {
			Funcionario adminPadrao = new Funcionario();
			adminPadrao.setNomeFuncionario("Administrador");
			adminPadrao.setUsuario("admin");
			adminPadrao.setCpf("12345678901");
			adminPadrao.setSenha("123");
			adminPadrao.setAcesso("admin");
			repo.save(adminPadrao);
		}
	}
	
	@PostMapping(value = "/cadastroSistema")
	public String cadastroFuncionario(@Valid Funcionario func, BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:/cadastroSistema?erro=true";
		}
		Funcionario existe = repo.findByUsuario(func.getUsuario());
		if(existe != null) {
			return "redirect:/cadastroSistema?erroUsuario=true";
		}
		func.setCpf(func.getCpf().replaceAll("\\D", ""));
		repo.save(func);
		return "redirect:/login?sucesso=true";
	}
	
	@PostMapping(value = "/logar")
	public String logar(@RequestParam String usuario, @RequestParam String senha, HttpSession session) {		
		Funcionario func = repo.findByUsuario(usuario);
		if(func == null || !func.getSenha().equals(senha)) {
			return "redirect:/login?usuarioValido=false";
		}
		session.setAttribute("usuarioLogado", func);
	    return "redirect:/";
	}
	
	@PostMapping(value = "/salvar")
	public String salvarFuncionario(@ModelAttribute Funcionario func, @RequestParam(required = false) String novaSenha, BindingResult result) {
		if(result.hasErrors()) {
			return "redirect:/funcionarios?erro=true";
		}
		
		
		if(func.getIdFuncionario() != null) {
			Funcionario usrExistente = repo.findByUsuario(func.getUsuario());
			if(repo.findByUsuario(func.getUsuario()) != null && !usrExistente.getIdFuncionario().equals(func.getIdFuncionario())) {
				return "redirect:/funcionarios?erroUsuario=true";
			}
						
			Funcionario funcExistente = repo.findById(func.getIdFuncionario()).orElse(new Funcionario());

			String cpfFormatado = func.getCpf().replaceAll("\\D", "");
			funcExistente.setCpf(cpfFormatado);
			
			Funcionario cpfExistente = repo.findByCpf(cpfFormatado);
			if(cpfExistente != null && !cpfExistente.getIdFuncionario().equals(func.getIdFuncionario())) {
				return "redirect:/funcionarios?erroCPF=true";
			}
			
			funcExistente.setNomeFuncionario(func.getNomeFuncionario());
			funcExistente.setUsuario(func.getUsuario());
			funcExistente.setAcesso(func.getAcesso());
			
			if (novaSenha != null && !novaSenha.isEmpty()) {
				funcExistente.setSenha(novaSenha);
			}
			repo.save(funcExistente);
			return "redirect:/funcionarios?editado=true";
		} else {
			func.setCpf(func.getCpf().replaceAll("\\D", ""));
			repo.save(func);
		}
		return "redirect:/funcionarios?sucesso=true";
	}
	
	@PostMapping(value = "/apagar")
	public String apagarFuncionario(@RequestParam Integer idFuncionario) {
		if(idFuncionario != null) {
			repo.deleteById(idFuncionario);
		}
		return "redirect:/funcionarios?apagar=true";
	}
}