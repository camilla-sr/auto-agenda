package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.model.SuperAdmin;
import br.com.autoagenda.autoagenda.repositorios.SuperAdminRepository;

@Service
public class SuperAdminService {
	@Autowired private SuperAdminRepository repo;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public SuperAdmin autenticar(String usuario, String senha) {
		SuperAdmin admin = repo.findByUsuario(usuario);
		if(admin != null && admin.isAtivo()) {
            // verifico se a senha no banco já está criptografada
            if (admin.getSenha().startsWith("$2a$") || admin.getSenha().startsWith("$2b$")) {
                if (passwordEncoder.matches(senha, admin.getSenha())) return admin;
            } else {
                // MODO MIGRAÇÃO: senha ainda é texto plano no banco
                if (admin.getSenha().equals(senha)) {
                    admin.setSenha(passwordEncoder.encode(senha));
                    repo.save(admin);
                    return admin;
                }
            }
        }
		return null;
	}
	
	public void salvarOuAtualizar(SuperAdmin admin, String novaSenha, boolean cadastroInicial) {
        SuperAdmin existeUser = repo.findByUsuario(admin.getUsuario());

        if (existeUser != null && (admin.getIdSuperadmin() == null || !existeUser.getIdSuperadmin().equals(admin.getIdSuperadmin()))) {
            if (!existeUser.isAtivo()) throw new IllegalArgumentException("Usuário já cadastrado, mas desativado.");
            throw new IllegalArgumentException("O usuário '" + admin.getUsuario() + "' já está em uso.");
        }

        if (admin.getIdSuperadmin() != null) {
            SuperAdmin banco = repo.findById(admin.getIdSuperadmin()).orElseThrow();
            banco.setNome(admin.getNome());
            banco.setEmail(admin.getEmail());
            banco.setUsuario(admin.getUsuario());
            
            if (novaSenha != null && !novaSenha.isEmpty()) {
                banco.setSenha(passwordEncoder.encode(novaSenha));
            }
            repo.save(banco);
        } else {
            if (novaSenha != null && !novaSenha.isEmpty()) {
                admin.setSenha(passwordEncoder.encode(novaSenha));
            }
            admin.setPrimeiroLogin(cadastroInicial);
            admin.setAtivo(true);
            repo.save(admin);
        }
    }
}