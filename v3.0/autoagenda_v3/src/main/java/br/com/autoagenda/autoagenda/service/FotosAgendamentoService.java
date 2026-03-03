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
            String slug = ag.getOficina().getSlug();
            String subPasta = slug + "/agendamentos";
            Path diretorio = Paths.get(pastaFotos, subPasta);
            
            if (!Files.exists(diretorio)) Files.createDirectories(diretorio);
            
            String nomeOriginal = file.getOriginalFilename();
            String ext = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeApenas = ag.getIdAgendamento() + "_" + gerarCodigo() + ext;
            Path destino = diretorio.resolve(nomeApenas);
            
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            
            String caminhoRelativo = subPasta + "/" + nomeApenas;
            FotosAgendamento foto = new FotosAgendamento(ag, caminhoRelativo);
            return repo.save(foto);            
        } catch(IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
    }
	
	public List<FotosAgendamento> buscarPorAgendamento(Integer idAgendamento) {
        return repo.findByAgendamento_IdAgendamento(idAgendamento);
    }
	
	public List<FotosAgendamento> buscarPorToken(String token) {
        return repo.findByTokenTemp(token);
    }

	public FotosAgendamento salvarFotoMobile(String token, MultipartFile file){
        try {
            String subPasta = "temp";
            Path diretorio = Paths.get(pastaFotos, subPasta);
            
            if (!Files.exists(diretorio)) Files.createDirectories(diretorio);
            
            String nomeOriginal = file.getOriginalFilename();
            String ext = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeApenas = "temp_" + token + "_" + gerarCodigo() + ext;
            Path destino = diretorio.resolve(nomeApenas);
            
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            
            String caminhoRelativo = subPasta + "/" + nomeApenas;
            FotosAgendamento foto = new FotosAgendamento(token, caminhoRelativo);
            return repo.save(foto);         
        } catch(IOException e) {
            throw new RuntimeException("Erro mobile", e);
        }
    }

	public void vincularFotosAoAgendamento(String token, Agendamento ag) {
        List<FotosAgendamento> fotosTemporarias = repo.findByTokenTemp(token);
        
        String slug = ag.getOficina().getSlug();
        String subPastaDestino = slug + "/agendamentos";
        Path diretorioDestino = Paths.get(pastaFotos, subPastaDestino);
        
        for (FotosAgendamento foto : fotosTemporarias) {
            try {
                if (!Files.exists(diretorioDestino)) Files.createDirectories(diretorioDestino);

                Path caminhoAntigo = Paths.get(pastaFotos).resolve(foto.getNomeArquivo());
                
                if (Files.exists(caminhoAntigo)) {
                    String nomeAntigo = caminhoAntigo.getFileName().toString();
                    String ext = "";
                    int i = nomeAntigo.lastIndexOf('.');
                    if (i > 0) { ext = nomeAntigo.substring(i); }
                    
                    String nomeApenas = ag.getIdAgendamento() + "_" + gerarCodigo() + ext;
                    Path caminhoNovo = diretorioDestino.resolve(nomeApenas);
                    
                    Files.move(caminhoAntigo, caminhoNovo, StandardCopyOption.REPLACE_EXISTING);
                    
                    foto.setNomeArquivo(subPastaDestino + "/" + nomeApenas);
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
    
    public void apagarFotoPorId(Integer idFoto) {
        FotosAgendamento foto = repo.findById(idFoto).orElseThrow();
        try {
            Path arquivo = Paths.get(pastaFotos).resolve(foto.getNomeArquivo());
            Files.deleteIfExists(arquivo);
            repo.delete(foto);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao apagar arquivo físico", e);
        }
    }
    
    public void limparFotosTemporarias(String token) {
        List<FotosAgendamento> fotos = repo.findByTokenTemp(token);
        for (FotosAgendamento foto : fotos) {
            excluirArquivoFisicoERegistro(foto);
        }
    }
    
    public void apagarFotosDoAgendamento(Agendamento agendamento) {
        List<FotosAgendamento> fotos = repo.findByAgendamento_IdAgendamento(agendamento.getIdAgendamento());
        for (FotosAgendamento foto : fotos) {
            excluirArquivoFisicoERegistro(foto);
        }
    }
    
    private void excluirArquivoFisicoERegistro(FotosAgendamento foto) {
        try {
            Path arquivo = Paths.get(pastaFotos).resolve(foto.getNomeArquivo());
            Files.deleteIfExists(arquivo);
            
            repo.delete(foto);
        } catch (IOException e) {
        	throw new RuntimeException("Erro ao excluir imagens", e);
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