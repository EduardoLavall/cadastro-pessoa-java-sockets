package servidor;

import beans.Pessoa;
import dao.PessoaDAO;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Cada cliente e atendido por uma Thread diferente.
public class AtenderCliente extends Thread {
    private final Socket cliente;

    public AtenderCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (Socket conexao = cliente) {
            conexao.setSoTimeout(10000);
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            saida.flush();
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());

            String resposta;

            try {
                Pessoa pessoa = (Pessoa) entrada.readObject();
                PessoaDAO dao = new PessoaDAO();
                dao.cadastrar(pessoa);
                resposta = "sucesso: Pessoa cadastrada!";
            } catch (Exception erro) {
                resposta = "erro: " + erro.getMessage();
            }

            saida.writeObject(resposta);
            saida.flush();
            System.out.println(resposta);

        } catch (Exception erro) {
            // Problema com um cliente nao derruba o servidor.
            System.out.println("Falha na comunicacao com um cliente: " + erro.getMessage());
        }
    }
}
