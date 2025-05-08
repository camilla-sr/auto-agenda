package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.autoagenda.autoagenda.model.M_Funcionario;

@Controller
public class C_Login {

	@PostMapping("/logar")
	public String logar(@RequestParam String usuario, String senha) {
		if (usuario == null || usuario.trim().isEmpty()) return "erro-campo-vazio";
		if (senha == null || senha.trim().isEmpty()) return "erro-campo-vazio";
		if((usuario == null || usuario.trim().isEmpty()) || 
		   (senha == null || senha.trim().isEmpty())) return "usuario-ou-senha-nao-informado";
		
		M_Funcionario func = new M_Funcionario();
		return "index";
	}
}
