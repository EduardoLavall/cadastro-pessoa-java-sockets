package beans;

import java.io.Serializable;

// Mesma classe no cliente e no servidor.
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String sexo;
    private String idioma;

    public Pessoa(String nome, String sexo, String idioma) {
        this.nome = nome;
        this.sexo = sexo;
        this.idioma = idioma;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public String getIdioma() {
        return idioma;
    }
}
