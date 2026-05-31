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
import br.com.autoagenda.autoagenda.service.LogService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/{slug}/funcionario-api")
public class C_Funcionario {
	@Autowired private LogService log;
	@Autowired FuncionarioService service;
	@Autowired private CodigoService codigoService;
	@Autowired private Sessao s;
	
	@PostMapping(value = "/logar")
    public String logar(@PathVariable("slug") String slug, @RequestParam String usuario,
                        @RequestParam String senha, HttpSession session, RedirectAttributes ra) {
        usuario = usuario != null ? usuario.trim() : "";
        senha = senha != null ? senha.trim() : "";
        
        Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
        Funcionario func = service.autenticar(usuario, senha, oficinaAtual.getIdOficina());
        if(func == null) { 
            log.registrar("Tentativa Login", "Sistema", null, "Falha no login para usuário: " + usuario, false );
            return "redirect:/"+ slug +"/login?usuarioValido=false";
        }

        if (func.isUsarAuth()) {
            log.registrar("Login 2FA", "Sistema", null, "Solicitação de autenticação em duas etapas", false );
            
            codigoService.solicitarEnvioCodigo(func.getEmail());
            session.setAttribute("tempEmail2FA", func.getEmail());
            return "redirect:/" + slug + "/login?precisa2fa=true";
        }        
        
        session.setAttribute("primeiroLogin", func.isPrimeiroLogin());          
        session.setAttribute("usuarioLogado", func);
        
        if (session.getAttribute("superAdminLogado") != null) {
            String desc = "Logou na oficina " + oficinaAtual.getNomeFantasia() + " com usuário válido " + func.getUsuario() + ". Acesso de superadmin";
            log.registrar("Login", "Oficina", oficinaAtual.getIdOficina(), desc, false);
        } else {
            log.registrar("Login", "Sistema", null, "Login realizado por: " + func.getUsuario(), false );
        }
        
        return "redirect:/" + slug;
    }

	@PostMapping("/logar-2fa")
    public String logar2fa(@PathVariable("slug") String slug, @RequestParam String codigo, HttpSession session, RedirectAttributes ra) {
        String email = (String) session.getAttribute("tempEmail2FA");
        if (email == null) return "redirect:/" + slug + "/login";
        
        String resultado = codigoService.validarCodigo(email, codigo);
        if ("OK".equals(resultado)) {
            Funcionario func = service.buscarPorEmail(email);
            session.removeAttribute("tempEmail2FA");
            session.setAttribute("primeiroLogin", func.isPrimeiroLogin());
            session.setAttribute("usuarioLogado", func);
            
            if (session.getAttribute("superAdminLogado") != null) {
                String desc = "Logou na oficina " + func.getOficina().getNomeFantasia() + " com usuário válido " + func.getUsuario() + " via 2FA. Acesso de superadmin";
                log.registrar("Login 2FA", "Oficina", func.getOficina().getIdOficina(), desc, false);
            } else {
                log.registrar("Login 2FA", "Sistema", null, "Login realizado com autenticação em duas etapas", false );
            }
            
            return "redirect:/" + slug;
        } else {
            log.registrar("Falha 2FA", "Sistema", null, "Código inválido para: " + email, false );
            ra.addFlashAttribute("erroOtp", "Código de segurança inválido ou expirado.");
            return "redirect:/" + slug + "/login?precisa2fa=true";
        }
    }

    @PostMapping("/toggle-2fa") @ResponseBody
    public ResponseEntity<?> toggle2fa(@PathVariable String slug, @RequestParam boolean usarAuth, HttpSession session) {
        Funcionario logado = (Funcionario) session.getAttribute("usuarioLogado");
        if(logado != null) {
            service.atualizar2FA(logado, usarAuth);
            logado.setUsarAuth(usarAuth);
            session.setAttribute("usuarioLogado", logado);
            
            log.registrar(usarAuth ? "Ativação 2FA" : "Desativação 2FA", "Funcionário", logado.getIdFuncionario(), "2FA alterado para: " + usarAuth, false );
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }
	
    @PostMapping(value = "logar_config")
    public String configurar(@PathVariable("slug") String slug, @RequestParam String usuario,
                            @RequestParam String senha, HttpSession session) {
        Oficina oficinaAtual = (Oficina) session.getAttribute("oficinaAtual");
        Funcionario func = service.autenticar(usuario, senha, oficinaAtual.getIdOficina());
        
        if(func == null) {
            log.registrar("Tentativa Login Config", "Sistema", null, "Falha no login da configuração para usuário: " + usuario, false );
            return "redirect:/"+ slug +"/login?usuarioValido=false";
        }
        session.setAttribute("usuarioLogado", func);
        
        if (session.getAttribute("superAdminLogado") != null) {
            String desc = "Acesso ao painel de configuração da oficina " + oficinaAtual.getNomeFantasia() + " com usuário válido " + func.getUsuario() + ". Acesso de superadmin";
            log.registrar("Login Config", "Oficina", oficinaAtual.getIdOficina(), desc, false);
        } else {
            log.registrar("Login Config", "Sistema", null, "Acesso ao painel de configuração", false );
        }
        return "redirect:/"+ slug +"/painel_configuracao";
    }
    
    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@PathVariable("slug") String slug, @RequestParam String novaSenha, HttpSession session) {
        Funcionario func = (Funcionario) session.getAttribute("usuarioLogado");
        if(func != null) {
            service.atualizarSenha(func, novaSenha);
            session.setAttribute("usuarioLogado", func);
            session.setAttribute("primeiroLogin", false);
            
            log.registrar("Redefinição Senha", "Funcionário", func.getIdFuncionario(), "Senha redefinida no primeiro acesso", false );
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
            log.registrar(edicao ? "Edição" : "Criação", "Funcionário", func.getIdFuncionario(), "Funcionário: " + func.getNomeFuncionario() + " | Usuário: " + func.getUsuario(), false );
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
            
            log.registrar("Edição Perfil", "Funcionário", atualizado.getIdFuncionario(), "Atualização de dados do perfil", false );
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
            log.registrar("Alteração Senha", "Funcionário", logado.getIdFuncionario(), "Senha alterada pelo usuário", false );
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
    	log.registrar("Exclusão", "Funcionário", idFuncionario, "Funcionário removido do sistema", false );
        service.excluir(idFuncionario);
        return "redirect:/"+ slug +"/funcionarios?apagar=true";
    }
    
	@PostMapping("/enviar-codigo-reset") @ResponseBody
	public ResponseEntity<?> enviarCodigoReset(@RequestParam String email) {
		Funcionario func = service.buscarPorEmail(email); 
		
		if (func == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
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

	@PostMapping("/validar-codigo") @ResponseBody
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

	@PostMapping("/redefinir-senha-final") @ResponseBody
	public ResponseEntity<?> redefinirSenhaFinal(@RequestBody Map<String, String> dados) {
		String email = dados.get("email");
		String novaSenha = dados.get("novaSenha");
		
		Funcionario func = service.buscarPorEmail(email);
		if (func != null) {
			service.atualizarSenha(func, novaSenha);
			log.registrar("Reset Senha", "Funcionário", func.getIdFuncionario(), "Senha redefinida via recuperação", false );
			return ResponseEntity.ok("Senha atualizada.");
		}
		return ResponseEntity.badRequest().body("Erro ao atualizar senha.");
	}
}