package br.com.autoagenda.autoagenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import br.com.autoagenda.autoagenda.service.FotosAgendamentoService;

@Controller
@RequestMapping("/mobile")
public class C_UploadMobile {
	
	@Autowired private FotosAgendamentoService service;
    @GetMapping("/upload-fotos/{token}")
    public String paginaMobile(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        return "mobile-upload";
    }

    @PostMapping("/enviar/{token}")
    @ResponseBody
    public ResponseEntity<?> receberFotoCelular(@PathVariable String token, @RequestParam("fotos") MultipartFile[] arquivos) {
    	try {
            if (arquivos != null && arquivos.length > 0) {
                for (MultipartFile file : arquivos) {
                    if (!file.isEmpty()) { service.salvarFotoMobile(token, file); }
                }
                return ResponseEntity.ok().body("{\"mensagem\": \"Sucesso\", \"qtd\": " + arquivos.length + "}");
            }
            return ResponseEntity.badRequest().body("Nenhum arquivo recebido");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro");
        }
    }
    
    @DeleteMapping("/cancelar/{token}")
    @ResponseBody
    public ResponseEntity<?> cancelarSessao(@PathVariable String token) {
        try {
            service.limparFotosTemporarias(token);
            return ResponseEntity.ok("Sessão limpa");
        } catch (Exception e) {
            return ResponseEntity.ok("Nada a limpar");
        }
    }
}