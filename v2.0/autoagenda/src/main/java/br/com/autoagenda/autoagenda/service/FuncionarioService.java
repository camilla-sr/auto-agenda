package br.com.autoagenda.autoagenda.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import jakarta.annotation.PostConstruct;

@Service
public class FuncionarioService {
	@Autowired private FuncionarioRepository repo;
    @Autowired private EmailService emailService;

    @PostConstruct
    public void verificarAdmin() {
        if(repo.findByUsuario("admin") == null) {
            Funcionario admin = new Funcionario();
            admin.setNomeFuncionario("Administrador");
			admin.setUsuario("admin");
			admin.setEmail("teste@email.com");
			admin.setCpf("88461726049");
			admin.setSenha("123");
			admin.setAcesso("admin");
			admin.setPrimeiroLogin(false);
            repo.save(admin);
        }
    }

    public Funcionario autenticar(String usuario, String senha) {
        Funcionario func = repo.findByUsuario(usuario);
        if (func != null && func.getSenha().equals(senha)) { return func; }
        return null;
    }

    public void atualizarSenha(Funcionario func, String novaSenha) {
        func.setPrimeiroLogin(false);
        func.setSenha(novaSenha);
        repo.save(func);
    }

    public void salvarOuAtualizar(Funcionario func, String novaSenha, boolean cadastroInicial) {
        String cpfLimpo = func.getCpf().replaceAll("\\D", "");
        func.setCpf(cpfLimpo);

        Funcionario existeUser = repo.findByUsuario(func.getUsuario());
        if (existeUser != null) {
            if (func.getIdFuncionario() == null || !existeUser.getIdFuncionario().equals(func.getIdFuncionario())) {
                throw new IllegalArgumentException("erroUsuario");
            }
        }

        Funcionario existeCpf = repo.findByCpf(cpfLimpo);
        if (existeCpf != null) {
            if (func.getIdFuncionario() == null || !existeCpf.getIdFuncionario().equals(func.getIdFuncionario())) {
                throw new IllegalArgumentException("erroCPF");
            }
        }

        if (func.getIdFuncionario() != null) {
            Funcionario funcBanco = repo.findById(func.getIdFuncionario()).orElseThrow();
            funcBanco.setCpf(cpfLimpo);
            funcBanco.setNomeFuncionario(func.getNomeFuncionario());
            funcBanco.setEmail(func.getEmail());
            funcBanco.setUsuario(func.getUsuario());
            funcBanco.setAcesso(func.getAcesso());

            if (novaSenha != null && !novaSenha.isEmpty()) { funcBanco.setSenha(novaSenha); }
            
            repo.save(funcBanco);
        } else {
            if (cadastroInicial) {
                if (novaSenha != null && !novaSenha.isEmpty()) { func.setSenha(novaSenha); }
            } else {
            	func.setPrimeiroLogin(true);
                String senhaProvisoria = gerarSenhaAleatoria();
                func.setSenha(senhaProvisoria);
                
                emailService.redefirSenha(func);
            }
            repo.save(func);
        }
    }

    public void excluir(Integer id) {
        if (id != null) repo.deleteById(id);
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