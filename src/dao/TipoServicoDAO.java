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
    
        public void editarTipoServico(int idServico, String novaDescricao) {
        int servicoValido = validaID(idServico);

        if (servicoValido == 2) {
            System.out.println("Tipo de Serviço não encontrado na base");
        } else {
            String sqlEdit = "UPDATE tipo_servico set desc_servico = '" + novaDescricao
                    + " where id_servico = " + idServico + "";

            boolean resposta = conn.executar(sqlEdit);
            if (resposta == true) {
                System.out.println("Tipo editado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }
        
    public void apagarPeca(int idServico) {
        int servicoValido = validaID(idServico);
        
        if (servicoValido == 2) {
            System.out.println("Peça não encontrada na base");
        } else {
            String sqlDel = "DELETE from tipo_servico where id_servico = " + idServico + "";
            boolean resposta = conn.executar(sqlDel);
            if (resposta == true) {
                System.out.println("Serviço deletado");
            } else {
                System.out.println("Algo deu errado");
            }
        }
        conn.desconectar();
    }
    
        
    // -------------- MÉTODOS DE APOIO ---------------    
    private int validaID(int id) {
        int resposta = 0;
        try {
            String sql = "SELECT * from tipo_servico where id_servico = " + id + "";
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
    // -----------------------------------------------
}
