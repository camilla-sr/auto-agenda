package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    final Conexao conn = new Conexao();

    public boolean cadastrarFuncionario(String nomeFuncionario) {
        String sqlInserir = "INSERT INTO funcionario (nome_funcionario) VALUES ('" + nomeFuncionario + "')";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void listarFuncionarios() {
        String sqlConsulta = "SELECT * FROM funcionario order by nome_funcionario";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            if (lista != null) {
                System.out.println("\n\tListando Funcionários");
                while (lista.next()) {
                    int idFuncionario = lista.getInt("id_funcionario");
                    String nomeFuncionario = lista.getString("nome_funcionario");

                    System.out.println(nomeFuncionario);
                }
            } else {
                System.out.println("\tNenhum funcinonário cadastrado");
            }
            System.out.println("---------------------------");
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean editarFuncionario(int idFuncionario, String novoNome) {
        boolean resposta = false;
        boolean funcionarioValido = validaID(idFuncionario);

        if (!funcionarioValido) {
            System.out.println("Funcionário não encontrado na base");
        } else {
            String sqlEdit = "UPDATE funcionario set nome_funcionario = '" + novoNome + "' where id_funcionario = " + idFuncionario + "";
            resposta = conn.executar(sqlEdit);
        }
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean apagarFuncionario(int idFuncionario) {
        boolean resposta = false;
        boolean funcionarioValido = validaID(idFuncionario);

        if (!funcionarioValido) {
            System.out.println("Funcionário não encontrado na base");
        } else {
            String sqlDel = "DELETE from funcionario where id_funcionario = " + idFuncionario;
            resposta = conn.executar(sqlDel);
        }
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

// -------------- MÉTODOS DE APOIO --------------
    public boolean validaID(int idFuncionario) {
        boolean resposta = false;
        try {
            String sql = "SELECT * from funcionario where id_funcionario = " + idFuncionario;
            ResultSet retorno = conn.executarConsulta(sql);
            if (retorno != null && retorno.next()) {
                resposta = true;
            } else {
                resposta = false;
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return resposta;
    }

    public void listaEdicao() {
        String sqlConsulta = "SELECT * from funcionario";
        System.out.println("\nID | FUNCIONÁRIO");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            while (lista.next()) {
                int id = lista.getInt("id_funcionario");
                String nomeFuncionario = lista.getString("nome_funcionario");

                System.out.printf("%d. %s \n", id, nomeFuncionario);
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public int verificaRegistro() {
        String sqlConsulta = "SELECT * from funcionario";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        int contagem = 0;
        try {
            while (lista.next()) {
                contagem++;
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return contagem;
    }
}
