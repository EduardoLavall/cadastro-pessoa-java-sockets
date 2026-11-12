package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // As credenciais ficam apenas na memoria enquanto o servidor estiver aberto.
    private static String usuario = "root";
    private static String senha = "";

    public static void configurar(String usuarioInformado, String senhaInformada) {
        usuario = usuarioInformado;
        senha = senhaInformada;
    }

    public static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/cadastro_pessoas"
            + "?connectionTimeZone=America/Sao_Paulo";
        return DriverManager.getConnection(url, usuario, senha);
    }
}
