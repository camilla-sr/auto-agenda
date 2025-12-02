package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.service.FuncionarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	@Autowired FuncionarioService service;
	
	@PostMapping(value = "/logar")
    public String logar(@RequestParam String usuario, @RequestParam String senha, HttpSession session) {        
        Funcionario func = service.autenticar(usuario, senha);
        
        if(func == null) { return "redirect:/login?usuarioValido=false"; }
        
        session.setAttribute("primeiroLogin", func.isPrimeiroLogin());            
        session.setAttribute("usuarioLogado", func);
        
        return "redirect:/";
    }
    
    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam String novaSenha, HttpSession session) {
        Funcionario func = (Funcionario) session.getAttribute("usuarioLogado");
        if(func != null) {
            service.atualizarSenha(func, novaSenha);
            
            session.setAttribute("usuarioLogado", func);
            session.setAttribute("primeiroLogin", false);
        }
        return "redirect:/";
    }
    
    @PostMapping(value = "/salvar")
    public String salvarFuncionario(@ModelAttribute Funcionario func, 
            @RequestParam(required = false) String novaSenha,
            @RequestParam(required = false, defaultValue="false") boolean cadastroInicial, 
            BindingResult result) {
        
        String paginaErro = cadastroInicial ? "redirect:/cadastroSistema" : "redirect:/funcionarios";
        String paginaSucesso = cadastroInicial ? "redirect:/login?sucesso=true" : "redirect:/funcionarios?sucesso=true";
        String flagEditado = "redirect:/funcionarios?editado=true";

        if(result.hasErrors()) { return paginaErro + "?erro=true"; }
        boolean edicao = func.getIdFuncionario() != null;
        
        try {
            service.salvarOuAtualizar(func, novaSenha, cadastroInicial);
            if (edicao) {
                return flagEditado;
            } else {
                return paginaSucesso;
            }
        } catch (IllegalArgumentException e) {
            return paginaErro + "?" + e.getMessage() + "=true";
        }
    }
    
    @PostMapping(value = "/apagar")
    public String apagarFuncionario(@RequestParam Integer idFuncionario) {
        service.excluir(idFuncionario);
        return "redirect:/funcionarios?apagar=true";
    }
}