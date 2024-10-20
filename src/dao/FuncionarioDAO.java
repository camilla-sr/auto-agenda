package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    final Conexao conn = new Conexao();
    private int idFuncionario;
    private String nomeFuncionario;

    public void cadastrarFuncionario(String nomeFuncionario) {
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
        String sqlConsulta = "SELECT * FROM funcionario order by nome_funcionario";

        try {
            ResultSet lista = conn.executarConsulta(sqlConsulta);
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

    public void editarFuncionario(int idFuncionario, String novoNome) {
        try {
            String sql = "SELECT * FROM funcionario where id_funcionario = " + idFuncionario;
            ResultSet retorno = conn.executarConsulta(sql);

            int row = 0;
            if (retorno != null && retorno.next()) {
                row = retorno.getInt("id_funcionario");
                int funcionarioValido = validaID(row);
                if (funcionarioValido == 2) {
                    System.out.println("Funcionário não encontrado na base");
                } else {
                    String sqlEdit = "UPDATE funcionario set nome_funcionario = '"+novoNome+"' where id_funcionario = "+idFuncionario+"";
                    boolean resposta = conn.executar(sqlEdit);
                    if (resposta == true) {
                        System.out.println("Funcionário editado");
                    } else {
                        System.out.println("Algo deu errado");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        }
    }
    
    public void apagarFuncionario(int idFuncionario){
        try {
            String sql = "SELECT * FROM funcionario where id_funcionario = " + idFuncionario;
            ResultSet retorno = conn.executarConsulta(sql);

            int row = 0;
            if (retorno != null && retorno.next()) {
                row = retorno.getInt("id_funcionario");
                int funcionarioValido = validaID(row);
                if (funcionarioValido == 2) {
                    System.out.println("Funcionário não encontrado na base");
                } else {
                    String sqlDel = "DELETE from funcionario where id_funcionario = " + idFuncionario;
                    boolean resposta = conn.executar(sqlDel);
                    if (resposta == true) {
                        System.out.println("Funcionário deletado");
                    } else {
                        System.out.println("Algo deu errado");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        }
    }

// -------------- MÉTODOS DE APOIO --------------    
    private int validaID(int idFuncionario) {
        int resposta = 0;
        try {
            String sql = "SELECT * from funcionario where id_funcionario = " + idFuncionario;
            ResultSet retorno = conn.executarConsulta(sql);
            if (retorno != null && retorno.next()) {
                resposta = 1;
            } else {
                resposta = 2;
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        }
        return resposta;
    }

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

// ------------------------------------------------
}
