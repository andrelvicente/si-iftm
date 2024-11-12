package classes;
import java.util.ArrayList;
import java.util.HashMap;

public class Biblioteca {
    private String nome;
    private String localizacao;
    private HashMap<String, String> categorias = new HashMap<>();
    private ArrayList<Livro> livros = new ArrayList<>();

    public Biblioteca(String nome, String localizacao) {
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void listarLivros() {
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    public void adicionarCategoria(String key, String value) {
        categorias.put(key, value);
    }

    public void listarCategorias() {
        categorias.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }
}