package include;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Conexao {
    public Connection conn = null;
    public Statement stnt = null;
    public ResultSet resultset = null;

    private final String servidor = "jdbc:mysql://127.0.0.1:3306/auto_agenda";
    private final String usuario = "root";
    private final String senha = "";
    private final String driver = "com.mysql.cj.jdbc.Driver";

    public Connection conectar() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(servidor, usuario, senha);
            stnt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Erro ao acessar db" + e.getMessage());
        }
        return conn;
    }

    public void desconectar() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro ao encerrar conexão" + e.getMessage());
        }
    }

    public boolean executar(String sql) {
        boolean retorno = false;

        conectar(); // Conectar ao banco
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            retorno = true;
        } catch (SQLException e) {
            System.out.println("Erro ao executar query: " + e.getMessage());
        } finally {
            desconectar();
        }
        return retorno;
    }

    public ResultSet executarConsulta(String sql) {
        conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql); 

            resultset = stmt.executeQuery(); // Executa a consulta
        } catch (SQLException e) {
            System.out.println("Erro ao executar consulta: " + e.getMessage());
        }
        return resultset; // Retorna o ResultSet
    }
}
