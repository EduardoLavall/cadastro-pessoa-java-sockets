package servidor;

import conexao.Conexao;
import java.io.Console;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== SERVIDOR DE CADASTRO ===");
        System.out.print("Usuario do MySQL (Enter = root): ");
        String usuario = teclado.nextLine().trim();
        if (usuario.isEmpty()) usuario = "root";

        String senha;
        Console console = System.console();
        if (console != null) {
            char[] digitado = console.readPassword("Senha do MySQL: ");
            senha = digitado == null ? "" : new String(digitado);
        } else {
            System.out.print("Senha do MySQL: ");
            senha = teclado.nextLine();
        }
        Conexao.configurar(usuario, senha);

        try (ServerSocket servidor = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado na porta 12345.");
            System.out.println("Abra agora o CLIENTE para cadastrar pessoas.");
            System.out.println("Para encerrar, pressione Ctrl + C.");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado.");
                AtenderCliente atendimento = new AtenderCliente(cliente);
                atendimento.start();
            }
        } catch (Exception erro) {
            System.out.println("Nao foi possivel iniciar o servidor: " + erro.getMessage());
        }
    }
}
