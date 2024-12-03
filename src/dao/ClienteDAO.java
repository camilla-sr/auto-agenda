package dao;

import include.Conexao;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    final Conexao conn = new Conexao();

    //Métodos Principais
    public boolean cadastrarCliente(String nomeCliente, String whatsappCliente, String modeloCarro, int anoCarro) {
        boolean resposta = false;
        
        String sqlInserir = "INSERT into cliente (nome_cliente, whatsapp_cliente, modelo_carro, ano_carro)"
                + "VALUES ('" + nomeCliente + "', '" + whatsappCliente + "', '" + modeloCarro + "', '" + anoCarro + "')";

        resposta = conn.executar(sqlInserir);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public boolean editarCliente(int idCliente, String novoNomeCliente, String novoWhatsappCliente, String novoModeloCarro, int novoAnoCarro) {
        boolean resposta = false;
        String sqlEdit = "";
        
        // Muda o nome do cliente
        if(novoNomeCliente != null && novoWhatsappCliente == null && novoModeloCarro == null && novoAnoCarro == 0){
            sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "'";
        }
        //Muda o whatsapp do cliente
        if(novoWhatsappCliente != null && novoNomeCliente == null && novoModeloCarro == null && novoAnoCarro == 0){
            sqlEdit = "UPDATE cliente set whatsapp_cliente = '" + novoWhatsappCliente + "'";            
        }
        // Muda o modelo do carro
        if(novoModeloCarro != null && novoNomeCliente == null && novoWhatsappCliente == null && novoAnoCarro == 0){
            sqlEdit = "UPDATE cliente set modelo_carro = '" + novoModeloCarro + "'";                        
        }
        //Muda o ano do carro
        if(novoAnoCarro != 0 && novoNomeCliente == null && novoWhatsappCliente == null && novoModeloCarro == null){
            sqlEdit = "UPDATE cliente set ano_carro = " + novoAnoCarro;                        
        }
        // Muda tudo
        if(novoAnoCarro != 0 && novoNomeCliente != null && novoWhatsappCliente != null && novoModeloCarro != null){
            sqlEdit = "UPDATE cliente set nome_cliente = '" + novoNomeCliente + "', whatsapp_cliente = '" + novoWhatsappCliente
                    + "', modelo_carro = '" + novoModeloCarro + "', ano_carro = '" + novoAnoCarro + "'";                        
        }
        
        sqlEdit = sqlEdit + " WHERE id_cliente = " + idCliente + "";
        resposta = conn.executar(sqlEdit);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

    public void listarCliente() {
        String sqlConsulta = "SELECT * from cliente";
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        
        try {
            while (lista.next()) {
                System.out.println("---------------------------");
                int id = lista.getInt("id_cliente");
                String nome = lista.getString("nome_cliente");
                String whatsapp = lista.getString("whatsapp_cliente");
                String modelo = lista.getString("modelo_carro");
                String ano = lista.getString("ano_carro");

                System.out.println("ID: " + id);
                System.out.println("Nome do cliente: " + nome);
                System.out.println("WhatsApp: " + whatsapp);
                System.out.println("Modelo do veículo: " + modelo);
                System.out.println("Ano do veículo: " + ano);
                System.out.println("---------------------------");
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }

    public boolean apagarCliente(int idCliente) {
        boolean resposta = false;

        String sqlDel = "DELETE from cliente WHERE id_cliente = " + idCliente + "";
        resposta = conn.executar(sqlDel);
        if (resposta == true) {
            conn.desconectar();
            return true;
        } else {
            conn.desconectar();
            return false;
        }
    }

// -------------- MÉTODO DE APOIO ---------------- 
    public boolean validaID(int id) {
        boolean resposta = false;
        try {
            String sql = "SELECT * from cliente WHERE id_cliente = " + id + "";
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
        String sqlConsulta = "SELECT * from cliente";
        
        System.out.println("\nID | CLIENTE");
        ResultSet lista = conn.executarConsulta(sqlConsulta);
        try {
            while (lista.next()) {
                int id = lista.getInt("id_cliente");
                String cliente = lista.getString("nome_cliente");

                System.out.printf("%d. %s\n", id, cliente);
            }
            lista.close();
        } catch (SQLException e) {
            System.out.println("Erro ao processar resultado: " + e.getMessage());
        } finally {
            conn.desconectar();
        }
    }
    
    public int verificaRegistro(){
        String sqlConsulta = "SELECT * from cliente";  
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
