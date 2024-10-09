package principal;

import dao.Conexao;

public class TipoServico {
    private int idServico;
    private String descricaoServico;

// -------------- GETTERS E SETTERS --------------
    public int getIdServico() {
        return idServico;
    }
    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }
    public String getDescricaoServico() {
        return descricaoServico;
    }
    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }
//  -----------------------------------------------
}
