package br.com.autoagenda.autoagenda.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.Agendamento;
import br.com.autoagenda.autoagenda.model.FotosAgendamento;
import br.com.autoagenda.autoagenda.repositorios.FotosAgendamentoRepository;

@Service
public class FotosAgendamentoService {
	@Value("${app.upload.dir}") private String pastaFotos;
	@Autowired private FotosAgendamentoRepository repo;
	
	public FotosAgendamento salvarFoto(Agendamento ag, MultipartFile file){
		try {
			Path diretorio = Paths.get(pastaFotos);
			if (!Files.exists(diretorio)) {
				Files.createDirectories(diretorio);
			}
			
			String nomeOriginal = file.getOriginalFilename();
			String ext = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
			String novoNome = ag.getIdAgendamento() + "_" + gerarCodigo() + ext;
			Path destino = diretorio.resolve(novoNome);
			
			Files.copy(file.getInputStream(), destino);
			FotosAgendamento foto = new FotosAgendamento(ag, novoNome);
			return repo.save(foto);			
		}catch(IOException e) {
			throw new RuntimeException("Erro ao salvar imagem", e);
		}
	}
	
	public List<FotosAgendamento> buscarPorAgendamento(Integer idAgendamento) {
        return repo.findByAgendamento_IdAgendamento(idAgendamento);
    }

    public FotosAgendamento salvarFotoMobile(String token, MultipartFile file){
        try {
            Path diretorio = Paths.get(pastaFotos);
            if (!Files.exists(diretorio)) Files.createDirectories(diretorio);
            
            String nomeOriginal = file.getOriginalFilename();
            String ext = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            
            String novoNome = "temp_" + token + "_" + gerarCodigo() + ext;
            Path destino = diretorio.resolve(novoNome);
            
            Files.copy(file.getInputStream(), destino);
            FotosAgendamento foto = new FotosAgendamento(token, novoNome);
            return repo.save(foto);         
        } catch(IOException e) {
            throw new RuntimeException("Erro mobile", e);
        }
    }

    public void vincularFotosAoAgendamento(String token, Agendamento ag) {
        List<FotosAgendamento> fotosTemporarias = repo.findByTokenTemp(token);
        Path diretorio = Paths.get(pastaFotos);
        
        for (FotosAgendamento foto : fotosTemporarias) {
            try {
                String nomeAntigo = foto.getNomeArquivo();
                Path caminhoAntigo = diretorio.resolve(nomeAntigo);
                
                if (Files.exists(caminhoAntigo)) {
                    String ext = "";
                    int i = nomeAntigo.lastIndexOf('.');
                    if (i > 0) { ext = nomeAntigo.substring(i); }
                    
                    String novoNome = ag.getIdAgendamento() + "_" + gerarCodigo() + ext;
                    Path caminhoNovo = diretorio.resolve(novoNome);
                    Files.move(caminhoAntigo, caminhoNovo, StandardCopyOption.REPLACE_EXISTING);
                    
                    foto.setNomeArquivo(novoNome);
                    foto.setAgendamento(ag);
                    foto.setTokenTemp(null);
                    repo.save(foto);
                } else {
                    repo.delete(foto); 
                }
                
            } catch (IOException e) {
                e.printStackTrace(); 
                throw new RuntimeException("Erro ao renomear foto mobile", e);
            }
        }
    }
    
    public void limparFotosTemporarias(String token) {
        List<FotosAgendamento> fotos = repo.findByTokenTemp(token);
        Path diretorioBase = Paths.get(pastaFotos);

        for (FotosAgendamento foto : fotos) {
            try {
                Path arquivo = diretorioBase.resolve(foto.getNomeArquivo());
                Files.deleteIfExists(arquivo);
                repo.delete(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
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