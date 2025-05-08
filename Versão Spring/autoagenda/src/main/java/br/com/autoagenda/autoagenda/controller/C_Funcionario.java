package br.com.autoagenda.autoagenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import br.com.autoagenda.autoagenda.model.M_Funcionario;

@Controller
public class C_Funcionario {
	
	@PostMapping("/login")
	public String login(@RequestParam String usuario, String senha, HttpSession session) {
		if(usuario == null || usuario.trim().isEmpty()) return "usuario-nao-informado";
		if(senha == null || senha.trim().isEmpty()) return "senha-nao-informada";
		
		M_Funcionario func = new M_Funcionario().logar(usuario, senha);
		
		if(func == null) return "redirect:/login";
		
		session.setAttribute("usuarioLogado", func);
		return "redirect:/index";
	}
	
	@PostMapping("/cadastrarFuncionario")
    public String addFuncionario(@RequestParam String nome, String usuario, String senha) {
		if(nome == null || nome.trim().isEmpty()) return "usuario-nao-informado";
		if(usuario == null || usuario.trim().isEmpty()) return "usuario-nao-informado";
		if(senha == null || senha.trim().isEmpty()) return "usuario-nao-informado";
		
		M_Funcionario func = new M_Funcionario();
		boolean resposta = func.cadastrarFuncionario(nome , usuario, senha);
		
		if(!resposta) return "cadastrarFuncionario";
		
		return "redirect:/login";
    }

    public void edFuncionario() {
    
    }

//    public void consFuncionario() {
//        f.listarFuncionarios();
//    }

    public void delFuncionario() {
        
    }
}
