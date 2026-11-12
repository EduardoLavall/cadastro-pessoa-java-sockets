package cliente;

import beans.Pessoa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class Cliente extends JFrame {
    private final JTextField campoNome = new JTextField();
    private final JRadioButton masculino = new JRadioButton("Masculino");
    private final JRadioButton feminino = new JRadioButton("Feminino");
    private final JComboBox<String> campoIdioma = new JComboBox<String>(
            new String[]{"Portugues", "Ingles", "Espanhol", "Outro"});
    private final JButton botaoCadastrar = new JButton("Cadastrar pessoa");
    private final JTextArea mensagem = new JTextArea("Inicie o servidor antes de cadastrar.");

    public Cliente() {
        super("Cadastro de Pessoa - Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470, 360);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fundo = new Color(250, 251, 253);
        JPanel principal = new JPanel(new BorderLayout(12, 18));
        principal.setBackground(fundo);
        principal.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));

        JLabel titulo = new JLabel("Cadastro de Pessoa");
        titulo.setFont(new Font("Dialog", Font.BOLD, 22));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(3, 2, 12, 16));
        formulario.setBackground(fundo);
        formulario.add(new JLabel("Nome *"));
        formulario.add(campoNome);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(masculino);
        grupo.add(feminino);
        masculino.setSelected(true);
        JPanel opcoesSexo = new JPanel(new GridLayout(1, 2));
        opcoesSexo.setBackground(fundo);
        masculino.setBackground(fundo);
        feminino.setBackground(fundo);
        opcoesSexo.add(masculino);
        opcoesSexo.add(feminino);
        formulario.add(new JLabel("Sexo"));
        formulario.add(opcoesSexo);
        formulario.add(new JLabel("Idioma"));
        formulario.add(campoIdioma);
        principal.add(formulario, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new GridLayout(2, 1, 0, 12));
        rodape.setBackground(fundo);
        botaoCadastrar.setBackground(new Color(38, 105, 180));
        botaoCadastrar.setForeground(Color.WHITE);
        rodape.add(botaoCadastrar);
        mensagem.setEditable(false);
        mensagem.setLineWrap(true);
        mensagem.setWrapStyleWord(true);
        mensagem.setOpaque(false);
        mensagem.setForeground(new Color(72, 80, 91));
        rodape.add(mensagem);
        principal.add(rodape, BorderLayout.SOUTH);
        setContentPane(principal);

        botaoCadastrar.addActionListener(evento -> cadastrar());
        campoNome.addActionListener(evento -> cadastrar());
    }

    private void cadastrar() {
        String nome = campoNome.getText().trim();
        if (nome.isEmpty()) {
            mensagem.setText("erro: O nome e obrigatorio.");
            mensagem.setForeground(new Color(180, 45, 45));
            campoNome.requestFocusInWindow();
            return;
        }
        if (!botaoCadastrar.isEnabled()) return;
        String sexo = masculino.isSelected() ? "Masculino" : "Feminino";
        String idioma = (String) campoIdioma.getSelectedItem();
        Pessoa pessoa = new Pessoa(nome, sexo, idioma);

        botaoCadastrar.setEnabled(false);
        mensagem.setText("Enviando para o servidor...");

        // Faz a comunicacao fora da tela para nao travar a janela.
        SwingWorker<String, Void> tarefa = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                try (Socket conexao = new Socket("localhost", 12345)) {
                    conexao.setSoTimeout(7000);
                    ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
                    saida.flush();
                    ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
                    saida.writeObject(pessoa);
                    saida.flush();
                    return (String) entrada.readObject();
                } catch (java.net.ConnectException erro) {
                    return "erro: O servidor nao esta aberto. Inicie o servidor primeiro.";
                } catch (Exception erro) {
                    return "erro: " + erro.getMessage();
                }
            }

            @Override
            protected void done() {
                try {
                    String resposta = get();
                    mensagem.setText(resposta);
                    mensagem.setForeground(resposta.startsWith("sucesso")
                            ? new Color(25, 116, 65) : new Color(180, 45, 45));
                    if (resposta.startsWith("sucesso")) campoNome.setText("");
                } catch (Exception erro) {
                    mensagem.setText("Erro ao receber resposta.");
                }
                botaoCadastrar.setEnabled(true);
            }
        };
        tarefa.execute();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignorado) { }
        SwingUtilities.invokeLater(() -> new Cliente().setVisible(true));
    }
}
