package br.com.autoagenda.autoagenda.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "log_sistema")
public class LogSistema {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long idLog;
    private LocalDateTime dataEvento = LocalDateTime.now();
    private String tipoUsuario;
    private Integer idUsuario;
    @ManyToOne @JoinColumn(name = "fk_oficina") private Oficina oficina;
    private String acao;
    private String tipoAlvo;
    private Integer alvoId;
    @Column(columnDefinition = "TEXT") private String descricao;

    public Long getIdLog() { return idLog; }
    public void setIdLog(Long idLog) { this.idLog = idLog; }
    public LocalDateTime getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDateTime dataEvento) { this.dataEvento = dataEvento; }
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    public String getTipoAlvo() { return tipoAlvo; }
    public void setTipoAlvo(String tipoAlvo) { this.tipoAlvo = tipoAlvo; }
    public Integer getAlvoId() { return alvoId; }
    public void setAlvoId(Integer alvoId) { this.alvoId = alvoId; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}