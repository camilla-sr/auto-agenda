package principal;

import dao.Conexao;

public class Funcionamento {
    private int idFuncionamento;
    private String diaFuncionamento;
    private String horaFuncionamento;
    
// -------------- GETTERS E SETTERS --------------
    public int getIdFuncionamento() {
        return idFuncionamento;
    }
    public void setIdFuncionamento(int idFuncionamento) {
        this.idFuncionamento = idFuncionamento;
    }
    public String getDiaFuncionamento() {
        return diaFuncionamento;
    }
    public void setDiaFuncionamento(String diaFuncionamento) {
        this.diaFuncionamento = diaFuncionamento;
    }
    public String getHoraFuncionamento() {
        return horaFuncionamento;
    }
    public void setHoraFuncionamento(String horaFuncionamento) {
        this.horaFuncionamento = horaFuncionamento;
    }
// -----------------------------------------------


}
