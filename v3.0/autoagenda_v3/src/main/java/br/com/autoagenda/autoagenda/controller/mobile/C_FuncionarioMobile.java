package br.com.autoagenda.autoagenda.controller.mobile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoagenda.autoagenda.dto.mobile.FuncionarioLoginDto;
import br.com.autoagenda.autoagenda.dto.mobile.FuncionarioListagemDto;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;
import br.com.autoagenda.autoagenda.service.CodigoService;
import br.com.autoagenda.autoagenda.service.FuncionarioService;
import br.com.autoagenda.autoagenda.service.LogService;

@RestController
@RequestMapping("/mobile/funcionario-api")
public class C_FuncionarioMobile {
	@Autowired private LogService log;
	@Autowired FuncionarioService serv;
	@Autowired private FuncionarioRepository repo;
    @Autowired private OficinaRepository oficinaRepo;
    @Autowired private CodigoService codigoService;
    
    @PostMapping("/logar")
    public ResponseEntity<?> logarMobile(@RequestBody Map<String, String> credenciais) {
        try {
            String slug = credenciais.get("slug");
            String usuario = credenciais.get("usuario");
            String senha = credenciais.get("senha");
            
            Funcionario func = serv.autenticarMobile(usuario, senha, slug);
            FuncionarioLoginDto dto = new FuncionarioLoginDto(
                func.getIdFuncionario(),
                func.getNomeFuncionario(),
                func.getUsuario(),
                func.getEmail(),
                func.getAcesso(),
                func.isPrimeiroLogin(),
                new FuncionarioLoginDto.OficinaResumo(func.getOficina().getIdOficina())
            );
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            if ("Oficina não encontrada.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", e.getMessage()));
        }
    }
	
	@GetMapping
    public ResponseEntity<?> listarFuncionarios(@RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada."));
            List<Funcionario> lista = repo.findByOficinaAndAtivoTrue(oficina);
            FuncionarioListagemDto.OficinaResumo resumo = new FuncionarioListagemDto.OficinaResumo(idOficina);
            
            List<FuncionarioListagemDto> listaDto = lista.stream()
            	    .map(func -> new FuncionarioListagemDto(
            	        func.getIdFuncionario(),
            	        func.getNomeFuncionario(),
            	        func.getTelefone(),
            	        func.getUsuario(),
            	        func.getEmail(),
            	        func.getCpf(),
            	        func.getAcesso(),
            	        func.isAtivo(),
            	        resumo
            	    ))
            	    .toList();

                return ResponseEntity.ok(listaDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        Optional<Funcionario> funcOpt = repo.findById(id);
        if (funcOpt.isEmpty() || !funcOpt.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Funcionário não encontrado."));
        }
        return ResponseEntity.ok(funcOpt.get());
    }
	
	@PostMapping
    public ResponseEntity<?> salvarFuncionario(@RequestBody Funcionario func, @RequestHeader("idOficina") Integer idOficina) {
        try {
            Oficina oficina = oficinaRepo.findById(idOficina).orElseThrow(() -> new IllegalArgumentException("Oficina inválida."));
            
            serv.salvarOuAtualizar(func, null, false, oficina);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Dados salvos com sucesso!"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Este e-mail ou usuário já está em uso."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
	
	@PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Integer id, @RequestHeader("idOficina") Integer idOficina) {
        
        Optional<Funcionario> funcOpt = repo.findById(id);
        if (funcOpt.isEmpty() || !funcOpt.get().getOficina().getIdOficina().equals(idOficina)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Funcionário não encontrado."));
        }
        Funcionario func = funcOpt.get();
        func.setAtivo(!func.isAtivo());
        repo.save(func);
        
        return ResponseEntity.ok(Map.of("mensagem", "Status atualizado!", "ativo", func.isAtivo()
        ));
    }
	
	@PostMapping("/enviar-codigo-reset")
	public ResponseEntity<?> enviarCodigoReset(@RequestBody Map<String, String> dados) {
		String email = dados.get("email");
		Funcionario func = serv.buscarPorEmail(email); 
		
		if (func == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "E-mail não encontrado."));
        }
		String resultado = codigoService.solicitarEnvioCodigo(email);
		switch (resultado) {
			case "OK":
				return ResponseEntity.ok(Map.of("mensagem", "Código enviado."));
			case "SEM_ENVIO":
				return ResponseEntity.status(201).body(Map.of("erro", "Serviço de e-mail instável."));
			case "OFFLINE":
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("erro", "Serviço de autenticação offline."));
			default:
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro interno."));
		}
	}

	@PostMapping("/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody Map<String, String> dados) {
        String email = dados.get("email");
        String codigo = dados.get("codigo");
        String resultado = codigoService.validarCodigo(email, codigo);
        switch (resultado) {
            case "OK":
                return ResponseEntity.ok(Map.of("mensagem", "Código correto."));
            case "INVALIDO":
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Código inválido."));
            case "OFFLINE":
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("erro", "Serviço de autenticação offline."));
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("erro", "Erro na validação."));
        }
    }

	@PostMapping("/redefinir-senha-final")
	public ResponseEntity<?> redefinirSenhaFinal(@RequestBody Map<String, String> dados) {
		String email = dados.get("email");
		String novaSenha = dados.get("novaSenha");
		
		Funcionario func = serv.buscarPorEmail(email);
		if (func != null) {
			serv.atualizarSenha(func, novaSenha);
			log.registrar("Reset Senha", "Funcionário", func.getIdFuncionario(), "Senha redefinida via recuperação (App Mobile)", true);
			return ResponseEntity.ok(Map.of("mensagem", "Senha atualizada com sucesso."));
		}
		return ResponseEntity.badRequest().body(Map.of("erro", "Erro ao atualizar senha."));
	}
}