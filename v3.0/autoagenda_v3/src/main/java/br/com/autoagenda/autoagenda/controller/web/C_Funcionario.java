package br.com.autoagenda.autoagenda.controller.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import br.com.autoagenda.autoagenda.config.Sessao;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.service.CodigoService;
import br.com.autoagenda.autoagenda.service.FuncionarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/{slug}/funcionario-api")
public class C_Funcionario {
	@Autowired FuncionarioService service;
	@Autowired private CodigoService codigoService;
	@Autowired private Sessao s;
	
	@PostMapping(value = "/logar")
    public String logar(@PathVariable("slug") String slug, @RequestParam String usuario,
    					@RequestParam String senha, HttpSession session) {        
        Funcionario func = service.autenticar(usuario, senha);
        
        if(func == null) { return "redirect:/"+ slug +"/login?usuarioValido=false"; }
        
        session.setAttribute("primeiroLogin", func.isPrimeiroLogin());          
        session.setAttribute("usuarioLogado", func);
        
        return "redirect:/" + slug;
    }
	
	@PostMapping(value = "logar_config")
	public String configurar(@PathVariable("slug") String slug, @RequestParam String usuario,
							@RequestParam String senha, HttpSession session) {
		Funcionario func = service.autenticar(usuario, senha);
		if(func == null) { return "redirect:/"+ slug +"/login?usuarioValido=false"; }
		
		session.setAttribute("usuarioLogado", func);
		
		return "redirect:/"+ slug +"/painel_configuracao";
	}
    
    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@PathVariable("slug") String slug, @RequestParam String novaSenha, HttpSession session) {
        Funcionario func = (Funcionario) session.getAttribute("usuarioLogado");
        if(func != null) {
            service.atualizarSenha(func, novaSenha);
            
            session.setAttribute("usuarioLogado", func);
            session.setAttribute("primeiroLogin", false);
        }
        return "redirect:/" + slug;
    }
    
    @PostMapping(value = "/salvar")
    public String salvarFuncionario(@PathVariable("slug") String slug, @SessionAttribute("oficinaAtual") Oficina oficina,
            @ModelAttribute Funcionario func, 
            @RequestParam(required = false) String novaSenha,
            @RequestParam(required = false, defaultValue="false") boolean cadastroInicial, 
            BindingResult result) {
        
        String paginaErro = cadastroInicial 
                ? "redirect:/"+ slug +"/cadastroSistema"
                : "redirect:/"+ slug +"/funcionarios";
        
        if(result.hasErrors()) { return paginaErro + "?erro=true"; }
        boolean edicao = func.getIdFuncionario() != null;
        
        String usuarioDigitado = func.getUsuario(); 
        
        try {
            service.salvarOuAtualizar(func, novaSenha, cadastroInicial, oficina);
            if (edicao) {
                return "redirect:/"+ slug +"/funcionarios?editado=true";
            } else {
                if (!usuarioDigitado.equals(func.getUsuario())) {
                    return "redirect:/"+ slug +"/funcionarios?sucesso=true&userGerado=" + func.getUsuario();
                }
                return "redirect:/"+ slug +"/funcionarios?sucesso=true";
            }
        } catch (IllegalArgumentException e) {
            return paginaErro + "?" + e.getMessage() + "=true";
        }
    }
    
    @PostMapping("/salvar-perfil")
    public String salvarPerfil(@PathVariable("slug") String slug, Funcionario form, HttpSession session, RedirectAttributes ra) {
        Funcionario logado = (Funcionario) session.getAttribute("usuarioLogado");
        if (logado == null) return "redirect:/"+ slug +"/login";

        try {
            service.atualizarPerfil(form, logado);
            Funcionario atualizado = service.buscarPorEmail(form.getEmail());
            session.setAttribute("usuarioLogado", atualizado);
            ra.addFlashAttribute("msgSucesso", "Dados atualizados com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", "Erro: " + e.getMessage());
        }
        return "redirect:/"+ slug +"/perfil";
    }
    
    @PostMapping("/alterar-senha-perfil")
    public String alterarSenhaPerfil(@PathVariable String slug, 
                                     @RequestParam String senhaAtual, 
                                     @RequestParam String novaSenha, 
                                     @RequestParam String confirmaSenha, 
                                     HttpSession session, RedirectAttributes ra) {
        try {
            Funcionario logado = (Funcionario) session.getAttribute("usuarioLogado");
            if (logado == null) return "redirect:/" + slug + "/login";
            
            service.alterarSenhaPerfil(logado, senhaAtual, novaSenha, confirmaSenha);
            s.logout(session);
            return "redirect:/" + slug + "/login?senhaAlterada=true";
            
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("msgErro", e.getMessage());
            return "redirect:/" + slug + "/perfil";
        } catch (Exception e) {
            ra.addFlashAttribute("msgErro", "Erro interno ao alterar senha.");
            return "redirect:/" + slug + "/perfil";
        }
    }
    
    @PostMapping(value = "/apagar")
    public String apagarFuncionario(@PathVariable("slug") String slug, @RequestParam Integer idFuncionario) {
        service.excluir(idFuncionario);
        return "redirect:/"+ slug +"/funcionarios?apagar=true";
    }
    
	@PostMapping("/enviar-codigo-reset")
	@ResponseBody
	public ResponseEntity<?> enviarCodigoReset(@RequestParam String email) {
		Funcionario func = service.buscarPorEmail(email); 
		
		if (func == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
		}
		String resultado = codigoService.solicitarEnvioCodigo(email);
		switch (resultado) {
			case "OK":
				return ResponseEntity.ok("Código enviado.");
			case "SEM_ENVIO":
				return ResponseEntity.status(201).body("Serviço de e-mail instável.");
			case "OFFLINE":
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de autenticação offline.");
			default:
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno.");
		}
	}

	@PostMapping("/validar-codigo")
    @ResponseBody
    public ResponseEntity<?> validarCodigo(@RequestBody Map<String, String> dados) {
        String email = dados.get("email");
        String codigo = dados.get("codigo");
        String resultado = codigoService.validarCodigo(email, codigo);
        switch (resultado) {
            case "OK":
                return ResponseEntity.ok("Código correto.");
            case "INVALIDO":
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código inválido.");
            case "OFFLINE":
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de autenticação offline.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na validação.");
        }
    }

	@PostMapping("/redefinir-senha-final")
	@ResponseBody
	public ResponseEntity<?> redefinirSenhaFinal(@RequestBody Map<String, String> dados) {
		String email = dados.get("email");
		String novaSenha = dados.get("novaSenha");
		
		Funcionario func = service.buscarPorEmail(email);
		
		if (func != null) {
			service.atualizarSenha(func, novaSenha);
			return ResponseEntity.ok("Senha atualizada.");
		}
		
		return ResponseEntity.badRequest().body("Erro ao atualizar senha.");
	}
}