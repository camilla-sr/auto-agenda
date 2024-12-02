package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaDAO {
    final Conexao conn = new Conexao();

    // Métodos Principais
    public boolean cadastrarPeca(String descricaoPeca, int qntdPeca) {
        String sqlInserir = "INSERT into peca (desc_peca, qntd_peca)"
                + "VALUES ('" + descricaoPeca + "', " + qntdPeca + ")";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean editarPeca(int idPeca, String novaDescricao, int novaQntd) {
        String sqlEdit = ""; // inicializo a variável da query

        // testo o que será editado
        // Mudo a descrição da peça
        if (novaDescricao != null && !novaDescricao.isEmpty() && novaQntd == 0) {
            sqlEdit = "UPDATE peca SET desc_peca = '" + novaDescricao + "'";
        }

        // Muda apenas a quantidade
        if (novaQntd != 0 && (novaDescricao == null || novaDescricao.isEmpty())) {
            sqlEdit = "UPDATE peca SET qntd_peca = " + novaQntd;
        }

        // Muda ambos
        if (novaDescricao != null && !novaDescricao.isEmpty() && novaQntd != 0) {
            sqlEdit = "UPDATE peca SET desc_peca = '" + novaDescricao + "', qntd_peca = " + novaQntd;
        }

        sqlEdit = sqlEdit + " where id_peca = " + idPeca + "";
        boolean resposta = false;

        resposta = conn.executar(sqlEdit);

        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void listarPecas() {
        String sqlConsulta = "SELECT * from peca";
        System.out.println("---------------------------");
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            while (lista.next()) {
                int id = lista.getInt("id_peca");
                String descricaoPeca = lista.getString("desc_peca");
                int quantidade = lista.getInt("qntd_peca");

                System.out.println("ID: " + id);
                System.out.println("Peça: " + descricaoPeca);
                System.out.println("Quantidade: " + quantidade);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean apagarPeca(int idPeca) {
        boolean resposta = false;
        String sqlDel = "DELETE from peca where id_peca = " + idPeca + "";
        resposta = conn.executar(sqlDel);

        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

// -------------- MÉTODOS DE APOIO ---------------    
    public int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from peca where id_peca = " + id + "";
            ResultSet retorno = conn.executarConsulta(sql);
            if (retorno != null && retorno.next()) {
                resposta = 1;
            } else {
                resposta = 2;
            }
        } catch (SQLException e) {
            System.out.println("Algo deu errado" + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return resposta;
    }

    public int listaEdicao() {
        String sqlConsulta = "SELECT * from peca";
        System.out.println("\nID | DESCRIÇÃO | QUANTIDADE");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        int contagem = 0;

        try {
            while (lista.next()) {
                int id = lista.getInt("id_peca");
                String descricaoPeca = lista.getString("desc_peca");
                int quantidade = lista.getInt("qntd_peca");

                System.out.printf("%d. %s  \t-  \t%d\n", id, descricaoPeca, quantidade);
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
