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
	
	public void salvarOuAtualizar(SuperAdmin admin) {
        SuperAdmin existeUser = repo.findByUsuario(admin.getUsuario());
        if (existeUser != null) {
            if (admin.getIdSuperadmin() == null || !existeUser.getIdSuperadmin().equals(admin.getIdSuperadmin())) {
                if (!existeUser.isAtivo()) {
                    throw new IllegalArgumentException("Este usuário já está cadastrado, mas encontra-se desativado.");
                }
                throw new IllegalArgumentException("O usuário '" + admin.getUsuario() + "' já está em uso.");
            }
        }
        if (admin.getIdSuperadmin() != null) {
            SuperAdmin existe = repo.findById(admin.getIdSuperadmin()).orElseThrow();
            existe.setNome(admin.getNome());
            existe.setEmail(admin.getEmail());
            existe.setUsuario(admin.getUsuario());
            existe.setSenha(admin.getSenha());
            
            repo.save(existe);
        } else {
            admin.setAtivo(true);
            repo.save(admin);
        }
    }
}