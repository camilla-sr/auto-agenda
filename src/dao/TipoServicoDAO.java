package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoServicoDAO {
    final Conexao conn = new Conexao();

    public boolean cadastrarTipoServico(String descricaoServico) {
        boolean resposta = false;
        String sqlInserir = "INSERT INTO tipo_servico (desc_servico) VALUES ('" + descricaoServico + "')";

        resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void listarTiposServico() {
        String sqlConsulta = "SELECT * FROM tipo_servico order by desc_servico";
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            System.out.println("\nServiços disponíveis");
            while (lista.next()) {
                int idServico = lista.getInt("id_servico");
                String descricaoServico = lista.getString("desc_servico");

                System.out.println(descricaoServico);
            }
            System.out.println("---------------------------");

            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean editarTipoServico(int idServico, String novaDescricao) {
        boolean resposta = false;
        String sqlEdit = "UPDATE tipo_servico set desc_servico = '" + novaDescricao
                + " where id_servico = " + idServico + "";

        resposta = conn.executar(sqlEdit);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean apagarTipoServico(int idServico) {
        boolean resposta = false;

        String sqlDel = "DELETE from tipo_servico where id_servico = " + idServico + "";
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
    public boolean validaID(int id) {
        boolean resposta = false;
        try {
            String sql = "SELECT * from tipo_servico where id_servico = " + id + "";
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
        String sqlConsulta = "SELECT * from tipo_servico";
        
        System.out.println("\nID | SERVIÇO");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            while (lista.next()) {
                int id = lista.getInt("id_servico");
                String servico = lista.getString("desc_servico");

                System.out.printf("%d. %s\n", id, servico);
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
}
