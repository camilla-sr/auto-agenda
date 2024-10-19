package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoServicoDAO {
    final Conexao conn = new Conexao();
    private int idServico;
    private String descricaoServico;

    public void cadastrarTipoServico(String descricaoServico) {
        String sqlInserir = "INSERT INTO tipo_servico (descricao_servico) VALUES ('" + descricaoServico + "')";

        boolean resposta = conn.executar(sqlInserir);
        if (resposta) {
            System.out.println("Tipo de serviço inserido");
        } else {
            System.out.println("Algo deu errado");
        }
    }

    public void listarTiposServico() {
        String sqlConsulta = "SELECT * FROM tipo_servico order by descricao_servico";
        ResultSet lista = conn.executarConsulta(sqlConsulta);

        try {
            System.out.println("\nServiços disponíveis");
            while (lista.next()) {
                int idServico = lista.getInt("id_servico");
                String descricaoServico = lista.getString("descricao_servico");

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
}
