package br.com.autoagenda.autoagenda.service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;

@Service
public class FuncionarioService {
	@Autowired private FuncionarioRepository repo;
    @Autowired private EmailService emailService;
    @Autowired private OficinaRepository oficinaRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public Funcionario autenticar(String usuario, String senha, Integer idOficina) {
        Funcionario func = repo.findByUsuarioAndOficina_IdOficina(usuario, idOficina);
        if (func != null && func.isAtivo() && validarSenha(senha, func)) { 
            return func;
        }
        return null;
    }
    
    public Funcionario autenticarMobile(String usuario, String senha, String slug) {
        Oficina oficina = oficinaRepo.findBySlug(slug).orElse(null);
        if (oficina == null || !oficina.getAtivo()) throw new IllegalArgumentException("OFICINA_INVALIDA");
        
        Funcionario func = repo.findByUsuarioAndOficina_IdOficina(usuario, oficina.getIdOficina());
        
        if (func == null || !func.isAtivo() || !validarSenha(senha, func)) {
            throw new IllegalArgumentException("Usuário e/ou senha incorretos.");
        }
        return func;
    }

    public void atualizarSenha(Funcionario func, String novaSenha) {
        func.setPrimeiroLogin(false);
        func.setSenha(novaSenha);
        repo.save(func);
    }
    
    public void alterarSenhaPerfil(Funcionario usuarioLogado, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new IllegalArgumentException("A nova senha e a confirmação não conferem.");
        }

        if (!validarSenha(senhaAtual, usuarioLogado)) {
             throw new IllegalArgumentException("A senha atual informada está incorreta.");
        }
        if (passwordEncoder.matches(novaSenha, usuarioLogado.getSenha())) {
            throw new IllegalArgumentException("A nova senha deve ser diferente da atual.");
        }
        usuarioLogado.setSenha(passwordEncoder.encode(novaSenha));
        repo.save(usuarioLogado);
    }

    public void salvarOuAtualizar(Funcionario func, String novaSenha, boolean cadastroInicial, Oficina oficina) {
        String cpfLimpo = func.getCpf().replaceAll("\\D", "");
        func.setCpf(cpfLimpo);

        Funcionario existeCpf = repo.findByCpf(cpfLimpo);
        if (existeCpf != null && (func.getIdFuncionario() == null || !existeCpf.getIdFuncionario().equals(func.getIdFuncionario()))) {
            throw new IllegalArgumentException("erroCPF");
        }

        if (func.getIdFuncionario() != null) {
            Funcionario funcBanco = repo.findById(func.getIdFuncionario()).orElseThrow();
            
            Funcionario existeUser = repo.findByUsuarioAndOficina_IdOficina(func.getUsuario(), oficina.getIdOficina());
            if (existeUser != null && !existeUser.getIdFuncionario().equals(func.getIdFuncionario())) {
                 throw new IllegalArgumentException("erroUsuario");
            }

            funcBanco.setOficina(oficina);
            funcBanco.setCpf(cpfLimpo);
            funcBanco.setNomeFuncionario(func.getNomeFuncionario());
            funcBanco.setEmail(func.getEmail());
            funcBanco.setUsuario(func.getUsuario());
            funcBanco.setAcesso(func.getAcesso());
            funcBanco.setTelefone(func.getTelefone());

            if (novaSenha != null && !novaSenha.isEmpty()) funcBanco.setSenha(passwordEncoder.encode(novaSenha));
            repo.save(funcBanco);
        } else {
            tratarUsuarioDuplicado(func, oficina);
            
            if (cadastroInicial) {
                if (novaSenha != null && !novaSenha.isEmpty()) { 
                    func.setSenha(passwordEncoder.encode(novaSenha)); 
                }
                func.setOficina(oficina);
                repo.save(func);
            } else {
                func.setPrimeiroLogin(true);
                String senhaProvisoria = gerarSenhaAleatoria();
                func.setSenha(passwordEncoder.encode(senhaProvisoria));
                
                func.setOficina(oficina);
                repo.save(func);
                
                func.setSenha(senhaProvisoria);
                emailService.redefirSenha(func);
            }
        }
    }
    
    public void atualizarPerfil(Funcionario form, Funcionario usuarioLogado) {
        Funcionario atual = repo.findById(usuarioLogado.getIdFuncionario())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        atual.setNomeFuncionario(form.getNomeFuncionario());
        atual.setEmail(form.getEmail());
        atual.setTelefone(form.getTelefone());
        
        if (!atual.getEmail().equals(usuarioLogado.getEmail())) {
             if (repo.findByEmail(form.getEmail()).isPresent()) {
                 throw new IllegalArgumentException("E-mail já está em uso.");
             }
        }
        repo.save(atual);
    }

    public void atualizar2FA(Funcionario func, boolean usarAuth) {
        func.setUsarAuth(usarAuth);
        repo.save(func);
    }
    
    public void excluir(Integer id) {
        if (id!= null) {
            Funcionario func = repo.findById(id).orElseThrow();
            func.setAtivo(false);
            repo.save(func);
        }
    }
    
    private boolean validarSenha(String senhaDigitada, Funcionario func) {
        if (func.getSenha().startsWith("$2a$") || func.getSenha().startsWith("$2b$")) {
            return passwordEncoder.matches(senhaDigitada, func.getSenha());
        } else {
            if (func.getSenha().equals(senhaDigitada)) {
                func.setSenha(passwordEncoder.encode(senhaDigitada));
                repo.save(func);
                return true;
            }
        }
        return false;
    }
    
    public void resetarSenha(Integer id) {
        Funcionario func = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        
        String senhaProvisoria = gerarSenhaAleatoria();
        func.setSenha(passwordEncoder.encode(senhaProvisoria));
        func.setPrimeiroLogin(true); 
        
        repo.save(func);
        
        func.setSenha(senhaProvisoria);
        emailService.redefirSenha(func);
    }
    
    private void tratarUsuarioDuplicado(Funcionario func, Oficina oficina) {
        String usuarioDigitado = func.getUsuario().toLowerCase().trim();
        Integer idOficina = oficina.getIdOficina();

        if (!repo.existsByUsuarioAndOficina_IdOficina(usuarioDigitado, idOficina)) {
            func.setUsuario(usuarioDigitado);
            return;
        }
        String nomeLimpo = Normalizer.normalize(func.getNomeFuncionario().toLowerCase(), Normalizer.Form.NFD)
                                     .replaceAll("[^\\p{ASCII}]", "");
        List<String> nomes = Arrays.stream(nomeLimpo.split("\\s+")).filter(p -> !p.matches("^(de|do|da|dos|das)$"))
                                   .collect(Collectors.toList());

        if (nomes.size() >= 3) { 
            String primeiroNome = nomes.get(0);
            String ultimoNome = nomes.get(nomes.size() - 1);
            
            if (usuarioDigitado.equals(primeiroNome + "." + ultimoNome)) {
                for (int i = nomes.size() - 2; i > 0; i--) {
                    String tentativaMeio = primeiroNome + "." + nomes.get(i);
                    
                    if (!repo.existsByUsuarioAndOficina_IdOficina(tentativaMeio, idOficina)) {
                        func.setUsuario(tentativaMeio);
                        return; 
                    }
                }
            }
        }
        String tentativaNum = usuarioDigitado;
        int cont = 1;
        while (repo.existsByUsuarioAndOficina_IdOficina(tentativaNum, idOficina)) {
            tentativaNum = usuarioDigitado + cont;
            cont++;
        }
        func.setUsuario(tentativaNum);
    }
    
    public Funcionario buscarPorEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    private String gerarSenhaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        
        for (int i = 0; i < 6; i++) {
            sb.append(caracteres.charAt(rd.nextInt(caracteres.length())));
        }
        return sb.toString();
    }
}