package principal;

import dao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Funcionamento {

    private int idFuncionamento;
    private String diaFuncionamento;
    private String horaFuncionamento;

    public void cadastrarFuncionamento(String diaFuncionamento, String horaFuncionamento) {
        Conexao conn = new Conexao();
        String sqlInserir = "INSERT INTO funcionamento (dia_funcionamento, horario_funcionamento) VALUES ('" + diaFuncionamento + "', '" + horaFuncionamento + "')";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta) {
            System.out.println("Horário de funcionamento inserido com sucesso.");
        } else {
            System.out.println("Algo deu errado ao inserir o horário de funcionamento.");
        }
    }

    // Método para listar todos os horários de funcionamento do banco de dados
    public void listarFuncionamentos() {
        Conexao conn = new Conexao();
        String sqlConsulta = "SELECT * FROM funcionamento";
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int idFuncionamento = lista.getInt("id_funcionamento");
                String diaFuncionamento = lista.getString("dia_funcionamento");
                String horaFuncionamento = lista.getString("horario_funcionamento");

                System.out.println("---------------------------");
                System.out.println("Dia de Funcionamento: " + diaFuncionamento);
                System.out.println("Horário de Funcionamento: " + horaFuncionamento);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

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