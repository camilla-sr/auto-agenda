package principal;

import dao.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Funcionario {

    private int idFuncionario;
    private String nomeFuncionario;
<<<<<<< HEAD

=======

    public void cadastrarFuncionario(String nomeFuncionario) {
        Conexao conn = new Conexao();
        String sqlInserir = "INSERT INTO funcionario (nome_funcionario) VALUES ('" + nomeFuncionario + "')";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta) {
            System.out.println("Funcionário inserido com sucesso.");
        } else {
            System.out.println("Algo deu errado ao inserir o funcionário.");
        }
    }

// Método para listar todos os funcionários do banco de dados
    public void listarFuncionarios() {
        Conexao conn = new Conexao();
        String sqlConsulta = "SELECT * FROM funcionario order by nome_funcionario";
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            System.out.println("\nFuncionários");
            while (lista.next()) {
                int idFuncionario = lista.getInt("id_funcionario");
                String nomeFuncionario = lista.getString("nome_funcionario");

                System.out.println(nomeFuncionario);
            }
            System.out.println("---------------------------");
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

>>>>>>> 19b0b566bcd8d09403fe783d56bda2aec8544874
// -------------- GETTERS E SETTERS --------------
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
// -----------------------------------------------

}
