package br.com.autoagenda.autoagenda.model;

import br.com.autoagenda.autoagenda.include.Conexao;

public class M_Cliente {
    private int idCliente;
    private String nomeCliente;
    private String whatsappCliente;
    private String modeloCarro;
    private int anoCarro;

    public boolean cadastrarCliente(String nomeCliente, String whatsappCliente, String modeloCarro, int anoCarro) {
        String sql = "INSERT into cliente (nome_cliente, whatsapp_cliente, modelo_carro, ano_carro)"
                + "VALUES ('" + nomeCliente + "', '" + whatsappCliente + "', '" + modeloCarro + "', '" + anoCarro + "')";

        boolean resposta = Conexao.executar(sql);
        if(!resposta) return false;
        
        return true;
    }

    public boolean editarCliente(int idCliente, String novoNomeCliente, String novoWhatsappCliente, String novoModeloCarro, int novoAnoCarro) {
        String sqlEdit = "";

        // Muda o nome do cliente
        if (novoNomeCliente != null && novoWhatsappCliente == null && novoModeloCarro == null && novoAnoCarro == 0) {
            sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "'";
        }
        //Muda o whatsapp do cliente
        if (novoWhatsappCliente != null && novoNomeCliente == null && novoModeloCarro == null && novoAnoCarro == 0) {
            sqlEdit = "UPDATE cliente set whatsapp_cliente = '" + novoWhatsappCliente + "'";
        }
        // Muda o modelo do carro
        if (novoModeloCarro != null && novoNomeCliente == null && novoWhatsappCliente == null && novoAnoCarro == 0) {
            sqlEdit = "UPDATE cliente set modelo_carro = '" + novoModeloCarro + "'";
        }
        //Muda o ano do carro
        if (novoAnoCarro != 0 && novoNomeCliente == null && novoWhatsappCliente == null && novoModeloCarro == null) {
            sqlEdit = "UPDATE cliente set ano_carro = " + novoAnoCarro;
        }
        // Muda tudo
        if (novoAnoCarro != 0 && novoNomeCliente != null && novoWhatsappCliente != null && novoModeloCarro != null) {
            sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "', whatsapp_cliente = '" + novoWhatsappCliente
                    + "', modelo_carro = '" + novoModeloCarro + "', ano_carro = '" + novoAnoCarro + "'";
        }

        sqlEdit = sqlEdit + " WHERE id_cliente = " + idCliente + "";
        boolean resposta = Conexao.executar(sqlEdit);
        if(!resposta) return false;
        
        return true;
    }

//    public void listarCliente() {
//        String sqlConsulta = "SELECT * from cliente";
//        ResultSet lista = conn.executarConsulta(sqlConsulta);
//        System.out.println("---------------------------");
//        try {
//            if (lista != null) {
//                while (lista.next()) {
//                    int id = lista.getInt("id_cliente");
//                    String nome = lista.getString("nome_cliente");
//                    String whatsapp = lista.getString("whatsapp_cliente");
//                    String modelo = lista.getString("modelo_carro");
//                    String ano = lista.getString("ano_carro");
//
//                    System.out.println("ID: " + id);
//                    System.out.println("Nome do cliente: " + nome);
//                    System.out.println("WhatsApp: " + whatsapp);
//                    System.out.println("Modelo do veículo: " + modelo);
//                    System.out.println("Ano do veículo: " + ano);
//                    System.out.println("---------------------------");
//                }
//            } else {
//                System.out.println("\tNenhum cliente cadastrado");
//            }
//            lista.close();
//        } catch (SQLException e) {
//            System.out.println("Erro ao processar resultado: " + e.getMessage());
//        } finally {
//            conn.desconectar();
//        }
//    }

    public boolean apagarCliente(int idCliente) {
        String sql = "DELETE from cliente WHERE id_cliente = " + idCliente + "";
        boolean resposta = Conexao.executar(sql);
        if(!resposta) return false;
        return true;
    }
    
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    public String getWhatsappCliente() { return whatsappCliente; }
    public void setWhatsappCliente(String whatsappCliente) { this.whatsappCliente = whatsappCliente; }
    public String getModeloCarro() { return modeloCarro; }
    public void setModeloCarro(String modeloCarro) {this.modeloCarro = modeloCarro; }
    public int getAnoCarro() { return anoCarro; }
    public void setAnoCarro(int anoCarro) { this.anoCarro = anoCarro; }
}
