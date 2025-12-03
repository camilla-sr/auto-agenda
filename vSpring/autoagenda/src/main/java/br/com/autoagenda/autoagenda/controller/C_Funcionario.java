package br.com.autoagenda.autoagenda.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.service.CodigoService;
import br.com.autoagenda.autoagenda.service.FuncionarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funcionario-api")
public class C_Funcionario {
	@Autowired FuncionarioService service;
	@Autowired private CodigoService codigoService;

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

		boolean valido = codigoService.validarCodigo(email, codigo);

		if (valido) {
			return ResponseEntity.ok("Código correto.");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código inválido.");
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