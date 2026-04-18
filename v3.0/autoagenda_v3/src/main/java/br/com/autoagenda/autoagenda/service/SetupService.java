package br.com.autoagenda.autoagenda.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.model.Funcionario;
import br.com.autoagenda.autoagenda.model.Oficina;
import br.com.autoagenda.autoagenda.repositorios.FuncionarioRepository;
import br.com.autoagenda.autoagenda.repositorios.OficinaRepository;

@Service
public class SetupService {
    @Autowired private OficinaRepository oficinaRepo;
    @Autowired private FuncionarioRepository funcionarioRepo;
    
    private final String BASE_UPLOAD_DIR = "src/main/resources/static/img";

    public Oficina salvarOficinaInicial(Oficina oficina, MultipartFile logo, MultipartFile favicon) {
        try {
            System.out.println("--- INICIANDO UPLOAD DE IMAGENS DO SETUP ---");
            
            String slugLimpo = oficina.getSlug().trim();
            String subPasta = slugLimpo + "/branding";
            
            if (logo != null && !logo.isEmpty()) {
                String ext = obterExtensao(logo.getOriginalFilename());
                String nomeLogo = "logotipo" + ext;
                
                salvarArquivo(logo, subPasta, nomeLogo);
                oficina.setLogotipo(slugLimpo + "/branding/" + nomeLogo); 
                System.out.println("Logo vinculada no DB: " + oficina.getLogotipo());
            } else {
                System.out.println("Aviso: Nenhuma LOGO recebida no formulário.");
            }            
            return oficinaRepo.save(oficina);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar imagens: " + e.getMessage());
        }
    }

    public void criarAdminInicial(Funcionario admin, Integer idOficina) {
        Oficina oficina = null;
        if (admin.getCpf() != null) { admin.setCpf(admin.getCpf().replaceAll("\\D", "")); }
        
        if (idOficina != null) {
            oficina = oficinaRepo.findById(idOficina.intValue()).orElse(null);
        }

        if (oficina == null) {
            List<Oficina> todas = oficinaRepo.findAll();
            if (todas.isEmpty()) {
                throw new IllegalStateException("Erro crítico: Nenhuma oficina encontrada para vincular o admin.");
            }
            oficina = todas.get(0);
        }

        admin.setOficina(oficina);
        admin.setAcesso("admin");
        admin.setPrimeiroLogin(false);
        
        if (funcionarioRepo.findByUsuario(admin.getUsuario()) != null) {
            throw new IllegalArgumentException("Este usuário já existe.");
        }
        if (funcionarioRepo.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }

        funcionarioRepo.save(admin);
    }

    public void atualizarDadosCadastrais(Oficina form) {
        Oficina atual = oficinaRepo.findById(form.getIdOficina())
            .orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada"));
        
        if (temValor(form.getNomeFantasia())) atual.setNomeFantasia(form.getNomeFantasia());
        if (temValor(form.getCnpj())) atual.setCnpj(form.getCnpj());
        if (temValor(form.getSlug())) atual.setSlug(form.getSlug().toLowerCase().trim());
        if (temValor(form.getTelefonePrincipal())) atual.setTelefonePrincipal(form.getTelefonePrincipal());
        if (temValor(form.getEmailContato())) atual.setEmailContato(form.getEmailContato());
        
        if (temValor(form.getCep())) {
            atual.setCep(form.getCep());
            atual.setLogradouro(form.getLogradouro());
            atual.setNumero(form.getNumero());
            atual.setBairro(form.getBairro());
            atual.setCidade(form.getCidade());
            atual.setUf(form.getUf());
            atual.setComplemento(form.getComplemento());
        }
        
        oficinaRepo.save(atual);
    }

    public void atualizarFuncionalidades(Integer idOficina, boolean produto, boolean financeiro, boolean auth) {
        Oficina atual = oficinaRepo.findById(idOficina)
            .orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada"));
        
        atual.setUsarProdutos(produto);
        atual.setUsarFinanceiro(financeiro);
        atual.setUsarAuth(auth);
        
        oficinaRepo.save(atual);
    }

    public void atualizarBranding(Integer idOficina, MultipartFile logo) {
        try {
            Oficina atual = oficinaRepo.findById(idOficina)
                .orElseThrow(() -> new IllegalArgumentException("Oficina não encontrada"));
            
            String subPasta = atual.getSlug() + "/branding";
            
            if (logo != null && !logo.isEmpty()) {
                String ext = obterExtensao(logo.getOriginalFilename());
                String nomeLogo = "logotipo" + ext;
                salvarArquivo(logo, subPasta, nomeLogo);
                atual.setLogotipo(subPasta + "/" + nomeLogo);
            }
            oficinaRepo.save(atual);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagens: " + e.getMessage());
        }
    }

    private boolean temValor(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
    
    private void salvarArquivo(MultipartFile arquivo, String subPasta, String nomeArquivo) throws IOException {
        Path caminhoUpload = Paths.get(BASE_UPLOAD_DIR, subPasta);
        
        if (!Files.exists(caminhoUpload)) {
            Files.createDirectories(caminhoUpload);
            System.out.println("--> Diretório criado: " + caminhoUpload.toAbsolutePath());
        }
        
        Path caminhoCompleto = caminhoUpload.resolve(nomeArquivo);
        Files.copy(arquivo.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("--> Arquivo salvo fisicamente em: " + caminhoCompleto.toAbsolutePath());
    }
    
    private String obterExtensao(String nomeOriginal) {
        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            return nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        }
        return ".png";
    }
}