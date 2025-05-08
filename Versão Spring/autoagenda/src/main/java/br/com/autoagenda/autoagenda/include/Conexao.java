package br.com.autoagenda.autoagenda.include;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.autoagenda.autoagenda.model.M_Funcionario;

public class Conexao {
    private static final String servidor = "jdbc:mysql://127.0.0.1:3306/auto_agenda";
    private static final String usuario = "root";
    private static final String senha = "";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    public Conexao() {}

    public static boolean executar(String sql) {
        boolean retorno = false;

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao executar query: " + e.getMessage());
        }
        return retorno;
    }
    
    public static List<String> executarConsulta(String sql) {
        List<String> dados = new ArrayList<>();
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int colunas = metaData.getColumnCount();
            
            while (rs.next()) {
                for (int i = 1; i <= colunas; i++) {
                    dados.add(rs.getString(i));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar consulta: " + e.getMessage());
        }
        return dados;
    }
    
    public static M_Funcionario logar(String usuario, String senha) {
    	String sql = "select * from funcionario where usuario = ? and senha = ?";
    	
    	try(Connection conn = conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)){
    		stmt.setString(1, usuario);
    		stmt.setString(2, senha);
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
                M_Funcionario func = new M_Funcionario();
                func.setIdFuncionario(rs.getInt("id"));
                func.setNomeFuncionario(rs.getString("nome"));
                func.setUsuario(rs.getString("usuario"));
                func.setSenha(rs.getString("senha"));
                return func;
            }
    	}catch(SQLException e) {
    		System.out.println("Erro ao buscar funcionário: " + e.getMessage());
    	}
    	return null;
    }
    
    public static boolean validaID(String tabela, String coluna, int id) {
    	boolean resposta = false;
        
        String sql = "SELECT 1 FROM " + tabela + " WHERE " + coluna + " = " + id + "";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            resposta = rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao validar ID: " + e.getMessage());
        }
        
        return resposta;
    }
    
    //---------------- MÉTODOS DE BASE -------------------
    
    private static Connection conectar() {
    	Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(servidor, usuario, senha);
        } catch (Exception e) {
            System.out.println("Erro ao acessar db" + e.getMessage());
        }
        return conn;
    }
}
