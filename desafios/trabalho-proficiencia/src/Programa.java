import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import classes.Biblioteca;
import classes.Livro;

public class Programa {
    private static Biblioteca biblioteca = new Biblioteca("Biblioteca Central", "Centro da Cidade");
    private static ArrayList<Livro> livrosCadastrados = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String ARQUIVO_JSON = "biblioteca.json";

    public static void main(String[] args) {
        while (true) {
            geraMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    try {
                        preencherBiblioteca();
                        break;
                    } catch (Exception e) {
                        System.out.println("Erro ao preencher biblioteca.");
                    }
                case 2:
                    try {
                        inserirLivroNaBiblioteca();
                        break;
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir livro biblioteca.");
                    }
                case 3:
                    try {
                        gerarRelatorioBiblioteca();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao gerar relatorio.");
                    }
                case 4:
                    try {
                        listarLivrosBiblioteca();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao Listar livros biblioteca.");
                    }
                case 5:
                    try {
                        cadastrarLivro();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao cadastrar livro.");
                    }
                case 6:
                    try {
                        modificarLivro();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao modificar livro.");
                    }
                case 7:
                    try {
                        listarLivrosCadastrados();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao listar livros cadastrados.");
                    }
                case 8:
                    try {
                        salvarDados();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao salvar dados.");
                    }
                case 9:
                    try {
                        carregarDados();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao carregar dados.");
                    }
                case 10:
                    try {
                        apagarDados();
                        break;
                    } catch (Exception e) {
                       System.out.println("Erro ao apagar dados");
                    }
                case 11:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void geraMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Preencher dados da Biblioteca.");
        System.out.println("2. Inserir Livro na Biblioteca.");
        System.out.println("3. Gerar relatório da Biblioteca.");
        System.out.println("4. Listar Livros da Biblioteca.");
        System.out.println("5. Cadastrar um novo Livro.");
        System.out.println("6. Modificar um Livro.");
        System.out.println("7. Listar todos os Livros cadastrados.");
        System.out.println("8. Salvar dados em arquivo JSON.");
        System.out.println("9. Carregar dados de arquivo JSON.");
        System.out.println("10. Apagar todos os dados.");
        System.out.println("11. Sair.");
        System.out.print("Escolha uma opção: ");
    }

    public static void preencherBiblioteca() {
        System.out.print("Nome da Biblioteca: ");
        String nome = scanner.nextLine();
        System.out.print("Localização da Biblioteca: ");
        String localizacao = scanner.nextLine();
        biblioteca = new Biblioteca(nome, localizacao);
    }

    public static void inserirLivroNaBiblioteca() {
        System.out.print("Título do Livro a inserir na Biblioteca: ");
        String titulo = scanner.nextLine();
        for (Livro livro : livrosCadastrados) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                biblioteca.adicionarLivro(livro);
                System.out.println("Livro inserido com sucesso.");
                return;
            }
        }
        System.out.println("Livro não encontrado nos cadastrados.");
    }

    public static void gerarRelatorioBiblioteca() {
        System.out.println("Relatório da Biblioteca:");
        System.out.println("Nome: " + biblioteca.getNome());
        System.out.println("Localização: " + biblioteca.getLocalizacao());
        System.out.println("Livros: ");
        biblioteca.listarLivros();
    }

    public static void listarLivrosBiblioteca() {
        biblioteca.listarLivros();
    }

    public static void cadastrarLivro() {
        System.out.print("Título do Livro: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor do Livro: ");
        String autor = scanner.nextLine();
        System.out.print("Gênero do Livro: ");
        String genero = scanner.nextLine();
        livrosCadastrados.add(new Livro(titulo, autor, genero));
        System.out.println("Livro cadastrado com sucesso.");
    }

    public static void modificarLivro() {
        System.out.print("Informe o título do livro a ser modificado: ");
        String titulo = scanner.nextLine();
        for (Livro livro : livrosCadastrados) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                System.out.print("Novo título: ");
                livro.setTitulo(scanner.nextLine());
                System.out.print("Novo autor: ");
                livro.setAutor(scanner.nextLine());
                System.out.print("Novo gênero: ");
                livro.setGenero(scanner.nextLine());
                System.out.println("Livro modificado com sucesso.");
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    public static void listarLivrosCadastrados() {
        for (Livro livro : livrosCadastrados) {
            System.out.println(livro);
        }
    }

    public static void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JSON))) {
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{\n");
            jsonBuilder.append("\"biblioteca\": {");
            jsonBuilder.append("\"nome\": \"").append(biblioteca.getNome()).append("\",");
            jsonBuilder.append("\"localizacao\": \"").append(biblioteca.getLocalizacao()).append("\"");
            jsonBuilder.append("},\n");

            jsonBuilder.append("\"livrosCadastrados\": [\n");
            for (int i = 0; i < livrosCadastrados.size(); i++) {
                Livro livro = livrosCadastrados.get(i);
                jsonBuilder.append("{")
                        .append("\"titulo\": \"").append(livro.getTitulo()).append("\",")
                        .append("\"autor\": \"").append(livro.getAutor()).append("\",")
                        .append("\"genero\": \"").append(livro.getGenero()).append("\"")
                        .append("}");
                if (i < livrosCadastrados.size() - 1) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\n");
            }
            jsonBuilder.append("]\n");
            jsonBuilder.append("}");

            writer.write(jsonBuilder.toString());
            System.out.println("Dados salvos em JSON com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public static void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_JSON))) {
            String json = lerArquivo(reader);
            if (json.isEmpty()) {
                System.out.println("Arquivo JSON vazio ou inválido.");
                return;
            }
    
            biblioteca = extrairBiblioteca(json);
            livrosCadastrados = extrairLivros(json);
            System.out.println(json);
            System.out.println("Dados carregados de JSON com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }
    
    private static String lerArquivo(BufferedReader reader) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null) {
            jsonBuilder.append(linha);
        }
        return jsonBuilder.toString();
    }
    
    private static Biblioteca extrairBiblioteca(String json) {
        String nome = extrairValor(json, "\"nome\": \"");
        String localizacao = extrairValor(json, "\"localizacao\": \"");
        return new Biblioteca(nome, localizacao);
    }
    
    private static ArrayList<Livro> extrairLivros(String json) {
        ArrayList<Livro> livros = new ArrayList<>();
        String[] livrosData = json.split("\"livrosCadastrados\": \\[")[1].split("\\],")[0].split("\\},");
    
        for (String livroData : livrosData) {
            String titulo = extrairValor(livroData, "\"titulo\": \"");
            String autor = extrairValor(livroData, "\"autor\": \"");
            String genero = extrairValor(livroData, "\"genero\": \"");
            livros.add(new Livro(titulo, autor, genero));
        }
        return livros;
    }
    
    private static String extrairValor(String json, String chave) {
        try {
            return json.split(chave)[1].split("\",")[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Valor não encontrado";
        }
    }

    public static void apagarDados() {
        biblioteca = new Biblioteca("Biblioteca Central", "Centro da Cidade");
        livrosCadastrados.clear();
        try {
            new FileWriter(ARQUIVO_JSON).close(); // Apaga o conteúdo do arquivo
            System.out.println("Dados apagados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao apagar os dados: " + e.getMessage());
        }
    }
}
