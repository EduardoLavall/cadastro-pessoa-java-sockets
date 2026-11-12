package dao;

import beans.Pessoa;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PessoaDAO {

    public void cadastrar(Pessoa pessoa) throws SQLException {
        // O nome nao pode ser vazio, mesmo que o usuario envie espacos.
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new SQLException("O nome e obrigatorio.");
        }

        String sql = "INSERT INTO pessoa (nome, sexo, idioma) VALUES (?, ?, ?)";

        // Cada cliente usa sua propria conexao com o banco.
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, pessoa.getNome().trim());
            comando.setString(2, pessoa.getSexo());
            comando.setString(3, pessoa.getIdioma());
            comando.executeUpdate();
        }
    }
}
