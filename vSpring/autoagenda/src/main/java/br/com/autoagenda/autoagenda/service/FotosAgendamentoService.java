package br.com.autoagenda.autoagenda.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.FotosAgendamento;
import br.com.autoagenda.autoagenda.repositorios.FotosAgendamentoRepository;

@Service
public class FotosAgendamentoService {
	@Value("${app.upload.dir}") private String pastaFotos;
	@Autowired private FotosAgendamentoRepository repo;
	
	public FotosAgendamento salvarFoto(Integer agendamentoId, MultipartFile file){
		try {
			Path diretorio = Paths.get(pastaFotos);
			if (!Files.exists(diretorio)) {
				Files.createDirectories(diretorio);
			}
			
			String nomeOriginal = file.getOriginalFilename();
			String ext = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
			String novoNome = agendamentoId + "_" + gerarCodigo() + ext;
			Path destino = diretorio.resolve(novoNome);
			
			Files.copy(file.getInputStream(), destino);
			FotosAgendamento foto = new FotosAgendamento(agendamentoId, destino.toString());
			return repo.save(foto);			
		}catch(IOException e) {
			throw new RuntimeException("Erro ao salvar imagem", e);
		}
	}
	
	private static String gerarCodigo() {
		String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int tamanho = 9;
		SecureRandom random = new SecureRandom();
		
		StringBuilder sb = new StringBuilder(tamanho);
		for(int i = 0; i < tamanho; i++) {
			sb.append(alfabeto.charAt(random.nextInt(alfabeto.length())));
		}
		return sb.toString();
	}
}