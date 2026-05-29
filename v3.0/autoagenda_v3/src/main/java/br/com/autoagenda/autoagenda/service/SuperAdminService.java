package br.com.autoagenda.autoagenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.autoagenda.autoagenda.model.SuperAdmin;
import br.com.autoagenda.autoagenda.repositorios.SuperAdminRepository;

@Service
public class SuperAdminService {
	@Autowired private SuperAdminRepository repo;
	
	public SuperAdmin autenticar(String usuario, String senha) {
		SuperAdmin admin = repo.findByUsuario(usuario);
		if(admin != null && admin.getSenha().equals(senha) && admin.isAtivo()) return admin;
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
                banco.setSenha(novaSenha);
            }
            repo.save(banco);
        } else {
            if (novaSenha != null && !novaSenha.isEmpty()) {
                admin.setSenha(novaSenha);
            }
            admin.setPrimeiroLogin(cadastroInicial);
            admin.setAtivo(true);
            repo.save(admin);
        }
    }
}