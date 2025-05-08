package br.com.autoagenda.autoagenda.model;

import br.com.autoagenda.autoagenda.include.Conexao;

public class M_Servico {
    private int idServico;
    private String descricaoServico;
    
    public M_Servico() {}
    
    public M_Servico(int idServico, String descricaoServico) {
    	this.idServico = idServico;
    	this.descricaoServico = descricaoServico;
    }
    
    public boolean cadastrarServico(String descricaoServico) {
    	String sql = "INSERT INTO tipo_servico (desc_servico) VALUES ('" + descricaoServico + "')";
    	boolean resposta = Conexao.executar(sql);
    	
    	if(!resposta) return false;
    	
    	return true;
    }

//    public void listarTiposServico() {
//        String sqlConsulta = "SELECT * FROM tipo_servico order by desc_servico";
//        ResultSet lista = conn.executarConsulta(sqlConsulta);
//
//        try {
//            if (lista != null) {
//                System.out.println("\nServiços disponíveis");
//                while (lista.next()) {
//                    int idServico = lista.getInt("id_servico");
//                    String descricaoServico = lista.getString("desc_servico");
//
//                    System.out.printf("%d. %s \n", idServico, descricaoServico);
//                }
//                System.out.println("---------------------------");
//            } else {
//                System.out.println("\tNenhum serviço cadastrado");
//            }
//
//            lista.close();
//        } catch (SQLException e) {
//            System.out.println("Erro ao processar resultado: " + e.getMessage());
//        } finally {
//            conn.desconectar();
//        }
//    }

    public boolean editarServico(int idServico, String novaDescricao) {
    	boolean valido = Conexao.validaID("tipo_servico", "id_servico", idServico);
    	if(!!valido) return false;
    	
        String sql = "UPDATE tipo_servico set desc_servico = '" + novaDescricao + "' where id_servico = " + idServico + "";
        boolean resposta = Conexao.executar(sql);
        if(!resposta) return false;
        
        return true;
    }

    public boolean apagarServico(int idServico) {
        boolean valido = Conexao.validaID("tipo_serivo", "id_servico", idServico);
        if(!valido) return false;

        String sql = "DELETE from tipo_servico where id_servico = " + idServico + "";
        boolean resposta = Conexao.executar(sql);
        if (!resposta) return false;
        
        return true;
    }
    
    public int getIdServico() { return idServico; }
    public void setIdServico(int idServico) { this.idServico = idServico; }
    public String getDescricaoServico() { return descricaoServico; }
    public void setDescricaoServico(String descricaoServico) { this.descricaoServico = descricaoServico; }
}
