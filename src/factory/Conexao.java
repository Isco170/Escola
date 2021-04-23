package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    
    public static Connection conetar() throws SQLException{
        Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        return conexao;
    }
}
